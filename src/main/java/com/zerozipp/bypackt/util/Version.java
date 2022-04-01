package com.zerozipp.bypackt.util;

public class Version {
    public static boolean NIGHTLY = false;
    public static boolean STABLE = true;
    private String version;
    private boolean stable;

    public Version(int x, int y, int z, boolean stableIn) {
        version = x+"."+y+"."+z;
        stable = stableIn;
    }

    public String getVersion() {
        return version;
    }

    public boolean isStable() {
        return stable;
    }
}
