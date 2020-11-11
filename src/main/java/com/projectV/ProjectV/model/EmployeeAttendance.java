package com.projectV.ProjectV.model;

import java.util.List;

public class EmployeeAttendance {
    private String ID;
    private List<Object> AttendanceStatus;

    public EmployeeAttendance(String ID, List<Object> attendanceStatus) {
        this.ID = ID;
        AttendanceStatus = attendanceStatus;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Object> getAttendanceStatus() {
        return AttendanceStatus;
    }

    public void setAttendanceStatus(List<Object> attendanceStatus) {
        AttendanceStatus = attendanceStatus;
    }
}


