package eu.baboi.cristian.tourguide.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
