package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String usernameKey= "username";

    public void loginOnclick(View view) {
        EditText myTextView = (EditText) findViewById(R.id.text_username);
        String username = myTextView.getText().toString();
        goToActivity(username);

        //1. get username and password via EditText view
        //2. add username to sharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
    }

    public void goToActivity(String s) {
        Intent intent = new Intent(this, welcomeActivity.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            // "username" key exists in SharedPreferences object which means that a user was logged in before the app closed.
            // get the name of that user from SharedPreferences using sharedPreferences.getString(usernameKey, "").
            // use intent to start the second activity welcoming the user
            String s = sharedPreferences.getString(usernameKey, ""); //question
            Intent intent = new Intent(this, welcomeActivity.class);
            intent.putExtra("message", s);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }
}
