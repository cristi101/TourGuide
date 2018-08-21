package eu.baboi.cristian.tourguide.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.baboi.cristian.tourguide.R;
import eu.baboi.cristian.tourguide.utils.net.model.LatLng;
import eu.baboi.cristian.tourguide.utils.net.model.PlaceType;

public class CategoryAdapter extends FragmentPagerAdapter {
    private final int[] categories = {
            R.string.category_lodging,
            R.string.category_restaurant,
            R.string.category_bar,
            R.string.category_cafe,
            R.string.category_churches,
            R.string.category_museums,
            R.string.category_parks
    };
    private final PlaceType[] types = {
            PlaceType.LODGING,
            PlaceType.RESTAURANT,
            PlaceType.BAR,
            PlaceType.CAFE,
            PlaceType.CHURCH,
            PlaceType.MUSEUM,
            PlaceType.PARK
    };
    private final Context mContext;

    private final LatLng slatina;
    private final LatLng munchen;
    private final int radius;

    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        slatina = new LatLng(44.430168, 24.37169);
        munchen = new LatLng(48.135125, 11.58198);
        radius = 6000;//15000
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0 || position >= types.length) return null;
        return PlacesFragment.newInstance(position, types[position], slatina, radius);
    }

    @Override
    public int getCount() {
        return types.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position < types.length && position >= 0) ? mContext.getString(categories[position]) : null;
    }
}
