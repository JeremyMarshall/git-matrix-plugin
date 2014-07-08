package org.jenkinsci.complex.axes;

import java.util.List;

public abstract class Container extends Item {

    private List<? extends Item> axisItems;

    public Container(List<? extends Item> axisItems) {

        if(axisItems == null)
            this.axisItems = emptyList();
        else
            this.axisItems = axisItems;
    }

    public List<? extends Item> getAxisItems(){
        if(axisItems == null)
            return emptyList();
        else
            return axisItems;
    }

    public void setAxisItems(List<? extends Item> cai){
        axisItems = cai;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Item item : getAxisItems()) {
            ret.append(item.toString()).append(' ');
        }
        return ret.toString();
    }

}
