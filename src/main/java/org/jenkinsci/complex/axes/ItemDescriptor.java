package org.jenkinsci.complex.axes;

import hudson.model.Descriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class ItemDescriptor extends Descriptor<Item> {

    @Override public String getDisplayName() {
        return "Item";
    }


    public   List<? extends Item> loadDefaultItems(){
        return Collections.emptyList();
    }

    public   List<? extends Item> loadDefaultItems(ArrayList<? extends Item> cai){
        return cai;
    }
}
