package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void loginOnclick(View view) {
        EditText myTextView = (EditText) findViewById(R.id.text_username);
        String username = myTextView.getText().toString();
        goToActivity(username);
    }

    public void goToActivity(String s) {
        Intent intent = new Intent(this, welcomeActivity.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
