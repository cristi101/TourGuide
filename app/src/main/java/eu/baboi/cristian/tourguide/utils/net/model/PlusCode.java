package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONObject;

public class PlusCode {
    public String globalCode;
    public String compoundCode;

    private PlusCode(JSONObject json) {
        globalCode = json.optString("global_code", null);
        compoundCode = json.optString("compound_code", null);
    }
}
