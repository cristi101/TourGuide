package eu.baboi.cristian.tourguide.utils.net;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.File;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.model.Photo;
import eu.baboi.cristian.tourguide.utils.net.requests.PhotoRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;
import eu.baboi.cristian.tourguide.utils.net.results.Photos;
import eu.baboi.cristian.tourguide.utils.secret.DataStore;
import eu.baboi.cristian.tourguide.utils.secret.Key;

public class Loaders {
    private static final String LOG = Loaders.class.getName();

    private Loaders() {
    }

    //start a loader - force loading data
    public static <T> void startLoader(AppCompatActivity activity, int id, Bundle args, LoaderManager.LoaderCallbacks<T> callbacks) {
        LoaderManager manager = activity.getSupportLoaderManager();
        Loader loader = manager.getLoader(id);
        if (loader == null) manager.initLoader(id, args, callbacks);
        else manager.restartLoader(id, args, callbacks);
    }

    public static <T> void initLoader(AppCompatActivity activity, int id, Bundle args, LoaderManager.LoaderCallbacks<T> callbacks) {
        LoaderManager manager = activity.getSupportLoaderManager();
        manager.initLoader(id, args, callbacks);
    }

    private static String key(Context context) {
        DataStore dataStore = new DataStore(context, Model.PASSWORD);
        String password = dataStore.getString(Model.PASSWORD_KEY, null);
        return TextUtils.isEmpty(password) ? null : Key.decodeApiKey(password, context.getString(R.string.key));
    }

    private static Uri photo(File dir, String photoReference, int maxHeight, int maxWidth, String key) {
        PhotoRequest photoRequest = PlacesApi.photo(photoReference);
        photoRequest.setDir(dir).key(key).maxHeight(maxHeight).maxWidth(maxWidth);
        PhotoRequest.Response response = photoRequest.get();
        return response.getResult();
    }

    public static class PlacesLoader<T,
            Rq extends PlacesRequest<T, Rq, Rs>,
            Rs extends PlacesResponse<T>>
            extends AsyncTaskLoader<PlacesResult<T, Rq, Rs>> {

        final private File cacheDir; // the folder to load pictures
        final private Rq request;

        private PlacesResult<T, Rq, Rs> data;

        public PlacesLoader(Context context, Rq request) {
            super(context);
            cacheDir = context.getDir("cachedPictures", 0);
            this.request = request;
        }

        @Override
        protected void onStartLoading() {
            if (data != null) {
                if (takeContentChanged()) data = null;
                else {
                    data.cached = true;
                    deliverResult(data); // use cached data
                }
            }
            if (data == null) forceLoad();
        }

        @Nullable
        @Override
        public PlacesResult<T, Rq, Rs> loadInBackground() {
            String api_key = key(getContext());
            if (api_key == null)
                return new PlacesResult<T, Rq, Rs>(PlacesException.from(PlacesException.Status.REQUEST_DENIED, "You must provide an API key."), request);
            if (request == null)
                return new PlacesResult<T, Rq, Rs>(PlacesException.from(PlacesException.Status.INVALID_REQUEST, "The request cannot be null."), request);
            if (!Model.hasNetwork(getContext()))
                return new PlacesResult<T, Rq, Rs>(PlacesException.from(PlacesException.Status.INTERNET_ERROR, "No network!"), request);

            request.key(api_key);//add key to request

            Rs response = request.get();
            if (response == null)
                return new PlacesResult<T, Rq, Rs>(PlacesException.from(PlacesException.Status.INTERNET_ERROR, "There is something wrong with the Internet connection"), request);

            PlacesException error = response.getError();
            if (error != null) return new PlacesResult<T, Rq, Rs>(error, request);

            T result = response.getResult();
            if (result instanceof Photos) {
                Photos.Iterator iterator = ((Photos) result).iterator();
                Photo photo;
                while ((photo = iterator.next()) != null) {
                    String photoReference = photo.photoReference;
                    if (photoReference != null)
                        photo.uri = photo(cacheDir, photoReference, 400, 400, api_key);
                }
            }
            return new PlacesResult<T, Rq, Rs>(result, request);
        }

        @Override
        public void deliverResult(PlacesResult<T, Rq, Rs> data) {
            this.data = data; // save the data for later
            super.deliverResult(data);
        }
    }

    public static class PlacesResult<T,
            Rq extends PlacesRequest<T, Rq, Rs>,
            Rs extends PlacesResponse<T>> {
        public final PlacesException error;
        public final T data;
        public final Rq request;
        public boolean cached;

        PlacesResult(PlacesException error, Rq request) {
            this.error = error;
            this.data = null;
            this.request = request;
            cached = false;
        }

        PlacesResult(T data, Rq request) {
            this.data = data;
            this.error = null;
            this.request = request;
            cached = false;
        }

    }


}
