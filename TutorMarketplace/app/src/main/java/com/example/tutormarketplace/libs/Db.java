package com.example.tutormarketplace.libs;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class Db
{
    public static SQLiteDatabase db;

    public static void SetDb(SQLiteDatabase database)
    {
        db = database;
    }

    public static void InitDb()
    {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS \"User\" (\n" +
                    "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"Login\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"Password\"\tTEXT NOT NULL,\n" +
                    "\t\"Name\"\tTEXT NOT NULL,\n" +
                    "\t\"Bio\"\tTEXT,\n" +
                    "\t\"LastOnline\"\tDate NOT NULL,\n" +
                    "\t\"IsOnline\"\tINTEGER NOT NULL,\n" +
                    "\t\"RoleID\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Role\"(\"ID\")\n" +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Role\" (\n" +
                    "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "\t\"Name\"\tTEXT NOT NULL UNIQUE\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Tag\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"Name\"\tTEXT NOT NULL UNIQUE\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Ad\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"Name\"\tTEXT NOT NULL,\n" +
                    "\t\"Description\"\tTEXT NOT NULL,\n" +
                    "\t\"Cost\"\tINTEGER NOT NULL,\n" +
                    "\t\"Date\"\tDate NOT NULL,\n" +
                    "\t\"OwnerID\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"OwnerID\") REFERENCES \"User\"(\"ID\")\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Application\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"AdID\"\tINTEGER NOT NULL,\n" +
                    "\t\"Message\"\tTEXT NOT NULL,\n" +
                    "\t\"OwnerID\"\tINTEGER NOT NULL,\n" +
                    "\t\"Date\"\tDate NOT NULL,\n" +
                    "\tFOREIGN KEY(\"OwnerID\") REFERENCES \"User\"(\"ID\"),\n" +
                    "\tFOREIGN KEY(\"AdID\") REFERENCES \"Ad\"(\"ID\")\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Message\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"RecipientID\"\tINTEGER NOT NULL,\n" +
                    "\t\"SenderID\"\tINTEGER NOT NULL,\n" +
                    "\t\"Message\"\tTEXT NOT NULL,\n" +
                    "\t\"Date\"\tDate NOT NULL,\n" +
                    "\t\"IsRead\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"SenderID\") REFERENCES \"User\"(\"ID\"),\n" +
                    "\tFOREIGN KEY(\"RecipientID\") REFERENCES \"User\"(\"ID\")\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"Review\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"RecipientID\"\tINTEGER NOT NULL,\n" +
                    "\t\"SenderID\"\tINTEGER NOT NULL,\n" +
                    "\t\"Message\"\tTEXT NOT NULL,\n" +
                    "\t\"Rating\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"SenderID\") REFERENCES \"User\"(\"ID\"),\n" +
                    "\tFOREIGN KEY(\"RecipientID\") REFERENCES \"User\"(\"ID\")\n" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS \"TagsOfAds\" (\n" +
                    "\t\"TagID\"\tINTEGER NOT NULL,\n" +
                    "\t\"AdID\"\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(\"AdID\") REFERENCES \"Ad\"(\"ID\"),\n" +
                    "\tFOREIGN KEY(\"TagID\") REFERENCES \"Tag\"(\"ID\"),\n" +
                    "\tPRIMARY KEY(\"TagID\",\"AdID\")\n" +
                    ")");

            db.execSQL("INSERT OR IGNORE INTO Role VALUES\n" +
                    "\t(NULL, 'Student'),\n" +
                    "\t(NULL, 'Tutor');");

            db.execSQL("INSERT OR IGNORE INTO User VALUES\n" +
                    "\t(NULL, 'adminS', 'admin', 'Администратор', '', '12-12-2022', 0, 1),\n" +
                    "\t(NULL, 'adminT', 'admin', 'Администратор', '', '12-12-2022', 0, 2);");

            db.execSQL("INSERT OR IGNORE INTO Tag VALUES\n" +
                    "\t(NULL, 'Французский язык'),\n" +
                    "\t(NULL, 'Английский язык'),\n" +
                    "\t(NULL, 'Немецкий язык'),\n" +
                    "\t(NULL, 'Математика');");

            //Создание представлений
            db.execSQL("CREATE VIEW IF NOT EXISTS v_Ad\n" +
                    "AS\n" +
                    "SELECT a.ID AS ID, a.Name AS Name, a.Description AS Description, u.Name AS Owner, a.Cost AS Cost\n" +
                    "FROM Ad AS a INNER JOIN User AS u\n" +
                    "\tON a.OwnerID = u.ID;");

        }
        catch (Exception ex)
        {
            Log.e("DB","База данных не задана!" );
        }
    }
}
