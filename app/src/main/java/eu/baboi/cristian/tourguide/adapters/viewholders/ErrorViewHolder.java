package eu.baboi.cristian.tourguide.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.utils.PagingCallbacks;
import eu.baboi.cristian.tourguide.utils.net.Loaders;
import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;


public class ErrorViewHolder<E,
        T extends ListAdapter.Page<E>,
        Rq extends PlacesRequest<T, Rq, Rs>,
        Rs extends PlacesResponse<T>,
        VH extends ViewHolder<E>,
        A extends ListAdapter<E, T, Rq, Rs, VH, A>>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    final private TextView message;
    final private AppCompatActivity activity;

    private PagingCallbacks<E, T, Rq, Rs, VH, A> callbacks = null;


    public ErrorViewHolder(@NonNull View itemView, AppCompatActivity context) {
        super(itemView);
        activity = context;

        message = itemView.findViewById(R.id.message);

        Button button = itemView.findViewById(R.id.try_again);
        button.setOnClickListener(this);
    }

    public void bind(PlacesException error, PagingCallbacks<E, T, Rq, Rs, VH, A> callbacks) {
        this.callbacks = callbacks;
        if (error != null)
            message.setText(String.format("Load error: %s\n%s", error.getClass().getSimpleName(), error.getMessage()));
        else message.setText("");
    }

    @Override
    public void onClick(View v) {
        if (callbacks != null)
            Loaders.startLoader(activity, callbacks.loaderId(), null, callbacks);
    }
}
