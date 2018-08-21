package eu.baboi.cristian.tourguide.utils.net.model;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public enum AddressType implements PlacesRequest.Value {
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

    ACCOUNTING("accounting"),
    AIRPORT("airport"),
    AMUSEMENT_PARK("amusement_park"),
    AQUARIUM("aquarium"),
    ART_GALLERY("art_gallery"),
    ATM("atm"),
    BAKERY("bakery"),
    BANK("bank"),
    BAR("bar"),
    BEAUTY_SALON("beauty_salon"),
    BICYCLE_STORE("bicycle_store"),
    BOOK_STORE("book_store"),
    BOWLING_ALLEY("bowling_alley"),
    BUS_STATION("bus_station"),
    CAFE("cafe"),
    CAMPGROUND("campground"),
    CAR_DEALER("car_dealer"),
    CAR_RENTAL("car_rental"),
    CAR_REPAIR("car_repair"),
    CAR_WASH("car_wash"),
    CASINO("casino"),
    CEMETERY("cemetery"),
    CHURCH("church"),
    CITY_HALL("city_hall"),
    CLOTHING_STORE("clothing_store"),
    CONVENIENCE_STORE("convenience_store"),
    COURTHOUSE("courthouse"),
    DENTIST("dentist"),
    DEPARTMENT_STORE("department_store"),
    DOCTOR("doctor"),
    ELECTRICIAN("electrician"),
    ELECTRONICS_STORE("electronics_store"),
    EMBASSY("embassy"),
    ESTABLISHMENT("establishment"),
    FINANCE("finance"),
    FIRE_STATION("fire_station"),
    FLORIST("florist"),
    FOOD("food"),
    FUNERAL_HOME("funeral_home"),
    FURNITURE_STORE("furniture_store"),
    GAS_STATION("gas_station"),
    GENERAL_CONTRACTOR("general_contractor"),
    GROCERY_OR_SUPERMARKET("grocery_or_supermarket"),
    GYM("gym"),
    HAIR_CARE("hair_care"),
    HARDWARE_STORE("hardware_store"),
    HEALTH("health"),
    HINDU_TEMPLE("hindu_temple"),
    HOME_GOODS_STORE("home_goods_store"),
    HOSPITAL("hospital"),
    INSURANCE_AGENCY("insurance_agency"),
    JEWELRY_STORE("jewelry_store"),
    LAUNDRY("laundry"),
    LAWYER("lawyer"),
    LIBRARY("library"),
    LIQUOR_STORE("liquor_store"),
    LOCAL_GOVERNMENT_OFFICE("local_government_office"),
    LOCKSMITH("locksmith"),
    LODGING("lodging"),
    MEAL_DELIVERY("meal_delivery"),
    MEAL_TAKEAWAY("meal_takeaway"),
    MOSQUE("mosque"),
    MOVIE_RENTAL("movie_rental"),
    MOVIE_THEATER("movie_theater"),
    MOVING_COMPANY("moving_company"),
    MUSEUM("museum"),
    NIGHT_CLUB("night_club"),
    PAINTER("painter"),
    PARK("park"),
    PARKING("parking"),
    PET_STORE("pet_store"),
    PHARMACY("pharmacy"),
    PHYSIOTHERAPIST("physiotherapist"),
    PLACE_OF_WORSHIP("place_of_worship"),
    PLUMBER("plumber"),
    POLICE("police"),
    POST_OFFICE("post_office"),
    REAL_ESTATE_AGENCY("real_estate_agency"),
    RESTAURANT("restaurant"),
    ROOFING_CONTRACTOR("roofing_contractor"),
    RV_PARK("rv_park"),
    SCHOOL("school"),
    SHOE_STORE("shoe_store"),
    SHOPPING_MALL("shopping_mall"),
    SPA("spa"),
    STADIUM("stadium"),
    STORAGE("storage"),
    STORE("store"),
    SUBWAY_STATION("subway_station"),
    SUPERMARKET("supermarket"),
    SYNAGOGUE("synagogue"),
    TAXI_STAND("taxi_stand"),
    TRAIN_STATION("train_station"),
    TRANSIT_STATION("transit_station"),
    TRAVEL_AGENCY("travel_agency"),
    UNIVERSITY("university"),
    VETERINARY_CARE("veterinary_care"),
    ZOO("zoo"),
    UNKNOWN("unknown");

    private final String type;

    AddressType(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }
}