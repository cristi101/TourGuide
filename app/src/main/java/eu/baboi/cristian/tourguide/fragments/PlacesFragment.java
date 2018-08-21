package eu.baboi.cristian.tourguide.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.baboi.cristian.tourguide.adapters.PlacesAdapter;
import eu.baboi.cristian.tourguide.adapters.viewholders.PlacesViewHolder;
import eu.baboi.cristian.tourguide.utils.net.PlacesApi;
import eu.baboi.cristian.tourguide.utils.net.model.LatLng;
import eu.baboi.cristian.tourguide.utils.net.model.PlaceType;
import eu.baboi.cristian.tourguide.utils.net.requests.NearbySearchRequest;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearch;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;

//E, T extends ListAdapter.Pager<E>, Rq extends PlacesRequest<T, Rq, Rs>, Rs extends PlacesResponse<T>, VH extends ViewHolder<E>, A extends ListAdapter<E, T, Rq, Rs, VH, A>
public class PlacesFragment extends
        CategoryFragment<PlacesSearchResult,
                PlacesSearch,
                NearbySearchRequest,
                NearbySearchRequest.Response,
                PlacesViewHolder,
                PlacesAdapter> {

    private static final String POSITION_KEY = "position";
    private static final String TYPE_KEY = "type";
    private static final String LOCATION_KEY = "location";
    private static final String RADIUS_KEY = "radius";

    public interface IPlaces {
        void setPlacesAdapter(PlacesAdapter adapter);
    }

    public static PlacesFragment newInstance(int position, PlaceType type, LatLng location, int radius) {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        args.putInt(TYPE_KEY, type.ordinal());
        args.putDoubleArray(LOCATION_KEY, new double[]{location.lat, location.lng});
        args.putInt(RADIUS_KEY, radius);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    PlacesAdapter getAdapter() {
        if (adapter == null) {
            int position;
            PlaceType type;
            LatLng location;
            int radius;

            Bundle args = getArguments();
            if (args == null) throw new IllegalArgumentException("PlacesFragment needs args.");

            position = args.getInt(POSITION_KEY);

            int ndx = args.getInt(TYPE_KEY);
            type = PlaceType.values()[ndx];

            double[] doubles = args.getDoubleArray(LOCATION_KEY);
            location = new LatLng(doubles[0], doubles[1]);

            radius = args.getInt(RADIUS_KEY);

            NearbySearchRequest request = PlacesApi.nearbySearchQuery(location).radius(radius).type(type).language("en");
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            adapter = new PlacesAdapter(position, activity, request);

            if (activity instanceof IPlaces)
                ((IPlaces) activity).setPlacesAdapter(adapter);
        }
        return adapter;
    }
}
