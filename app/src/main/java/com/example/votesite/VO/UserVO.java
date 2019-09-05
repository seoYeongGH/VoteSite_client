package com.example.votesite.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserVO {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("code")
    @Expose
    int code;

    @SerializedName("friend")
    @Expose
    int friend;

    public UserVO(String id, String password, String name, String email, int code, int friend) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.code = code;
        this.friend = friend;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCode() {
        return code;
    }

    public int getFriend() {
        return friend;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }
}
