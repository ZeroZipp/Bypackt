package com.zerozipp.bypackt.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Rotation {
    public float pitch;
    public float yaw;

    public Rotation(float yawIn, float pitchIn) {
        yaw = yawIn;
        pitch = pitchIn;
    }

    public void setPitch(float pitchIn) {
        pitch = pitchIn;
    }

    public void setYaw(float yawIn) {
        yaw = yawIn;
    }

    public static Rotation getLook(Entity eIn, Vec3d pIn) {
        float yaw = 0;
        float pitch = 0;

        double x = pIn.x - eIn.posX;
        double y = pIn.y - 3.5 - eIn.posY + eIn.getEyeHeight();
        double z = pIn.z - eIn.posZ;
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        yaw = (float)Math.toDegrees(-Math.atan(x/z));
        pitch = (float)-Math.toDegrees(Math.atan(y/d));

        if(x < 0 && z < 0) {
            yaw = (float)(90 + Math.toDegrees(Math.atan(z/x)));
        }else if(x > 0 && z < 0) {
            yaw = (float)(-90 + Math.toDegrees(Math.atan(z/x)));
        }

        Rotation rot = new Rotation(yaw, pitch);
        return rot;
    }
}
