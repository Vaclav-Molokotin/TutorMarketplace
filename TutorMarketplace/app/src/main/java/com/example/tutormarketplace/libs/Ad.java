package com.example.tutormarketplace.libs;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Ad
{
    public static Ad CurrentAd;
    private long id;
    private String name;
    private String description;
    private String date;
    private long owner;
    private float cost;
    private String ownerName;

    List<Tag> tags = new ArrayList<Tag>();

    // Конструктор. Передаётся ID владельца
    public Ad(long id, String name, String description, long ownerID, float cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = ownerID;
        this.cost = cost;
    }

    // Конструктор. Передаётся имя владельца
    public Ad(long id, String name, String description, String ownerName, float cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerName = ownerName;
        this.cost = cost;
    }

    // Конструктор. Передаётся дата создания и ID владельца (для добавления записи)
    public Ad(long id, String name, String description, float cost, String date, long owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getDate()
    {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getOwner() {
        return owner;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public float getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public static Ad GetAdById(long id)
    {
        String[] args = {String.valueOf(id)};
        Cursor cursor = Db.db.rawQuery("SELECT * FROM v_Ad WHERE ID = ?", args);
        if(cursor.moveToFirst())
        {
            Ad ad = new Ad(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getFloat(4));
            // Ищем теги объявления
            cursor = Db.db.rawQuery(
                    "SELECT t.ID, t.Name FROM TagsOfAds AS ta INNER JOIN Tag AS t ON ta.TagID = t.ID WHERE ta.AdID = ?", args);
            List<Tag> AdTags = new ArrayList<Tag>();
            if(cursor.moveToFirst())
            {
                do
                {
                    Tag tag = new Tag(cursor.getLong(0), cursor.getString(1));
                    AdTags.add(tag);
                }
                while(cursor.moveToNext());
            }

            ad.setTags(AdTags);

            return ad;
        }
        else
        {
            return null;
        }
    }

    public static void AddAd(Ad ad)
    {

        String[] args = new String[] {
                ad.getName(),
                ad.getDescription(),
                String.valueOf(ad.getCost()),
                String.valueOf(ad.getDate()),
                String.valueOf(ad.getOwner())};

        Db.db.execSQL("INSERT INTO Ad VALUES (NULL, ?, ?, ?, ?, ?)", args);
    }

    public static void UpdateAd(Ad ad)
    {
        String[] args = new String[] {ad.getName(), ad.getDescription(), String.valueOf(ad.getCost()), String.valueOf(ad.getId())};

        Db.db.execSQL("UPDATE Ad SET Name = ?, Description = ?, Cost = ? WHERE Id = ?", args);
    }

    public static void DeleteAd(Ad ad)
    {
        String[] args = new String[] {String.valueOf(ad.getId())};
        Db.db.execSQL("DELETE FROM Ad WHERE Id = ?", args);
    }

    public static List<Ad> GetAds()
    {
        List<Ad> ads = new ArrayList<Ad>();
        Cursor cursor = Db.db.rawQuery("SELECT * FROM v_Ad", null);
        if(cursor.moveToFirst())
        {
            do
            {
                Ad ad = new Ad(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getFloat(4));

                // Ищем теги объявления
                String[] args = new String[] {String.valueOf(ad.getId())};
                Cursor cursorTag = Db.db.rawQuery(
                        "SELECT t.ID, t.Name FROM TagsOfAds AS ta INNER JOIN Tag AS t ON ta.TagID = t.ID WHERE ta.AdID = ?", args);

                //Добавляем теги в список, если они есть
                List<Tag> AdTags = new ArrayList<Tag>();
                if(cursorTag.moveToFirst())
                {
                    do
                    {
                        Tag tag = new Tag(cursorTag.getLong(0), cursorTag.getString(1));
                        AdTags.add(tag);
                    }
                    while(cursorTag.moveToNext());
                }

                ad.setTags(AdTags);
                ads.add(ad);
            }
            while (cursor.moveToNext());

            return ads;
        }
        else
        {
            return new ArrayList<Ad>();
        }
    }

    public static List<Ad> GetAdsByOwner(String ownerName)
    {
        String[] args = new String[] {ownerName};
        List<Ad> ads = new ArrayList<Ad>();
        Cursor cursor = Db.db.rawQuery("SELECT * FROM v_Ad WHERE Owner = ?", args);
        if(cursor.moveToFirst())
        {
            do
            {
                Ad ad = new Ad(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getFloat(4));

                // Ищем теги объявления
                args = new String[] {String.valueOf(ad.getId())};
                Cursor cursorTag = Db.db.rawQuery(
                        "SELECT t.ID, t.Name FROM TagsOfAds AS ta INNER JOIN Tag AS t ON ta.TagID = t.ID WHERE ta.AdID = ?", args);

                //Добавляем теги в список, если они есть
                List<Tag> AdTags = new ArrayList<Tag>();
                if(cursorTag.moveToFirst())
                {
                    do
                    {
                        Tag tag = new Tag(cursorTag.getLong(0), cursorTag.getString(1));
                        AdTags.add(tag);
                    }
                    while(cursorTag.moveToNext());
                }

                ad.setTags(AdTags);
                ads.add(ad);
            }
            while (cursor.moveToNext());

            return ads;
        }
        else
        {
            return new ArrayList<Ad>();
        }
    }
}
