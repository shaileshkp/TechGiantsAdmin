package com.techgiants.admin.techgiantsadmin.model;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class Subjects {
    private String subjectName;
    private String desc;

    public Subjects() {
    }

    public Subjects(String subjectName, String desc) {
        this.subjectName = subjectName;
        this.desc = desc;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
