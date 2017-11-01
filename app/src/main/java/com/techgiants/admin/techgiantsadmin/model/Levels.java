package com.techgiants.admin.techgiantsadmin.model;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class Levels {

    private String level;
    private String duration;
    private String description;
    private String subjId;

    public Levels() {
    }

    public Levels(String level, String duration, String description, String subjId) {
        this.level = level;
        this.duration = duration;
        this.description = description;
        this.subjId = subjId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }
}
