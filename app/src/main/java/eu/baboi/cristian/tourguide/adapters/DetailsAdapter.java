package eu.baboi.cristian.tourguide.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.viewholders.DetailsViewHolder;
import eu.baboi.cristian.tourguide.utils.net.requests.PlaceDetailsRequest;
import eu.baboi.cristian.tourguide.utils.net.results.PlaceDetails;

public class DetailsAdapter extends
        ListAdapter<PlaceDetails,
                PlaceDetails,
                PlaceDetailsRequest,
                PlaceDetailsRequest.Response,
                DetailsViewHolder,
                DetailsAdapter> {

    private int id;

    public DetailsAdapter(int id, AppCompatActivity context, PlaceDetailsRequest request) {
        super(context, request);
        this.id = id;
    }

    protected int emptyResourceId() {
        return R.string.no_details;
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
                return new DetailsViewHolder(view, activity);
        }
        return super.onCreateViewHolder(parent, type);
    }
}

