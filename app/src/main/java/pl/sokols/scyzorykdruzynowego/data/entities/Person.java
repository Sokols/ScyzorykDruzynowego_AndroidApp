package pl.sokols.scyzorykdruzynowego.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import pl.sokols.scyzorykdruzynowego.utils.DateConverter;

@Entity(tableName = "people")
@TypeConverters(DateConverter.class)
public class Person {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int personId;

    private String name;
    private String surname;

    @ColumnInfo(name = "date_of_birth")
    private Date dateOfBirth;

    private String rank;
    private String team;
    private String function;

    public Person(String name, String surname, Date dateOfBirth, String rank, String team, String function) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.rank = rank;
        this.team = team;
        this.function = function;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
