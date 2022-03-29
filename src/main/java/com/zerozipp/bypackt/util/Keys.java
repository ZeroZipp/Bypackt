package com.zerozipp.bypackt.util;

import java.lang.reflect.Field;

import com.zerozipp.bypackt.Bypackt;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Keys {
    public Bypackt bypackt;

    public Keys() {
        bypackt = Bypackt.getBypackt();
    }

    public void setPressed(KeyBinding binding, boolean pressed) {
        try {
            Field field = binding.getClass().getDeclaredField(bypackt.isObfuscated() ? "field_74513_e" : "pressed");
            field.setAccessible(true);
            field.setBoolean(binding, pressed);
        } catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetPressed(KeyBinding binding) {
        setPressed(binding, GameSettings.isKeyDown(binding));
    }
}