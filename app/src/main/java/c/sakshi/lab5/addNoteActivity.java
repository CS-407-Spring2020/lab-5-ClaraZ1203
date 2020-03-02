package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static c.sakshi.lab5.MainActivity.usernameKey;

public class addNoteActivity extends AppCompatActivity {

    Integer noteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // 1. get EditText view
        TextView showNotes = (TextView) findViewById(R.id.text_editNote);
        // 2. get intent
        Intent intent = getIntent();
        // 3. get the value of integer "noteid" from intent
        noteid = intent.getIntExtra("noteid", -1);

        // 4. initialise class variable "noteid" with the value from intent

        if (noteid != -1) {
            // display content of note by retrieving "notes" ArrayList in welcomeActivity
            Note note = welcomeActivity.notes.get(noteid);
            String noteContent = note.getContent();
            // use editText.setText() to display the contents of this note on screen
            showNotes.setText(noteContent);
        }
    }

    public void saveOnClick(View view) {
        // 1. get editText view and the content that user entered
        EditText newNote = (EditText) findViewById(R.id.text_editNote);
        // 2. initialise SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        // 3. Initialise DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        // 4. set username in the following variable by fetching it from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, ""); //question

        // 5. save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //add note
            title = "Note_" + (welcomeActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, newNote.getText().toString(), date);
        } else { //update note
            title = "Note_" + (noteid + 1);
            dbHelper.updateNote(title, date, newNote.getText().toString(), username);
        }

        // 6. go to welcome activity using intents.
        Intent intent = new Intent(this, welcomeActivity.class);
        String s = sharedPreferences.getString(usernameKey, "");
        intent.putExtra("message", s);
        startActivity(intent);
    }
}