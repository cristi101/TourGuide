package eu.baboi.cristian.tourguide.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.utils.PagingCallbacks;
import eu.baboi.cristian.tourguide.utils.net.Loaders;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;

public class ButtonViewHolder<E,
        T extends ListAdapter.Page<E>,
        Rq extends PlacesRequest<T, Rq, Rs>,
        Rs extends PlacesResponse<T>,
        VH extends ViewHolder<E>,
        A extends ListAdapter<E, T, Rq, Rs, VH, A>>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public enum Label {
        FIRST(R.string.first_page),
        NEXT(R.string.next_page);
        public final int res;

        Label(int resId) {
            res = resId;
        }
    }

    final private Button button;
    final private AppCompatActivity activity;

    private PagingCallbacks<E, T, Rq, Rs, VH, A> callbacks = null;

    public ButtonViewHolder(@NonNull View itemView, AppCompatActivity context) {
        super(itemView);
        activity = context;
        button = itemView.findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void bind(Label label, PagingCallbacks<E, T, Rq, Rs, VH, A> callbacks) {
        this.callbacks = callbacks;
        button.setText(label.res);
    }

    public void onClick(View v) {
        if (callbacks != null)
            Loaders.startLoader(activity, callbacks.loaderId(), null, callbacks);
    }
}

