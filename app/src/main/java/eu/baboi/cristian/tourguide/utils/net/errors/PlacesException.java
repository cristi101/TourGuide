package eu.baboi.cristian.tourguide.utils.net.errors;


public class PlacesException extends Exception {

    public enum Status {
        OK("OK"),
        ZERO_RESULTS("ZERO_RESULTS"),
        NOT_FOUND("NOT_FOUND"),
        REQUEST_DENIED("REQUEST_DENIED"),
        OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
        INVALID_REQUEST("INVALID_REQUEST"),
        UNKNOWN_ERROR("UNKNOWN_ERROR"),
        INTERNET_ERROR("INTERNET_ERROR");

        final public String status;

        Status(String status) {
            this.status = status;
        }
    }

    protected PlacesException(String message) {
        super(message);
    }

    public static PlacesException from(String status, String errorMessage) {
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return from(s, errorMessage);
            }
        }
        return new UnknownErrorException(String.format("An unexpected error occured. Status: %s, Message: %s", status, errorMessage));
    }


    public static PlacesException from(Status status, String errorMessage) {
        switch (status) {
            case OK:
                return null;
            case ZERO_RESULTS:
                return new ZeroResultsException(errorMessage);
            case NOT_FOUND:
                return new NotFoundException(errorMessage);
            case REQUEST_DENIED:
                return new RequestDeniedException(errorMessage);
            case OVER_QUERY_LIMIT:
                return new OverQueryLimitException(errorMessage);
            case INVALID_REQUEST:
                return new InvalidRequestException(errorMessage);
            case UNKNOWN_ERROR:
                return new UnknownErrorException(errorMessage);
            case INTERNET_ERROR:
                return new InternetErrorException(errorMessage);
            default:
                return new UnknownErrorException(errorMessage);
        }
    }
}
