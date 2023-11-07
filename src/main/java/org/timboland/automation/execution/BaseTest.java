package org.timboland.automation.execution;

import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;
import org.timboland.testautomation.utilities.DriverUtils;

public class BaseTest {

    @Managed
    protected WebDriver driver;

    public BaseTest() {
        DriverUtils.printDriverDetails();
    }

}
