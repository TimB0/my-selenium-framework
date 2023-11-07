package org.timboland.testautomation.utilities;

import net.serenitybdd.core.environment.ConfiguredEnvironment;

/**
 * Retrieves property values as determined by Serenity
 */
public class SerenityPropertyHelper {

    /**
     * Find the value of the property with given key as determined by Serenity<br>
     * This has access to all of the values from System.getProperties()<br>
     * and from serenity.conf and serenity.properties
     *
     * @param propertyName - Name of the property
     * @return String - Value of the property. null if not found
     */
    public static String getProperty(String propertyName) {
        return ConfiguredEnvironment.getEnvironmentVariables().getProperty(propertyName);
    }
}
