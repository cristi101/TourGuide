package eu.baboi.cristian.tourguide.utils.net.model;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public enum RankBy implements PlacesRequest.Value {
    PROMINENCE("prominence"),
    DISTANCE("distance");

    private final String ranking;

    RankBy(String ranking) {
        this.ranking = ranking;
    }


    @Override
    public String value() {
        return ranking;
    }
}
