package org.timboland.testautomation.utilities;

import io.restassured.response.Response;
import org.timboland.automation.exceptions.BaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * utilities to query grid - nodes status, node current session details, , all sessions information
 * restassured request using graphql query
 */
public class GridUtils {


    public static Response getGridCurrentNodeSessionDetails(String webdriverURL) throws BaseException {
        Response response = null;
        return response;
    }

    public static Map<String, String> readGridCurrentNodeDetails(Response response) {
        Map<String, String> sessionDetails = new HashMap<>();
        return sessionDetails;
    }
}
