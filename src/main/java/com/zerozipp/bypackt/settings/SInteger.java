package com.zerozipp.bypackt.settings;

public class SInteger extends Setting {
    public int value;
    public int[] max = {0, 0};

    public SInteger(String nameIn, int valueIn, int minIn, int maxIn) {
        super(nameIn);
        value = valueIn;
        max = new int[] {
            minIn,
            maxIn
        };
    }

    public void setInteger(int valueIn) {
        value = valueIn;
    }

    public void updateInt(boolean backIn) {
        if(!backIn) {
            if(value < max[1]) {
                value += 1;
            }else{
                value = max[0];
            }
        }else{
            if(value > max[0]) {
                value -= 1;
            }else{
                value = max[1];
            }
        }
    }
}
