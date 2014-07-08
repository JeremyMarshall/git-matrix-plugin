package org.jenkinsci.complex.axes;

import hudson.matrix.MatrixBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Axis extends hudson.matrix.Axis {

    private final List<? extends Item> axisItems;

    public Axis(String name, String value){
        super(name, value);
        axisItems = new ArrayList<Item>();
    }

    public Axis(String name, List<? extends Item> axisItems){
        super(name, Axis.convertToAxisValue(axisItems));
        this.axisItems = (axisItems!=null)?axisItems: Item.emptyList();
    }

    public static String convertToAxisValue(List<? extends Item> axisItems){
        StringBuilder ret = new StringBuilder();
        boolean valueDefined = false;

        if(axisItems == null)
            axisItems = Item.emptyList();

        for (Item item : axisItems) {
            String i = item.toString();
            if( i.length() > 0){
                valueDefined = true;
                ret.append(item.toString()).append(' ');
            }
        }
        //there has to be something here for the Axis.value
        if (!valueDefined)
            ret.append("default");

        return ret.toString();
    }

    public List<? extends Item> getAxisItems(){
        return Collections.unmodifiableList(axisItems);
    }

    @Override
    public void addBuildVariable(String value, Map<String,String> map){}

    @Override
    public List<String> rebuild( MatrixBuild.MatrixBuildExecution context )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < axisItems.size(); i++){
            axisItems.get(i).rebuild(ret);
        }

        return ret;

    }

    @Override
    public List<String> getValues( )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < axisItems.size(); i++){
            axisItems.get(i).getValues(ret);
        }
        return ret;
    }
}
