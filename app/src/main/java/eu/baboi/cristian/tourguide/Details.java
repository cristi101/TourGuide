package eu.baboi.cristian.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import eu.baboi.cristian.tourguide.adapters.DetailsAdapter;
import eu.baboi.cristian.tourguide.utils.PagingCallbacks;
import eu.baboi.cristian.tourguide.utils.net.PlacesApi;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.requests.PlaceDetailsRequest;

public class Details extends AppCompatActivity implements PagingCallbacks.Progress {

    private String placeId;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) return;

        placeId = intent.getStringExtra(Model.ID_KEY);
        if (placeId == null) return;

        progress = findViewById(R.id.progress);

        PlaceDetailsRequest request = PlacesApi.placeDetails(placeId).language("en")
                .fields(PlaceDetailsRequest.Fields.FORMATTED_ADDRESS,
                        PlaceDetailsRequest.Fields.FORMATTED_PHONE_NUMBER,
                        PlaceDetailsRequest.Fields.INTERNATIONAL_PHONE_NUMBER,
                        PlaceDetailsRequest.Fields.OPENING_HOURS,
                        PlaceDetailsRequest.Fields.URL,
                        PlaceDetailsRequest.Fields.WEBSITE,
                        PlaceDetailsRequest.Fields.PHOTOS,
                        PlaceDetailsRequest.Fields.NAME,
                        PlaceDetailsRequest.Fields.VICINITY,
                        PlaceDetailsRequest.Fields.RATING,
                        PlaceDetailsRequest.Fields.PRICE_LEVEL,
                        PlaceDetailsRequest.Fields.PLACE_ID);
        DetailsAdapter adapter = new DetailsAdapter(-1, this, request);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        LoaderManager manager = getSupportLoaderManager();
        manager.initLoader(-1, null, new PagingCallbacks<>(adapter, request));
    }

    @Override
    public ProgressBar getProgressBar() {
        return progress;
    }
}
