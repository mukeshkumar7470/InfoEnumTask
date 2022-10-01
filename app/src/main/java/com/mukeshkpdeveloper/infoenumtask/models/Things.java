package com.mukeshkpdeveloper.infoenumtask.models;

import java.io.Serializable;

public class Things implements Serializable {
    private String thingName;
    private boolean isSelected = false;

    public Things(String thingName, boolean isSelected) {
        this.thingName = thingName;
        this.isSelected = isSelected;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Things{" +
                "thingName='" + thingName + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
