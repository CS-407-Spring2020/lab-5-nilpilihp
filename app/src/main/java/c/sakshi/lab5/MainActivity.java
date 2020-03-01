package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5",Context.MODE_PRIVATE);

        // did not log out
        if(! sharedPreferences.getString(usernameKey,"").equals(""))
        {
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        }
        else
            setContentView(R.layout.activity_main);

    }

    public void onButtonClick(View view)
    {
        //get username from editText
        EditText usernameText = (EditText) findViewById(R.id.username);
        String str = usernameText.getText().toString();

        //store key as username in SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username",str).apply();

        //go to second activity
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
