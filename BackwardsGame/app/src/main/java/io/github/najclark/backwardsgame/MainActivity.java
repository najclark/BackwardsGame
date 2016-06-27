package io.github.najclark.backwardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button original, timetrials, shootout, localmatch;
    int difficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        original = (Button) findViewById(R.id.original); //time limits
        timetrials = (Button) findViewById(R.id.timetrials); //unlimited time
        shootout = (Button) findViewById(R.id.shootout); //2 player online game
        localmatch = (Button) findViewById(R.id.localmatch); //2 player local game
    }

    public void onClick(View v) {

        if(v.getId() == original.getId()){

            final CharSequence[] items = {"Easy", "Medium", "Hard"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Make your selection");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                    if(items[item].toString().equals("Easy")){
                        difficulty = 1;
                    }
                    else if(items[item].toString().equals("Medium")){
                        difficulty = 2;
                    }
                    else if(items[item].toString().equals("Hard")){
                        difficulty = 3;
                    }

                    Intent myIntent = new Intent(MainActivity.this, Original.class);
                    myIntent.putExtra("difficulty", String.valueOf(difficulty));
                    MainActivity.this.startActivity(myIntent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(v.getId() == timetrials.getId()){

        }
        else if(v.getId() == shootout.getId()){

        }
        else if(v.getId() == localmatch.getId()){
            Toast.makeText(this, "Clicked on Local match", Toast.LENGTH_SHORT).show();

        }
        difficulty = 0;
    }
}
