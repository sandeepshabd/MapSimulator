package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;


public class Data implements Serializable {
    private String text;
    private int value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
