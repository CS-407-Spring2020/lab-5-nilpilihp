package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Main2Activity extends AppCompatActivity {
    TextView welcomeMsgText;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcomeMsgText = (TextView) findViewById(R.id.welcomeMsgText);
        // 1. Display welcome message w/ SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("username","");
        welcomeMsgText.setText("Welcome " + str + "!");

        // 2. Get SQLite instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 3. init with readNotes and username from SharedPreference
        notes = dbHelper.readNotes(str);

        // 4. Create ArrayList iterate over notes obj
        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note:notes)
        {
            displayNotes.add(String.format("Title:%s\nDate:%s",note.getTitle(),note.getDate()));
        }

        // 5. use the ListView to display
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // 6. onItemClickListener for ListView item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Init intent to take user to third activity
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                // ad dhte position of the item that was click on as noteid
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(this,MainActivity.class);
                SharedPreferences sharedPreferences =getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent);
                return true;
            case R.id.addNote:
                Intent intent2 = new Intent(this,Main3Activity.class);
                startActivity(intent2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }



}
