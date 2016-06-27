package io.github.najclark.backwardsgame;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    ArrayList<String> dictionary;
    ProgressBar pg;
    Button btn1, btn2, btn3, btn4;
    TextView tvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);

        dictionary = new ArrayList<String>();

        Intent intent = getIntent();
        String value = intent.getStringExtra("difficulty"); //get difficulty modifier

        pg = (ProgressBar) findViewById(R.id.pgOriginal);
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                pg.setProgress(50);
            }
        });
        dictionary = UtilsHandler.populateDictionary(this);

        btn1 = (Button) findViewById(R.id.btn1Original);
        btn2 = (Button) findViewById(R.id.btn2Original);
        btn3 = (Button) findViewById(R.id.btn3Original);
        btn4 = (Button) findViewById(R.id.btn4Original);
        tvOut = (TextView) findViewById(R.id.tvOriginal);
        game();
    }

    public void game(){
        String word = dictionary.get(new Random().nextInt(dictionary.size()-1)).toUpperCase();
        tvOut.setText(word);
    }
}
