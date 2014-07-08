package org.jenkinsci.complex.axes;

import hudson.matrix.MatrixBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Axis extends hudson.matrix.Axis {

    private final List<? extends Item> complexAxisItems;

    public List<? extends Item> getComplexAxisItems(){
        return Collections.unmodifiableList(complexAxisItems);
    }

    public Axis(String name, String value){
        super(name, value);
        complexAxisItems = new ArrayList<Item>();
    }

    public Axis(String name, List<? extends Item> complexAxisItem){
        super(name, Axis.convertToAxisValue(complexAxisItem));
        this.complexAxisItems = (complexAxisItem!=null)?complexAxisItem: Item.emptyList();
    }

    public static String convertToAxisValue(List<? extends Item> complexAxisItems){
        StringBuilder ret = new StringBuilder();
        boolean valueDefined = false;

        if(complexAxisItems == null)
            complexAxisItems = Item.emptyList();

        for (Item item : complexAxisItems) {
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

    @Override
    public void addBuildVariable(String value, Map<String,String> map){}

    @Override
    public List<String> rebuild( MatrixBuild.MatrixBuildExecution context )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < complexAxisItems.size(); i++){
            complexAxisItems.get(i).rebuild(ret);
        }

        return ret;

    }

    @Override
    public List<String> getValues( )
    {
        List<String> ret = new ArrayList<String>();

        for( int i = 0; i < complexAxisItems.size(); i++){
            complexAxisItems.get(i).getValues(ret);
        }
        return ret;
    }
}
