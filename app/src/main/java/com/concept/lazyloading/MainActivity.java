package com.concept.lazyloading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.concept.lazyloading.database.Data;
import com.concept.lazyloading.database.DatabaseHandler;
import com.concept.lazyloading.displaydata.DisplayData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText username, movie_name, age, favourite_genre;
    Button submit, display;
    RadioButton yes, no;
    RadioGroup options;
    String isButtonChecked = null;
    boolean isAuthenticated = false;
    boolean isDataInserted = false;

    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");


        username = findViewById(R.id.username);
        movie_name = findViewById(R.id.movie);
        age = findViewById(R.id.user_age);
        favourite_genre = findViewById(R.id.genre_liked);
        submit = findViewById(R.id.submit);
        display = findViewById(R.id.display);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        options = findViewById(R.id.option);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(username.getText()).toString().trim();
                String movie = Objects.requireNonNull(movie_name.getText()).toString().trim();
                String user_age = Objects.requireNonNull(age.getText()).toString().trim();
                String genre = Objects.requireNonNull(favourite_genre.getText()).toString().trim();

                isAuthenticated = authenticate(name, movie, user_age, genre);

                if (isAuthenticated) {
                    data = new Data(name, user_age, movie, genre, isButtonChecked);
                    data.setUsername(name);
                    data.setAge(user_age);
                    data.setMovie_name(movie);
                    data.setGenre(genre);
                    data.setLike_anime(isButtonChecked);

                    // inserting data into database
                    isDataInserted = databaseHandler.insertData(data);

                    if (isDataInserted) {

                        username.setText("");
                        age.setText("");
                        movie_name.setText("");
                        favourite_genre.setText("");
                        options.clearCheck();

                        Toast.makeText(getApplicationContext(), "Data inserted Successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(MainActivity.this, DisplayData.class));
//                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                }
                else  {
                    Toast.makeText(getApplicationContext(), "Operation Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

            private boolean authenticate(String name, String movie, String user_age, String genre) {

                boolean result = true;

                if (name.isEmpty()) {
                    username.setError("Field is required");
                    username.requestFocus();
                    result = false;
                }

                if (movie.isEmpty()) {
                    movie_name.setError("Field is required");
                    movie_name.requestFocus();
                    result = false;
                }

                if (user_age.isEmpty()) {
                    age.setError("Field is required");
                    age.requestFocus();
                    result = false;
                }
                else if (Integer.parseInt(user_age) < 18) {
                    age.setError("Age must be greater than 18");
                    age.requestFocus();
                    result = false;
                }

                if (genre.isEmpty()) {
                    favourite_genre.setError("Field is required");
                    favourite_genre.requestFocus();
                    result = false;
                }

                return result;

            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.getAllData().size() > 0) {
                    startActivity(new Intent(MainActivity.this, DisplayData.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioBtnClick(View view) {
        boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.yes:
                if (check) {
                    isButtonChecked = "yes";
                }
                break;
            case R.id.no:
                if (check) {
                    isButtonChecked = "no";
                }
                break;
        }
    }
}