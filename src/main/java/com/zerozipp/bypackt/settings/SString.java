package com.zerozipp.bypackt.settings;

public class SString extends Setting {
    public int value;
    public String[] list;

    public SString(String nameIn, int valueIn, String[] listIn) {
        super(nameIn);
        value = valueIn;
        list = listIn;
    }

    public void setValue(int valueIn) {
        value = valueIn;
    }

    public void updateStr(boolean backIn) {
        if(!backIn) {
            if(value >= list.length-1) {
                value = 0;
            }else{
                value += 1;
            }
        }else{
            if(value <= 0) {
                value = list.length-1;
            }else{
                value -= 1;
            }
        }
    }
}
