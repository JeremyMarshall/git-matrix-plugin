package org.jenkinsci.complex.axes;

import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.ArrayList;
import java.util.List;

public abstract class AxisDescriptor extends hudson.matrix.AxisDescriptor {


    public AxisDescriptor(){
        load();
    }

    //public abstract DescriptorExtensionList<? extends Item, Descriptor<? extends Item> > axisItemTypes();
    //public DescriptorExtensionList<Item,Descriptor<Item> > axisItemTypes() {
    //    DescriptorExtensionList<Item,Descriptor<Item> >  xxx =  Jenkins.getInstance().<Item,Descriptor<Item>>getDescriptorList(Item.class);
    //
    //    return xxx;
    //}

    @Override
    public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
        // To persist global configuration information,
        // set that to properties and call save().
        req.bindJSON(this, formData);
        save();
        return true;
    }

    public abstract List<ItemDescriptor> axisItemTypes();

    public  List<? extends Item> loadDefaultItems(){

        List<ItemDescriptor> ait =  axisItemTypes();

        ArrayList<Item> ai =  new ArrayList<Item>();

        for( int i = 0; i < ait.size(); i++){
            ait.get(i).loadDefaultItems(ai);
        }

        return ai;
    }

    @Override
    public String getDisplayName() {
        return "Complex Axis";
    }

    @Override
    public boolean isInstantiable() {
        return true;
    }

    public FormValidation doCheckName(@QueryParameter String value) {
        if (value.isEmpty()) {
            return FormValidation.error("You must provide a Name");
        }
        return FormValidation.ok();
    }
}