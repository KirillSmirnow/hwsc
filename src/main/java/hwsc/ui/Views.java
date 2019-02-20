package hwsc.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Views {

    public static final String PROFILE = "";
    public static final String CLASSROOM = "classroom";
    public static final String ASSIGNMENT = "assignment";
    public static final String HW_TEMPLATE = "hw-template";
    public static final String HW_PROGRESS = "hw-progress";
    public static final String SIGN_IN = "sign-in";
    public static final String SIGN_UP = "sign-up";

    public static final Set<String> PUBLIC_PAGES = new HashSet<>(Arrays.asList(SIGN_IN, SIGN_UP));

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

    public static String hwProgress(long homeworkId) {
        return HW_PROGRESS + "/" + homeworkId;
    }
}
