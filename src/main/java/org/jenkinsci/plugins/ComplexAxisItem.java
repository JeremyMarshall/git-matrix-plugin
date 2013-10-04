package org.jenkinsci.plugins;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import java.util.Collections;
import java.util.List;

public abstract class ComplexAxisItem extends AbstractDescribableImpl<ComplexAxisItem> implements Comparable, Describable<ComplexAxisItem> {


    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    public static List<ComplexAxisItem> emptyList(){
        return Collections.emptyList();
    }

    public List<String> rebuild(List<String> list){
        list.add(toString());
        return list;
    }

    List<String> getValues(List<String> list){
        list.add(toString());
        return list;
    }

}
