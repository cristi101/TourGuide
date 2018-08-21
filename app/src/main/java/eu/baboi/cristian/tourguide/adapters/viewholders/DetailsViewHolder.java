package eu.baboi.cristian.tourguide.adapters.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;
import eu.baboi.cristian.tourguide.utils.net.results.PlaceDetails;

public class DetailsViewHolder extends ViewHolder<PlaceDetails> implements View.OnClickListener {
    private static final String LOG = DetailsViewHolder.class.getName();

    private final ImageView photo;
    private final RatingBar rating;
    private final RatingBar level;
    private final TextView name;
    private final TextView address;
    private final TextView level_label;
    private final TextView open;
    private final TextView phone;
    private final TextView website;
    private final LinearLayout call;
    private final LinearLayout web;
    private final Context mContext;

    private String placeId = null;
    private Uri uri = null;

    public DetailsViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mContext = context;

        photo = itemView.findViewById(R.id.photo);
        rating = itemView.findViewById(R.id.rating);
        level = itemView.findViewById(R.id.level);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        level_label = itemView.findViewById(R.id.level_label);
        open = itemView.findViewById(R.id.open);
        phone = itemView.findViewById(R.id.phone);
        website = itemView.findViewById(R.id.website);
        call = itemView.findViewById(R.id.call);
        web = itemView.findViewById(R.id.web);

        LinearLayout layout = itemView.findViewById(R.id.list_item);
        layout.setOnClickListener(this);
    }

    public void bind(PlaceDetails result) {
        if (result == null) return;
        placeId = result.placeId;

        if (result.photos != null) {
            photo.setImageURI(result.photos[0].uri);//show only the first photo
            photo.setVisibility(View.VISIBLE);
        } else photo.setVisibility(View.GONE);

        name.setText(result.name);
        address.setText(result.formattedAddress);

        rating.setRating(result.rating);

        if (result.priceLevel != null && result.priceLevel != PriceLevel.UNKNOWN) {
            level.setRating(result.priceLevel.ordinal() + 1);
            level.setVisibility(View.VISIBLE);
            level_label.setVisibility(View.VISIBLE);
        } else {
            level.setVisibility(View.GONE);
            level_label.setVisibility(View.GONE);
        }

        if (result.openingHours != null) {
            StringBuilder builder = new StringBuilder();
            for (String s : result.openingHours.weekdayText) {
                if (builder.length() != 0)
                    builder.append('\n');
                builder.append(s);
            }
            open.setText(builder.toString());
            open.setVisibility(View.VISIBLE);
        } else open.setVisibility(View.GONE);


        if (result.internationalPhoneNumber != null) {
            phone.setText(result.internationalPhoneNumber);
            phone.setOnClickListener(new Listener(Uri.fromParts("tel", result.internationalPhoneNumber, null)));
            call.setVisibility(View.VISIBLE);
        } else {
            call.setVisibility(View.GONE);
            phone.setOnClickListener(null);
        }

        if (result.website != null) {
            website.setText(result.website.toString());
            website.setOnClickListener(new Listener(Uri.parse(result.website.toString())));
            web.setVisibility(View.VISIBLE);
        } else {
            web.setVisibility(View.GONE);
            website.setOnClickListener(null);
        }

        if (result.url != null) uri = Uri.parse(result.url.toString());
        else uri = null;
    }

    public void onClick(View v) {
        if (uri == null) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, LOG);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    private class Listener implements View.OnClickListener {
        private Uri uri;

        Listener(Uri uri) {
            this.uri = uri;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, LOG);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            }
        }
    }
}
