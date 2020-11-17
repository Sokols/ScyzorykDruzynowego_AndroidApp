package pl.sokols.scyzorykdruzynowego.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stamp {

    @SerializedName("stamp_id")
    private int stampId;
    @SerializedName("section_id")
    private int sectionId;
    @SerializedName("branch_id")
    private int branchId;
    @SerializedName("stamp_level")
    private int stampLevel;
    @SerializedName("stamp_name")
    private String stampName;
    @SerializedName(("stamp_image_url"))
    private String stampImageURL;
    @SerializedName("stamp_tasks")
    private List<String> stampTasks;

    public Stamp(int stampId, int sectionId, int branchId, int stampLevel, String stampName, String stampImageURL, List<String> stampTasks) {
        this.stampId = stampId;
        this.sectionId = sectionId;
        this.branchId = branchId;
        this.stampLevel = stampLevel;
        this.stampName = stampName;
        this.stampImageURL = stampImageURL;
        this.stampTasks = stampTasks;
    }

    public int getStampId() {
        return stampId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getStampLevel() {
        return stampLevel;
    }

    public String getStampName() {
        return stampName;
    }

    public String getStampImageURL() {
        return stampImageURL;
    }

    public List<String> getStampTasks() {
        return stampTasks;
    }
}
