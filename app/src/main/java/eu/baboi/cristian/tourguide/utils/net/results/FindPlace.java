package eu.baboi.cristian.tourguide.utils.net.results;

import eu.baboi.cristian.tourguide.utils.net.model.Photo;

public class FindPlace implements Photos {
    public PlacesSearchResult candidates[];

    private class Iterator implements Photos.Iterator {
        private int pos = 0;
        private int ndx = 0;

        @Override
        public Photo next() {
            if (candidates == null) return null;

            int len = candidates.length;
            if (len <= 0) return null;

            if (pos >= len) return null;

            int length = 0;
            Photo[] photos = null;
            do {
                PlacesSearchResult result = null;
                for (; result == null && pos < len; pos++) {//skip nulls
                    result = candidates[pos];
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
