package org.timboland.testautomation.utilities;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.webdriver.RemoteDriver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timboland.automation.data.CommonData;
import org.timboland.automation.exceptions.BaseException;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class DriverUtils {


    private static final Logger logger = LoggerFactory.getLogger(DriverUtils.class);

    /**
     * get name of browser in session- safari, chrome, edge etc
     * @return browser name
     */
    private static String getBrowserName() {
        return RemoteDriver.of(Serenity.getDriver()).getCapabilities().getBrowserName().toLowerCase();
    }

    /**
     * get version of the browser in session- safari, chrom, edge etc
     * @return browser name
     */
    public static String getBrowserVersion() {
        return RemoteDriver.of(Serenity.getDriver()).getCapabilities().getBrowserVersion();
    }

    /**
     * get current sessionID
     * @return sessionID
     */
    public static String getCurrentSessionID() {

        return RemoteDriver.of(Serenity.getDriver()).getSessionId().toString();
    }

    /**
     * get execution platform
     * @return platform
     */
    private static String getPlatform() {
        return RemoteDriver.of(Serenity.getDriver()).getCapabilities().getPlatformName().toString();
    }

    /**
     * get list of Capabilities
     * @return Map of capabilities
     */
    public static Map<String, Object> getCapabilities() {
        return RemoteDriver.of(Serenity.getDriver()).getCapabilities().asMap();
    }

    /**
     * get expected browser version, if provided
     * @return browser version
     */
    public static String getExpectedBrowserVersion() {
        return SerenityPropertyHelper.getProperty("webdriver_remote_browser_version");
    }

    /**
     * triggers graphql query to grid node current session
     * reads map of node details
     * returns nodeURI
     * @return nodeId
     */

    public static String getNodeId(String webdriverURL) {

        Response rsp = null;
        try {
            rsp = GridUtils.getGridCurrentNodeSessionDetails(webdriverURL);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        String nodeURI = GridUtils.readGridCurrentNodeDetails(rsp).get("nodeUri");
        return nodeURI;
    }

    /**
     * returns node Id based on the port number
     * for selenium 4 (4944 and 5955)
        * returns nodeID
     * @return
     */
    private static String returnNodeID() {
        String nodeID = "nodeID not found";
        String url = SerenityPropertyHelper.getProperty("webdriver.remote.url");
        try {
            int port = WebUtils.getPortNum(url);
            nodeID = ((port == 4844) || (port == 5955)) ? getNodeId(url) : "node ID not found";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return nodeID;
    }

    public static String getNodeId() {
        String environment = CommonData.getEnvironment();
        String nodeID = "nodeID not found, running on local";
        if (!environment.equalsIgnoreCase("local")) {
            nodeID = returnNodeID();
        }
        return nodeID;
    }

    /**
     * print driver connection details
     * capture source of test run
     */
    public static Map<String, String> returnDriverDetails() {
        final String DRIVER = SerenityPropertyHelper.getProperty("webdriver.driver");
        final String SYS_USER = System.getProperty("user.name");
        final String IS_BITRISE = System.getProperty("bitrise");
        Map<String, String> driverInformation = new HashMap<>();
        String sourceRun= (!(SYS_USER.isEmpty()) && (SYS_USER.equalsIgnoreCase("buildsvr"))) ? "JENKINS" :
                (!(StringUtils.isBlank(IS_BITRISE)) && (IS_BITRISE.equalsIgnoreCase("bitrise"))) ? "BITRISE":"MANUAL";
        String browserVersion = (!(StringUtils.isBlank(DRIVER)) && !(DRIVER.equalsIgnoreCase("appium"))) ? DriverUtils.getBrowserVersion() : "No browser version found.  Either missing webdriver.driver or Mobile Native Test";
        String browserName = (!(StringUtils.isBlank(DRIVER)) && !(DRIVER.equalsIgnoreCase("appium"))) ? DriverUtils.getBrowserName() : "No browser Name found.  Either not passing webdriver.driver or Mobile Native Test";
        driverInformation.put("testSourceName", sourceRun);
        driverInformation.put("browserVersion", browserVersion);
        driverInformation.put("browserName", browserName);
        driverInformation.put("platform", DriverUtils.getPlatform());
        driverInformation.put("sessionID", DriverUtils.getCurrentSessionID());
        driverInformation.put("nodeID", DriverUtils.getNodeId());
        return driverInformation;
    }

    public static void printDriverDetails() {
        logger.info("***** PRINTING DRIVER CONNECTION DETAILS");
        try {

            for (Map.Entry mapEntry : returnDriverDetails().entrySet()) {
                logger.info(mapEntry.getKey() + " : " + mapEntry.getValue());
            }
        }catch (Exception e) {
            logger.debug("Cannot print driver details ");
            e.printStackTrace();
        }
    }
}
