package pl.sokols.scyzorykdruzynowego.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teams")
public class Team implements Parcelable, Cloneable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int teamId;

    @ColumnInfo(name = "team_name")
    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team() {}

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @NonNull
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Something impossible just happened");
        }
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(teamId);
        parcel.writeString(teamName);
    }

    protected Team(Parcel in) {
        teamId = in.readInt();
        teamName = in.readString();
    }
}
