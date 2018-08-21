package eu.baboi.cristian.tourguide.utils.net.model;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public enum AddressComponentType implements PlacesRequest.Value {
    ADMINISTRATIVE_AREA_LEVEL_1("administrative_area_level_1"),
    ADMINISTRATIVE_AREA_LEVEL_2("administrative_area_level_2"),
    ADMINISTRATIVE_AREA_LEVEL_3("administrative_area_level_3"),
    ADMINISTRATIVE_AREA_LEVEL_4("administrative_area_level_4"),
    ADMINISTRATIVE_AREA_LEVEL_5("administrative_area_level_5"),
    COLLOQUIAL_AREA("colloquial_area"),
    CONTINENT("continent"),
    COUNTRY("country"),
    FLOOR("floor"),
    GEOCODE("geocode"),
    INTERSECTION("intersection"),
    LIGHT_RAIL_STATION("light_rail_station"),
    LOCALITY("locality"),
    NATURAL_FEATURE("natural_feature"),
    NEIGHBORHOOD("neighborhood"),
    POINT_OF_INTEREST("point_of_interest"),
    POLITICAL("political"),
    POST_BOX("post_box"),
    POSTAL_CODE("postal_code"),
    POSTAL_CODE_PREFIX("postal_code_prefix"),
    POSTAL_CODE_SUFFIX("postal_code_suffix"),
    POSTAL_TOWN("postal_town"),
    PREMISE("premise"),
    ROOM("room"),
    ROUTE("route"),
    STREET_ADDRESS("street_address"),
    STREET_NUMBER("street_number"),
    SUBLOCALITY("sublocality"),
    SUBLOCALITY_LEVEL_1("sublocality_level_1"),
    SUBLOCALITY_LEVEL_2("sublocality_level_2"),
    SUBLOCALITY_LEVEL_3("sublocality_level_3"),
    SUBLOCALITY_LEVEL_4("sublocality_level_4"),
    SUBLOCALITY_LEVEL_5("sublocality_level_5"),
    SUBPREMISE("subpremise"),
    WARD("ward"),

    AIRPORT("airport"),
    ART_GALLERY("art_gallery"),
    BAR("bar"),
    BUS_STATION("bus_station"),
    CAFE("cafe"),
    CAR_RENTAL("car_rental"),
    CAR_REPAIR("car_repair"),
    CLOTHING_STORE("clothing_store"),
    ELECTRONICS_STORE("electronics_store"),
    ESTABLISHMENT("establishment"),
    FINANCE("finance"),
    FOOD("food"),
    GENERAL_CONTRACTOR("general_contractor"),
    HEALTH("health"),
    HOME_GOODS_STORE("home_goods_store"),
    INSURANCE_AGENCY("insurance_agency"),
    LAWYER("lawyer"),
    LOCAL_GOVERNMENT_OFFICE("local_government_office"),
    LODGING("lodging"),
    MEAL_TAKEAWAY("meal_takeaway"),
    MOVING_COMPANY("moving_company"),
    PAINTER("painter"),
    PARK("park"),
    PARKING("parking"),
    REAL_ESTATE_AGENCY("real_estate_agency"),
    RESTAURANT("restaurant"),
    SCHOOL("school"),
    SHOPPING_MALL("shopping_mall"),
    STORAGE("storage"),
    STORE("store"),
    SUBWAY_STATION("subway_station"),
    TRAIN_STATION("train_station"),
    TRANSIT_STATION("transit_station"),
    TRAVEL_AGENCY("travel_agency"),
    UNKNOWN("unknown");

    private final String type;

    AddressComponentType(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }
}
