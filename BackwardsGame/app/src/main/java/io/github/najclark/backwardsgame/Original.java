package io.github.najclark.backwardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Original extends AppCompatActivity {

    AsyncTask<Void, Integer, Void> aProgressUpdate;
    ArrayList<String> dictionary;
    ArrayList<Integer> words;
    ProgressBar pg;
    Button btn1, btn2, btn3, btn4;
    Button[] btns = new Button[4];
    TextView tvOut, tvScore;
    int difficulty, correctIndex, score, games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);

        aProgressUpdate = new asyncTaskUpdateProgress();
        dictionary = new ArrayList<String>();
        words = new ArrayList<Integer>();

        Intent intent = getIntent();
        difficulty = Integer.parseInt(intent.getStringExtra("difficulty")); //get difficulty modifier

        pg = (ProgressBar) findViewById(R.id.pgOriginal);
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
        game();
    }

    @Override
    protected void onDestroy() {
        aProgressUpdate.cancel(true);
        super.onDestroy();
    }

    public void game(){
        tvScore.setText(score + "/" + games);
        if(games < 10) {
            Random r = new Random();
            String word = dictionary.get(r.nextInt(dictionary.size() - 1)).toUpperCase();
            tvOut.setText(word);

            aProgressUpdate.cancel(true);
            aProgressUpdate = new asyncTaskUpdateProgress();
            aProgressUpdate.execute();

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
            aProgressUpdate.cancel(true);
            double weightedScore = score*difficulty*UtilsHandler.avg(words);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You got " + tvScore.getText() + " questions right. The weighted score is: " + weightedScore)
                    .setCancelable(false)
                    .setPositiveButton("OK, I go it!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myIntent = new Intent(Original.this, MainActivity.class);
                            Original.this.startActivity(myIntent);
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

    public class asyncTaskUpdateProgress extends AsyncTask<Void, Integer, Void> {

        int progress = 100;
        int time;

        @Override
        protected void onPostExecute(Void result) {
            game();
        }

        @Override
        protected void onPreExecute() {
            pg.setProgress(progress);
            time = 10000 / (difficulty);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pg.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            while (progress > 0) {
                if (this.isCancelled()) break;
                if (time / 100 < 50) {
                    progress -= 5;
                    publishProgress(progress);
                    SystemClock.sleep(time / 20);
                } else {
                    progress -= 1;
                    publishProgress(progress);
                    SystemClock.sleep(time / 100);
                }
            }
            return null;
        }
    }
}
