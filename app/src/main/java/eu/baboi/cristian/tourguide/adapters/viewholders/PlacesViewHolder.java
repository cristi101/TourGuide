package eu.baboi.cristian.tourguide.adapters.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import eu.baboi.cristian.tourguide.Details;
import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;

public class PlacesViewHolder extends ViewHolder<PlacesSearchResult> implements View.OnClickListener {
    private static final String LOG = PlacesViewHolder.class.getName();

    private final ImageView photo;
    private final RatingBar rating;
    private final RatingBar level;
    private final TextView name;
    private final TextView address;
    private final TextView level_label;

    final private Context mContext;

    private String placeId = null;

    public PlacesViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mContext = context;

        photo = itemView.findViewById(R.id.photo);
        rating = itemView.findViewById(R.id.rating);
        level = itemView.findViewById(R.id.level);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        level_label = itemView.findViewById(R.id.level_label);

        LinearLayout layout = itemView.findViewById(R.id.list_item);
        layout.setOnClickListener(this);
    }

    public void bind(PlacesSearchResult result) {
        if (result == null) return;
        placeId = result.placeId;

        if (result.photos != null) {
            photo.setImageURI(result.photos[0].uri);
            photo.setVisibility(View.VISIBLE);
        } else {
            photo.setVisibility(View.GONE);
        }

        name.setText(result.name);
        address.setText(result.vicinity);

        rating.setRating(result.rating);

        if (result.priceLevel != null && result.priceLevel != PriceLevel.UNKNOWN) {
            level.setRating(result.priceLevel.ordinal() + 1);
            level.setVisibility(View.VISIBLE);
            level_label.setVisibility(View.VISIBLE);
        } else {
            level.setVisibility(View.GONE);
            level_label.setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        if (placeId == null) return;
        Intent intent = new Intent(mContext, Details.class);
        intent.putExtra(Model.ID_KEY, placeId);
        mContext.startActivity(intent);
    }
}
