package pl.sokols.scyzorykdruzynowego.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teams")
public class Team {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int teamId;

    @ColumnInfo(name = "team_name")
    private String teamName;

    public int getTeamId() {
        return teamId;
    }

    public Team(String teamName) {
        this.teamName = teamName;
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
}
