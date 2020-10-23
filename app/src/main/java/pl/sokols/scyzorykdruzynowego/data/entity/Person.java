package pl.sokols.scyzorykdruzynowego.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import pl.sokols.scyzorykdruzynowego.utils.DateConverter;

@Keep
@Entity(tableName = "people")
@TypeConverters(DateConverter.class)
public class Person implements Parcelable {

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

    public Person() {}

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

    protected Person(Parcel in) {
        personId = in.readInt();
        name = in.readString();
        surname = in.readString();
        rank = in.readString();
        team = in.readString();
        function = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(personId);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(rank);
        parcel.writeString(team);
        parcel.writeString(function);
    }
}
