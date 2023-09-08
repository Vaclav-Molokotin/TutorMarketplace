package com.example.tutormarketplace.libs;

import android.media.tv.interactive.AppLinkInfo;

import java.time.Instant;

public class Application
{
    public static Application CurrentApplication;
    private long id;
    private Ad ad;
    private String message;
    private User owner;

    private Instant date;



    public Application(long id, User owner, String message, Ad ad, Instant date) {
        this.id = id;
        this.owner = owner;
        this.message = message;
        this.ad = ad;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getMessage() {
        return message;
    }

    public Ad getAd() {
        return ad;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public static void addApplication(Application application)
    {
        String[] args = new String[] {
                String.valueOf(application.ad.getId()),
                application.message,
                String.valueOf(application.owner.getId()),
                application.date.toString()
        };
        Db.db.execSQL("INSERT INTO Application VALUES (NULL, ?, ?, ?, ?);", args);
    }
}
