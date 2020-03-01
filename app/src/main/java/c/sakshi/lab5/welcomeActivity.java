package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class welcomeActivity extends AppCompatActivity {

    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeMsg = (TextView) findViewById(R.id.text_welcomeUser);
        Intent intent = getIntent();
        String username = intent.getStringExtra("message");
        welcomeMsg.setText("Welcome " + username + " !");

    }
}
