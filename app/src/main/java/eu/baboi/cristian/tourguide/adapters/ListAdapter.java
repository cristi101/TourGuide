package eu.baboi.cristian.tourguide.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.viewholders.ButtonViewHolder;
import eu.baboi.cristian.tourguide.adapters.viewholders.EmptyViewHolder;
import eu.baboi.cristian.tourguide.adapters.viewholders.ErrorViewHolder;
import eu.baboi.cristian.tourguide.adapters.viewholders.ViewHolder;
import eu.baboi.cristian.tourguide.utils.PagingCallbacks;
import eu.baboi.cristian.tourguide.utils.net.Loaders;
import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;

public abstract class ListAdapter<E,
        T extends ListAdapter.Page<E>,
        Rq extends PlacesRequest<T, Rq, Rs>,
        Rs extends PlacesResponse<T>,
        VH extends ViewHolder<E>,
        A extends ListAdapter<E, T, Rq, Rs, VH, A>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Page<T> {
        boolean hasNext();

        boolean empty();

        int count();

        String token();

        T get(int pos);
    }

    enum ItemType {
        ERROR,
        EMPTY,
        FIRST,
        NEXT,
        ITEM
    }

    private T paging;
    private Rq request;
    private PlacesException error;

    public final AppCompatActivity activity;

    private RecyclerView recyclerView = null;

    ListAdapter(AppCompatActivity context, Rq request) {
        if (context == null)
            throw new IllegalArgumentException("This adapter must be attached to an activity");
        if (request == null) throw new IllegalArgumentException("No null request allowed");
        this.paging = null;
        this.request = request;
        this.error = null;
        activity = context;
    }

    protected abstract int emptyResourceId();// return the resource id for the empty state string

    public abstract int id();// return the adapter id

    private boolean hasFirst(Rq request) {
        return (request instanceof PlacesRequest.Page) && ((PlacesRequest.Page) request).hasFirst();
    }

    ;

    @Override
    public int getItemViewType(int position) {
        if (error != null) return ItemType.ERROR.ordinal();
        if (paging == null || paging.empty()) return ItemType.EMPTY.ordinal();

        if (position == 0 && hasFirst(request)) return ItemType.FIRST.ordinal();
        if (position == getItemCount() - 1 && paging.hasNext()) return ItemType.NEXT.ordinal();
        return ItemType.ITEM.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view;
        ItemType t = ItemType.values()[type];
        switch (t) {
            case ERROR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error, parent, false);
                return new ErrorViewHolder<E, T, Rq, Rs, VH, A>(view, activity);
            case EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty, parent, false);
                return new EmptyViewHolder(view);
            case FIRST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
                return new ButtonViewHolder<E, T, Rq, Rs, VH, A>(view, activity);
            case NEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
                return new ButtonViewHolder<E, T, Rq, Rs, VH, A>(view, activity);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemType t = ItemType.values()[holder.getItemViewType()];
        switch (t) {
            case ERROR:
                ErrorViewHolder<E, T, Rq, Rs, VH, A> errorViewHolder = (ErrorViewHolder<E, T, Rq, Rs, VH, A>) holder;
                PagingCallbacks<E, T, Rq, Rs, VH, A> callbacks = new PagingCallbacks((A) this, request);
                errorViewHolder.bind(error, callbacks);
                break;
            case EMPTY:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.bind(emptyResourceId());
                break;
            case FIRST:
                Rq first = request.copy();
                if (first instanceof PlacesRequest.Page) ((PlacesRequest.Page) first).page(null);
                PagingCallbacks<E, T, Rq, Rs, VH, A> callback1 = new PagingCallbacks((A) this, first);
                ButtonViewHolder<E, T, Rq, Rs, VH, A> bvh1 = (ButtonViewHolder<E, T, Rq, Rs, VH, A>) holder;
                bvh1.bind(ButtonViewHolder.Label.FIRST, callback1);
                break;
            case NEXT:
                String token = paging.token();
                Rq next = request.copy();
                if (next instanceof PlacesRequest.Page) ((PlacesRequest.Page) next).page(token);
                PagingCallbacks<E, T, Rq, Rs, VH, A> callback2 = new PagingCallbacks((A) this, next);
                ButtonViewHolder<E, T, Rq, Rs, VH, A> bvh2 = (ButtonViewHolder<E, T, Rq, Rs, VH, A>) holder;
                bvh2.bind(ButtonViewHolder.Label.NEXT, callback2);
                break;
            case ITEM:
                if (hasFirst(request)) position--;

                E item = paging.get(position);

                VH viewHolder = (VH) holder;
                viewHolder.bind(item);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (error != null) return 1;
        if (paging == null || paging.empty()) return 1;

        int count = paging.count();
        if (paging.hasNext()) count++;
        if (hasFirst(request)) count++;
        return count;
    }

    //called from loader callback
    public void update(Loaders.PlacesResult<T, Rq, Rs> result) {
        if (result == null) {
            request = null;
            paging = null;
            error = null;
        } else {
            request = result.request;
            paging = result.data;
            error = result.error;
        }
        notifyDataSetChanged();
    }

    //return a reference to the request
    public Rq request() {
        return request;
    }

    public void scrollTo(int position) {
        if (recyclerView == null) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        layoutManager.scrollToPosition(position);
    }

    public int currentPosition() {
        if (recyclerView == null) return -1;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return layoutManager.findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

}

