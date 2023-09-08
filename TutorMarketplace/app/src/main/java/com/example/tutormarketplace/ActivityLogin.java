package com.example.tutormarketplace;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutormarketplace.libs.AppSettings;
import com.example.tutormarketplace.libs.Db;
import com.example.tutormarketplace.libs.User;

import java.time.Instant;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Вход");

        Db.SetDb(getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null));
        Db.InitDb();
    }

    public void textRegister_click(View v)
    {
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }

    public void btnLogin_click(View v)
    {
        if(login())
        {
            Intent intent;
            if(User.CurrentUser.getRoleID() == 1)
            {
                intent = new Intent(this, ActivityStudent.class);
                startActivity(intent);
            }
            else if(User.CurrentUser.getRoleID() == 2)
            {
                intent = new Intent(this, ActivityTutor.class);
                startActivity(intent);
            }

        }
    }

    private boolean login()
    {
        EditText login = findViewById(R.id.editTextLogin);
        EditText password = findViewById(R.id.editTextPassword);

        String loginText = login.getText().toString().trim();
        String passwordText = password.getText().toString();

        if(checkAdminLogin())
            return false;

        if(loginText.equals(""))
        {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(passwordText.trim().equals(""))
        {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] args = {loginText, passwordText};
        Cursor query = Db.db.rawQuery("SELECT * FROM User WHERE Login = ? AND Password = ?", args);

        if(!query.moveToFirst())
        {
            Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
            return false;
        }

        String test = query.getString(5);

        User.CurrentUser = new User(
                query.getLong(0),
                query.getString(1),
                query.getString(3),
                query.getString(4),
                Instant.parse(test),
                query.getInt(6),
                query.getLong(7));

        return true;
    }

    private boolean checkAdminLogin()
    {
        EditText login = findViewById(R.id.editTextLogin);
        EditText password = findViewById(R.id.editTextPassword);

        String loginText = login.getText().toString().trim();
        String passwordText = password.getText().toString();

        if(loginText.equals("adminS") && passwordText.equals("admin"))
        {
            Intent intent = new Intent(this, ActivityStudent.class);
            startActivity(intent);
            Toast.makeText(this, "Вы в режиме администратора", Toast.LENGTH_SHORT).show();

            User.CurrentUser = new User(1, "adminS", "admin", "", Instant.now(), 0, 1);
            AppSettings.AdminMode = true;

            return true;
        }
        else if (loginText.equals("adminT") && passwordText.equals("admin"))
        {
            Intent intent = new Intent(this, ActivityTutor.class);
            startActivity(intent);
            Toast.makeText(this, "Вы в режиме администратора", Toast.LENGTH_SHORT).show();

            User.CurrentUser = new User(2, "adminT", "admin", "", Instant.now(), 0, 2);
            AppSettings.AdminMode = true;

            return true;
        }

        AppSettings.AdminMode = false;
        return false;
    }

}