package org.jenkinsci.complex.axes;

import java.util.List;

public abstract class Container extends Item {

    private List<? extends Item> complexAxisItems;

    public Container(List<? extends Item> complexAxisItems) {

        if(complexAxisItems == null)
            this.complexAxisItems = emptyList();
        else
            this.complexAxisItems = complexAxisItems;
    }

    public List<? extends Item> getComplexAxisItems(){
        if(complexAxisItems == null)
            return emptyList();
        else
            return complexAxisItems;
    }

    public void setComplexAxisItems(List<? extends Item> cai){
        complexAxisItems = cai;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Item item : getComplexAxisItems()) {
            ret.append(item.toString()).append(' ');
        }
        return ret.toString();
    }

}
