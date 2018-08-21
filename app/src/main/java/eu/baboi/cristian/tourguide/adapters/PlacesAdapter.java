package eu.baboi.cristian.tourguide.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.viewholders.PlacesViewHolder;
import eu.baboi.cristian.tourguide.utils.net.requests.NearbySearchRequest;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearch;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;

public class PlacesAdapter extends
        ListAdapter<PlacesSearchResult,
                PlacesSearch,
                NearbySearchRequest,
                NearbySearchRequest.Response,
                PlacesViewHolder,
                PlacesAdapter> {

    private int id;

    public PlacesAdapter(int id, AppCompatActivity context, NearbySearchRequest request) {
        super(context, request);
        this.id = id;
    }

    protected int emptyResourceId() {
        return R.string.no_places;
    }

    public int id() {
        return id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = null;
        ItemType t = ItemType.values()[type];
        switch (t) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
                return new PlacesViewHolder(view, activity);
        }
        return super.onCreateViewHolder(parent, type);
    }
}

