package eu.baboi.cristian.tourguide.utils.net.results;

import eu.baboi.cristian.tourguide.utils.net.model.Photo;

public interface Photos {
    interface Iterator {
        Photo next();
    }

    Iterator iterator();
}
