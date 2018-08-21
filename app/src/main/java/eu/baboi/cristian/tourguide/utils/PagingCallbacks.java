package eu.baboi.cristian.tourguide.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.adapters.viewholders.ViewHolder;
import eu.baboi.cristian.tourguide.utils.net.Loaders;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;

public class PagingCallbacks<E,
        T extends ListAdapter.Page<E>,
        Rq extends PlacesRequest<T, Rq, Rs>,
        Rs extends PlacesResponse<T>,
        VH extends ViewHolder<E>,
        A extends ListAdapter<E, T, Rq, Rs, VH, A>>
        implements LoaderManager.LoaderCallbacks<Loaders.PlacesResult<T, Rq, Rs>> {

    private final static String LOG = PagingCallbacks.class.getName();

    public interface Progress {
        ProgressBar getProgressBar();
    }

    private final AppCompatActivity activity;
    private ProgressBar progress;

    private final A adapter;
    private final Rq request;

    public PagingCallbacks(A adapter, Rq request) {
        if (adapter == null) throw new IllegalArgumentException("No null adapter allowed");
        if (request == null) throw new IllegalArgumentException("No null request allowed");
        this.adapter = adapter;
        this.request = request;

        activity = adapter.activity;
        if (activity instanceof Progress)
            progress = ((Progress) activity).getProgressBar();
    }

    public int loaderId() {
        return adapter.id();
    }

    @NonNull
    @Override
    public Loader<Loaders.PlacesResult<T, Rq, Rs>> onCreateLoader(int id, Bundle args) {
        if (progress != null) progress.setVisibility(View.VISIBLE);
        return new Loaders.PlacesLoader<>(activity.getApplicationContext(), request);
    }

    @Override
    public void onLoadFinished(Loader<Loaders.PlacesResult<T, Rq, Rs>> loader, Loaders.PlacesResult<T, Rq, Rs> data) {
        if (progress != null) progress.setVisibility(View.GONE);

        adapter.update(data);
        if (data != null && !data.cached) adapter.scrollTo(0);
    }

    @Override
    public void onLoaderReset(Loader<Loaders.PlacesResult<T, Rq, Rs>> loader) {
        adapter.update(null);
    }
}
