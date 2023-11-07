package org.timboland.automation.data;

import org.apache.commons.lang3.StringUtils;
import org.timboland.testautomation.utilities.SerenityPropertyHelper;

public class CommonData {

    private static final String ENVIRONMENT = "environment";

    public static String getEnvironment() {
        if (StringUtils.isEmpty(SerenityPropertyHelper.getProperty(ENVIRONMENT))) {
            throw new RuntimeException("\r\n !!! MSF msg: environment property not found or incorrect \r\n "
                    + "*** Add 'environment=yourenvironment'");
        } else if (SerenityPropertyHelper.getProperty(ENVIRONMENT).startsWith("uat")){
            return "uat";
        } else if (SerenityPropertyHelper.getProperty(ENVIRONMENT).startsWith("dev")) {
            return "dev";
        } else {
            return SerenityPropertyHelper.getProperty(ENVIRONMENT).toLowerCase();
        }


    }

    }

    }
    }
}
