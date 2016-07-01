package io.github.najclark.backwardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LocalMatch extends AppCompatActivity {

    ArrayList<String> dictionary;
    ArrayList<Integer> words;
    ProgressBar pg;
    Button btn1Top, btn2Top, btn3Top, btn4Top, btn1Bottom, btn2Bottom, btn3Bottom, btn4Bottom;
    Button[] btnsTop = new Button[4];
    Button[] btnsBottom = new Button[4];
    TextView tvTop, tvBottom;
    LinearLayout llTop, llBottom;
    int difficulty, correctIndex, topScore, bottomScore, games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_match);

        dictionary = new ArrayList<String>();
        words = new ArrayList<Integer>();

        dictionary = UtilsHandler.populateDictionary(this);

        btn1Top = (Button) findViewById(R.id.btn1Top);
        btn2Top = (Button) findViewById(R.id.btn2Top);
        btn3Top = (Button) findViewById(R.id.btn3Top);
        btn4Top = (Button) findViewById(R.id.btn4Top);
        tvTop = (TextView) findViewById(R.id.tvTop);

        tvBottom = (TextView) findViewById(R.id.tvBottom);
        btn1Bottom = (Button) findViewById(R.id.btn1Bottom);
        btn2Bottom = (Button) findViewById(R.id.btn2Bottom);
        btn3Bottom = (Button) findViewById(R.id.btn3Bottom);
        btn4Bottom = (Button) findViewById(R.id.btn4Bottom);

        btnsTop[0] = btn1Top;
        btnsTop[1] = btn2Top;
        btnsTop[2] = btn3Top;
        btnsTop[3] = btn4Top;

        btnsBottom[0] = btn1Bottom;
        btnsBottom[1] = btn2Bottom;
        btnsBottom[2] = btn3Bottom;
        btnsBottom[3] = btn4Bottom;

        llTop = (LinearLayout) findViewById(R.id.llTop);
        llBottom = (LinearLayout) findViewById(R.id.llBottom);
        Canvas c = new Canvas();
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        c.drawText("0", 0, 0, p);
        llBottom.draw(c);

        game();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void game(){
        //tvScore.setText(score + "/" + games);
        if(games < 10) {
            Random r = new Random();
            String word = dictionary.get(r.nextInt(dictionary.size() - 1)).toUpperCase();
            tvTop.setText(word);
            tvBottom.setText(word);

            word = new StringBuilder(word).reverse().toString();

            correctIndex = r.nextInt(btnsTop.length);
            for (int i = 0; i < btnsBottom.length; i++) {
                if (i == correctIndex) {
                    btnsTop[i].setText(word);
                    btnsBottom[i].setText(word);
                    words.add(word.length());
                } else {
                    String mangled = UtilsHandler.mangleText(word);
                    btnsTop[i].setText(mangled);
                    btnsBottom[i].setText(mangled);
                }
            }
            games++;
        }
        else{
            double topWScore = topScore*UtilsHandler.avg(words);
            double bottomWScore =bottomScore*UtilsHandler.avg(words);
            String msg = "";
            if(topScore > bottomScore){
                msg = "Congradulations Top Player, you won! You got " + topScore + " points, earning a weighted score of: " + topWScore;
            }
            else if (bottomScore > topScore){
                msg = "Congradulations Bottom Player, you won! You got " + bottomScore + " points, earning a weighted score of: " + bottomWScore;
            }
            else{
                msg = "Congradulations to both players, you tied! You both got " + topScore + " points, earning a weighted score of: " + bottomWScore;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK, I go it!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myIntent = new Intent(LocalMatch.this, MainActivity.class);
                            LocalMatch.this.startActivity(myIntent);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void onClick(View v){
        if(v.getId() == btnsTop[correctIndex].getId()){
            UtilsHandler.toastIt(this, "Correct Button!");
            //TODO make correct button turn green
            topScore++;
            game();
        }
        else if(v.getId() == btnsBottom[correctIndex].getId()){
            UtilsHandler.toastIt(this, "Correct Button!");
            //TODO make correct button turn green
            bottomScore++;
            game();
        }
        else {
            for(Button b : btnsTop){
                if(v.getId() == b.getId()){
                    topScore--;
                    break;
                }
            }
            for(Button b : btnsBottom){
                if(v.getId() == b.getId()){
                    bottomScore--;
                    break;
                }
            }
            UtilsHandler.toastIt(this, "Wrong Button!");
            game();
        }
    }

//    public void buttonColor(Button b, long millis){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                b.setBackgroundColor(Color.GREEN);
//            }
//        }).start();
//
//
//    }
}
