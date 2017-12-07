package org.smirnowku.hwsc.ui;

import org.smirnowku.hwsc.core.model.Check;

public class Views {

    public static final String PROFILE = "";
    public static final String CLASSROOM = "classroom";
    public static final String ASSIGNMENT = "assignment";
    public static final String HW_TEMPLATE = "hw-template";
    public static final String SIGN_IN = "sign-in";
    public static final String SIGN_UP = "sign-up";

    public static String classroom(long id) {
        return CLASSROOM + "/" + id;
    }

    public static String hwTemplate(long id) {
        return HW_TEMPLATE + "/" + id;
    }

    public static String assignment(long id) {
        return ASSIGNMENT + "/" + id;
    }

    public static String assignment(long id, Check.Status status) {
        return ASSIGNMENT + "/" + id;
    }
}
