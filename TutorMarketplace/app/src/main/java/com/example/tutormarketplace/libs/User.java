package com.example.tutormarketplace.libs;

import android.content.ContentValues;

import java.time.Instant;
import java.util.Date;

public class User
{
    public static User CurrentUser;

    long id;
    String login;
    String name;
    String bio;
    Instant lastOnline;
    int isOnline;
    long roleID;


    public User(long id, String login, String name, String bio, Instant lastOnline, int isOnline, long roleID) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.bio = bio;
        this.lastOnline = lastOnline;
        this.isOnline = isOnline;
        this.roleID = roleID;
    }

    public User(long id, String login, String name, Instant lastOnline, int isOnline) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.lastOnline = lastOnline;
        this.isOnline = isOnline;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Instant getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Instant lastOnline) {
        this.lastOnline = lastOnline;
    }

    public int isOnline() {
        return isOnline;
    }

    public void setOnline(int online) {
        isOnline = online;
    }

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public static void AddUser(User user, String password)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.putNull("id");
        contentValues.put("Login", user.login);
        contentValues.put("Password", password);
        contentValues.put("Name", user.name);
        contentValues.put("Bio", user.bio);
        contentValues.put("LastOnline", user.lastOnline.toString());
        contentValues.put("IsOnline", 1);
        contentValues.put("RoleID", user.roleID);

        Db.db.insert("User", null, contentValues);
    }
}
