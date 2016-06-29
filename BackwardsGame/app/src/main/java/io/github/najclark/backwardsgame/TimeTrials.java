package io.github.najclark.backwardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TimeTrials extends AppCompatActivity {

    ArrayList<String> dictionary;
    ArrayList<Integer> words;
    ProgressBar pg;
    Button btn1, btn2, btn3, btn4;
    Button[] btns = new Button[4];
    TextView tvOut, tvScore;
    int difficulty, correctIndex, score, games;
    long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);

        dictionary = new ArrayList<String>();
        words = new ArrayList<Integer>();

        pg = (ProgressBar) findViewById(R.id.pgOriginal);
        pg.setEnabled(false);

        dictionary = UtilsHandler.populateDictionary(this);

        btn1 = (Button) findViewById(R.id.btn1Original);
        btn2 = (Button) findViewById(R.id.btn2Original);
        btn3 = (Button) findViewById(R.id.btn3Original);
        btn4 = (Button) findViewById(R.id.btn4Original);
        tvOut = (TextView) findViewById(R.id.tvOutputOriginal);
        tvScore = (TextView) findViewById(R.id.tvScoreOriginal);

        btns[0] = btn1;
        btns[1] = btn2;
        btns[2] = btn3;
        btns[3] = btn4;

        start = System.currentTimeMillis();
        game();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void game(){
        tvScore.setText(score + "/" + games);
        if(games < 10) {
            Random r = new Random();
            String word = dictionary.get(r.nextInt(dictionary.size() - 1)).toUpperCase();
            tvOut.setText(word);

            word = new StringBuilder(word).reverse().toString();

            correctIndex = r.nextInt(btns.length);
            for (int i = 0; i < btns.length; i++) {
                if (i == correctIndex) {
                    btns[i].setText(word);
                    words.add(word.length());
                } else {
                    btns[i].setText(UtilsHandler.mangleText(word));
                }
            }
            games++;
        }
        else{
            long timeTaken = ((System.currentTimeMillis()-start)/1000);
            double weightedScore = timeTaken/score;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You got " + tvScore.getText() + " questions right in " + ((System.currentTimeMillis()-start)/1000) + "s. Thats a correct answer every " + weightedScore + "s!")
                    .setCancelable(false)
                    .setPositiveButton("OK, I go it!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myIntent = new Intent(TimeTrials.this, MainActivity.class);
                            TimeTrials.this.startActivity(myIntent);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void onClick(View v){
        if(v.getId() == btns[correctIndex].getId()){
            UtilsHandler.toastIt(this, "Correct Button!");
            score++;
            game();
        }
        else {
            UtilsHandler.toastIt(this, "Wrong Button!");
            game();
        }
    }
}
