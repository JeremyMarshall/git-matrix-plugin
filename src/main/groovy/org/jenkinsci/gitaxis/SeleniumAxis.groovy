/*
* The MIT License
*
* Copyright (c) 2010, InfraDNA, Inc.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package org.jenkinsci.gitaxis

import hudson.Extension
import hudson.util.FormValidation
import jenkins.model.Jenkins
import org.jenkinsci.complex.axes.Axis
import org.jenkinsci.complex.axes.AxisDescriptor
import org.jenkinsci.complex.axes.Item
import org.jenkinsci.complex.axes.ItemDescriptor
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.QueryParameter

public class SeleniumAxis extends Axis{

    @DataBoundConstructor
    public SeleniumAxis(String name, List<? extends Item> seleniumCapabilities){
        super(name, seleniumCapabilities)
    }

    public List<? extends SeleniumCapability> getSeleniumCapabilities(){
        return this.getComplexAxisItems() as List<? extends SeleniumCapability>
    }

    @Override
    public void addBuildVariable(String value, Map<String,String> map) {

       //so the value is PLATFORM-BROWSER-VERSION

       def parts = value.split(/-/)

       map.put(name + "_PLATFORM", parts[0]);
       map.put(name + "_BROWSER", parts[1]);
       map.put(name + "_VERSION", parts[2]);
    }



    @Extension
    public static class DescriptorImpl extends AxisDescriptor{
        private String server

        public String getServer(){
            if(server == null)
                return "http://localhost:4444"
            else
                return server
        }

        public void setServer(String server){
            this.server = server
        }

        public  List<ItemDescriptor> complexAxisItemTypes(){
            def cait = Jenkins.getInstance().<Item,ItemDescriptor>getDescriptorList(Item.class)

            def ret = new ArrayList<ItemDescriptor>()

            for( int i = 0; i < cait.size(); i++) {
                def name = cait.get(i).getClass().getName()

                //don't want the RO version to appear in the add list as it is added as part of the Dynamic item
                if (!name.contains("SeleniumCapabilityRO"))
                    ret.add(cait.get(i))

            }
            return ret
        }

        public List<? extends SeleniumCapability> getSeleniumCapabilities() {
            try{
                def sel = new Selenium(Selenium.load(getServer()), SeleniumCapabilityRO.class)

                sel.seleniumCapabilities
            }catch(ex){
                Item.emptyList()
            }
        }

        @Override
        public String getDisplayName() {
            return "Selenium Capability Axis"
        }


        public FormValidation doCheckServer(@QueryParameter String value) {
            if (value.isEmpty()) {
                return FormValidation.error("You must provide an URL.")
            }

            try {
                new URL(value)
            } catch (final MalformedURLException e) {
                return FormValidation.error("This is not a valid URL.")
            }

            return FormValidation.ok()
        }

    }

}