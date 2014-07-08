package org.jenkinsci.gitaxis

import hudson.Extension
import org.jenkinsci.complex.axes.Item
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor

import java.util.regex.Matcher

class SeleniumCapability extends  Item implements Comparable {

    protected Integer maxInstances
    protected String browserName
    protected String platformName
    protected String browserVersion

    SeleniumCapability() {
        browserName = "Any"
        platformName = "Any"
        browserVersion = "Any"
        maxInstances = 1
    }

    @DataBoundConstructor
    SeleniumCapability(String browserName, String platformName, String browserVersion) {
        this.browserName = browserName
        this.platformName = platformName
        this.browserVersion = browserVersion
    }

    SeleniumCapability(String titleAttr) {
        this()

        Matcher m = (titleAttr =~ /(platform|browserName|version)=(\w+)/)

        while (m.find()) {
            if (m.group(1).equals("platform"))
                this.platformName = m.group(2)
            else if (m.group(1).equals("browserName"))
                this.browserName = m.group(2)
            else if (m.group(1).equals("version"))
                this.browserVersion = m.group(2)
        }
    }

    public Integer incr() {
        maxInstances++
    }

    public String combinationFilter() {
        String.format("(TEST_PLATFORM=='%s' && TEST_BROWSER=='%s' && TEST_VERSION=='%s')", platformName, browserName, browserVersion)
    }

    @Override
    String toString() {
        String.format("%s-%s-%s", platformName, browserName, browserVersion)
    }


    public String getBrowserName(){
        this.browserName
    }

    public String getPlatformName(){
        this.platformName
    }

    public String getBrowserVersion(){
        this.browserVersion
    }

    @Extension public static class DescriptorImpl extends ItemDescriptor {

        @Override public String getDisplayName() {
            return "Defined Capability";
        }
    }
}
