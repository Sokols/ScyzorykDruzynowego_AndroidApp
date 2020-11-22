package pl.sokols.scyzorykdruzynowego.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stamp implements Parcelable {

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
    @SerializedName("stamp_tasks")
    private List<String> stampTasks;

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

    public List<String> getStampTasks() {
        return stampTasks;
    }

    public static final Creator<Stamp> CREATOR = new Creator<Stamp>() {
        @Override
        public Stamp createFromParcel(Parcel in) {
            return new Stamp(in);
        }

        @Override
        public Stamp[] newArray(int size) {
            return new Stamp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stampId);
        dest.writeInt(sectionId);
        dest.writeInt(branchId);
        dest.writeInt(stampLevel);
        dest.writeString(stampName);
        dest.writeStringList(stampTasks);
    }

    protected Stamp(Parcel in) {
        stampId = in.readInt();
        sectionId = in.readInt();
        branchId = in.readInt();
        stampLevel = in.readInt();
        stampName = in.readString();
        stampTasks = in.createStringArrayList();
    }
}
