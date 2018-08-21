package eu.baboi.cristian.tourguide.utils.net.results;

import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.utils.net.model.Photo;

public class PlacesSearch implements Photos, ListAdapter.Page<PlacesSearchResult> {
    public PlacesSearchResult results[];
    public String htmlAttributions[];
    public String nextPageToken;

    @Override
    public boolean hasNext() {
        return nextPageToken != null;
    }

    @Override
    public boolean empty() {
        return results == null || results.length == 0;
    }

    @Override
    public int count() {
        return empty() ? 0 : results.length;
    }

    @Override
    public String token() {
        return nextPageToken;
    }

    @Override
    public PlacesSearchResult get(int pos) {
        if (empty()) return null;
        if (pos < 0 || pos >= results.length) return null;
        return results[pos];
    }


    private class Iterator implements Photos.Iterator {
        private int pos = 0;
        private int ndx = 0;

        @Override
        public Photo next() {
            if (results == null) return null;

            int len = results.length;
            if (len <= 0) return null;

            if (pos >= len) return null;

            int length = 0;
            Photo[] photos = null;
            do {
                PlacesSearchResult result = null;
                for (; result == null && pos < len; pos++) {//skip nulls
                    result = results[pos];
                }
                if (result == null) return null;

                pos--;

                photos = result.photos;
                if (photos == null || (length = photos.length) <= 0) {
                    ndx = 0;
                    pos++;
                } else break;
            } while (true);

            if (ndx != 0) {// ndx < length
                int n = ndx;
                ndx++;
                if (ndx >= length) {
                    ndx = 0;
                    pos++;
                }
                return photos[n];
            } else {
                ndx++;
                if (ndx >= length) {
                    ndx = 0;
                    pos++;
                }
                return photos[0];
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator();
    }
}
