package org.smirnowku.hwsc.ui.classroom.actions;

public enum MemberRole {

    STUDENT("Student"),
    TEACHER("Teacher");

    private final String value;

    MemberRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
