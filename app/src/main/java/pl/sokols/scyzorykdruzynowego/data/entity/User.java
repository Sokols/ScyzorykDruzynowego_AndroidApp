package pl.sokols.scyzorykdruzynowego.data.entity;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    private String serverId;
    @SerializedName("id")
    private Integer userId;
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getServerId() {
        return serverId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
