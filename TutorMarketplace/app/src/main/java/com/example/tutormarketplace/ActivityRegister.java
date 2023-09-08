package com.example.tutormarketplace;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tutormarketplace.libs.Db;
import com.example.tutormarketplace.libs.User;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initDb();

        Button btn = findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (register())
                {
                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });
        setTitle("Регистрация");
    }

    private void initDb()
    {
        Db.SetDb(getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null));
    }

    private boolean register() {

        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        EditText password2 = findViewById(R.id.password2);
        RadioButton rbtnIsStudent = findViewById(R.id.rbtnIsStudent);

        String loginText = login.getText().toString();
        String passwordText = password.getText().toString();
        String password2Text = password2.getText().toString();
        long role = rbtnIsStudent.isChecked()?1:2;

        Instant currentTime = Instant.now();

        String lastOnline = currentTime.toString();

        // Валидация логиа
        if (loginText.equals(""))
        {
            Toast.makeText(this, "Укажите логин!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Валидация пароля
        if (passwordText.equals("") && password2Text.equals(""))
        {
            Toast.makeText(this, "Укажите пароль!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passwordText.equals(password2Text))
        {
            Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка существования логина
        String[] args = {loginText};
        Cursor query = Db.db.rawQuery("SELECT * FROM User WHERE login = ?;", args);

        if (query.moveToFirst())
        {
            Toast.makeText(this, "Такой логин уже существует!", Toast.LENGTH_SHORT).show();
            return false;
        }

        User user = new User(0, loginText, "Пользователь", "", Instant.now(), 0, role);
        User.AddUser(user, passwordText);


        // Добавления аккаунта пользователя


        // Проверка существования логина
        args = new String[] {loginText};
        query = Db.db.rawQuery("SELECT * FROM User;", null);

        return true;
    }
}