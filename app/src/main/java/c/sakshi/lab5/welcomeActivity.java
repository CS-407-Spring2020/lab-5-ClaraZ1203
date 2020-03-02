package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class welcomeActivity extends AppCompatActivity {

    TextView welcomeMsg;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 1. display welcome message. Fetch username from SharedPreferences.
        welcomeMsg = (TextView) findViewById(R.id.text_welcomeUser);
        Intent intent = getIntent();
        String username = intent.getStringExtra("message");
        welcomeMsg.setText("Welcome " + username + " !");

        // 2. Get SQLiteDatabase instance. (??
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Initiate the "notes" class variable using readNotes method implemented in DBHelper class. Use the username you
        //      got from SharedPreferences as a parameter to read Notes method. (??

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);


        // 4. create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Tittle:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // 5. use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.list_notes);
        listView.setAdapter(adapter);

        // 6. add onItemClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // initialise intent to take user to third activity (NoteActivity in this case)
                Intent intent = new Intent(getApplicationContext(), addNoteActivity.class);
                // add the position of the item that was clicked on as "noteid"
                intent.putExtra("noteid", position);

                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcome_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
                startActivity(intent);
                return true;
            case R.id.item_addNote:
                Intent intent2 = new Intent(this, addNoteActivity.class);
                startActivity(intent2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
