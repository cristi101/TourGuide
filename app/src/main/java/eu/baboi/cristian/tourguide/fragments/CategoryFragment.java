package eu.baboi.cristian.tourguide.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.adapters.viewholders.ViewHolder;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlacesResponse;

public abstract class CategoryFragment<E,
        T extends ListAdapter.Page<E>,
        Rq extends PlacesRequest<T, Rq, Rs>,
        Rs extends PlacesResponse<T>,
        VH extends ViewHolder<E>,
        A extends ListAdapter<E, T, Rq, Rs, VH, A>>
        extends Fragment {

    A adapter = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(getAdapter());

        return rootView;
    }

    abstract A getAdapter();
}
