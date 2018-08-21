package eu.baboi.cristian.tourguide.utils.net.requests;

import android.net.Uri;

import java.io.File;

import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;

public class PhotoRequest extends PlacesRequest<Uri, PhotoRequest, PhotoRequest.Response> {
    private File dir;

    @Override
    protected Class<Response> clazz() {
        return Response.class;
    }

    @Override
    protected String url() {
        return BASE_URL + "/photo";
    }

    @Override
    protected void validate() {
        if (!params().containsKey("photoreference")) {
            throw new IllegalArgumentException("Request must contain 'photoReference'.");
        }
        if (!params().containsKey("maxheight") && !params().containsKey("maxwidth")) {
            throw new IllegalArgumentException("Request must contain 'maxHeight' or 'maxWidth'.");
        }
    }

    @Override
    public PhotoRequest copy() {
        PhotoRequest request = new PhotoRequest();
        request.putAll(params());
        return request;
    }

    public PhotoRequest photoReference(String photoReference) {
        return param("photoreference", photoReference);
    }

    public PhotoRequest maxHeight(int maxHeight) {
        return param("maxheight", String.valueOf(maxHeight));
    }

    public PhotoRequest maxWidth(int maxWidth) {
        return param("maxwidth", String.valueOf(maxWidth));
    }

    public PhotoRequest setDir(File dir) {
        this.dir = dir;
        return instance();
    }

    public File getDir() {
        return dir;
    }

    public static class Response implements PlacesResponse<Uri> {

        private final Uri uri;

        public Response(Uri uri) {
            this.uri = uri;
        }

        @Override
        public boolean successful() {
            return uri != null;
        }

        @Override
        public PlacesException getError() {
            if (successful()) {
                return null;
            }
            return PlacesException.from("x", "no picture");
        }

        @Override
        public Uri getResult() {
            return uri;
        }
    }

}
