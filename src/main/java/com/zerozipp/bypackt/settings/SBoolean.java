package com.zerozipp.bypackt.settings;

public class SBoolean extends Setting {
    public boolean active;

    public SBoolean(String nameIn, boolean activeIn) {
        super(nameIn);
        active = activeIn;
    }

    public void setBoolean(boolean activeIn) {
        active = activeIn;
    }
}
