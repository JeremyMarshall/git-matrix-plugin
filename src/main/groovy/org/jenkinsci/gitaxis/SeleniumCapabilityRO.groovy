package org.jenkinsci.gitaxis

import hudson.Extension
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor

class SeleniumCapabilityRO extends SeleniumCapability{

    public SeleniumCapabilityRO(){
        super()
    }

    @DataBoundConstructor
    public SeleniumCapabilityRO(String browserName, String platformName, String browserVersion) {
        super(browserName, platformName, browserVersion)
    }

    public SeleniumCapabilityRO(String titleAttr){
        super(titleAttr)
    }

    @Extension
    public static class DescriptorImpl extends ItemDescriptor {

        @Override public String getDisplayName() {
            return "Selenium Capability RO";
        }
    }

}
