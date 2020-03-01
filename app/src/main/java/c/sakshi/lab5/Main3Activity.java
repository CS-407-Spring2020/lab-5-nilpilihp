package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.editTextBox);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", noteid);

        if(noteid != -1)
        {
            // Display content of note by retrieving "notes" arrayList in secondActivity
            Note note = Main2Activity.notes.get(noteid);
            String noteContentDisp = note .getContent();
            editText.setText(noteContentDisp);
        }
    }
    public void saveMethod(View view)
    {
        // 1. Get editText the the content user entered
        EditText editText = (EditText) findViewById(R.id.editTextBox);
        String inputText = editText.getText().toString();

        // 2. Init SQLite
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);

        // 3. Init DBHelper
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set username from SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        // 5. Save info to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        String date = dateFormat.format(new Date() );

        if(noteid == -1) //Add Note
        {
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(username,title,inputText,date);
        }
        else //Update Note
        {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNotes(username,title,inputText,date);
        }

        // 6. Go back to secondActivity
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
