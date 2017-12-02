package org.smirnowku.hwsc.ui;

public class Views {

    public static final String PROFILE = "";
    public static final String CLASSROOM = "classroom";
    public static final String SIGN_IN = "sign-in";
    public static final String SIGN_UP = "sign-up";

    public static String classroom(long id) {
        return CLASSROOM + "/" + id;
    }
}
