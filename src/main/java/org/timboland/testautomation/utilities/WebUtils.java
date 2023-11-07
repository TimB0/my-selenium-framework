package org.timboland.testautomation.utilities;

import java.net.MalformedURLException;
import java.net.URL;

public class WebUtils {

    /**
     * read port from current URL
     * @param "Port Number"
     * @return MalformedURLException
     */
    public static int getPortNum(String url) throws MalformedURLException {
        URL urlParts = new URL(url);
        return urlParts.getPort();
    }
}
