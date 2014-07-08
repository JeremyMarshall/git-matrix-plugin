package org.jenkinsci.gitaxis

import hudson.Extension
import hudson.model.Descriptor
import jenkins.model.Jenkins
import org.jenkinsci.complex.axes.AxisDescriptor
import org.jenkinsci.complex.axes.Container
import org.jenkinsci.complex.axes.ContainerDescriptor
import org.jenkinsci.complex.axes.Item
import org.kohsuke.stapler.DataBoundConstructor

class SeleniumDynamicCapability extends  Container {

    SeleniumDynamicCapability() {
        super(new ArrayList<SeleniumCapabilityRO>())
    }

    @DataBoundConstructor
    SeleniumDynamicCapability(List<SeleniumCapabilityRO> seleniumCapabilities) {
        super( seleniumCapabilities)
    }

    List<SeleniumCapabilityRO> getSeleniumCapabilities(){
        return getAxisItems()
    }

    void setSeleniumCapabilities(List<SeleniumCapabilityRO> sc){
        setAxisItems(sc)
    }

    String toString(){
        "DetectedSelenium"
    }

    @Override
    public List<String> rebuild(List<String> list){
        SeleniumDynamicCapability.DescriptorImpl sdcd = Jenkins.getInstance().getDescriptor(SeleniumDynamicCapability.class)

        List<SeleniumCapabilityRO> sc = sdcd.loadDefaultItems()

        if (sc.size() == 0)
            throw(new SeleniumException("No selenium capabilities detected"))

        setSeleniumCapabilities(sc)

        sc.each{list.add(it.toString())}
        return list;
    }

    @Override
    public List<String> getValues(List<String> list){
        getSeleniumCapabilities().each{list.add(it.toString())}

        if (list.size() == 0)
            list.add('Rebuilt at build time')

        return list;
    }

    @Extension public static class DescriptorImpl extends ContainerDescriptor {

        //so we need this to get at the name of the selenium server in the global config
        protected static Descriptor<? extends AxisDescriptor> getTopLevelDescriptor(){
            SeleniumAxis.DescriptorImpl sad = Jenkins.getInstance().getDescriptor(SeleniumAxis.class)
            sad.load()

            return sad
        }

        @Override
        public   List<? extends Item> loadDefaultItems(ArrayList<? extends Item> cai){
            def sdc = new SeleniumDynamicCapability(loadDefaultItems())

            cai.add(sdc)

            cai
        }

        @Override
        public  List<SeleniumCapabilityRO> loadDefaultItems(){
            getTopLevelDescriptor().getSeleniumCapabilities()
        }

        @Override public String getDisplayName() {
            return "Detected Capability";
        }

    }
}
