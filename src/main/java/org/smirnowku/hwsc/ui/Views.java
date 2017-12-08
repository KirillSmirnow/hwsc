package org.smirnowku.hwsc.ui;

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

    public static String assignment(long assignmentId) {
        return ASSIGNMENT + "/asg/" + assignmentId;
    }

    public static String assignmentByCheck(long checkId) {
        return ASSIGNMENT + "/check/" + checkId;
    }
}
