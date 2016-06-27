package io.github.najclark.backwardsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by nicholas on 6/25/16.
 */
public class WordBackground extends View {

    ArrayList<Word> words = new ArrayList<Word>();
    ArrayList<String> dictionary = new ArrayList<String>();
    int runs = 0;

    public WordBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init(canvas);
    }

    private void init(Canvas canvas){
        if(runs == 0){
            BufferedReader br;
//            try {
//                URL url = new URL("http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt".toString());
//                br = new BufferedReader(new InputStreamReader(url.openStream()));

            String str;
//                while ((str = br.readLine()) != null) {
//                    dictionary.add(str);
//                }
            for(int i = 0; i < 20; i++){
                dictionary.add("Hello");
            }
//                br.close();

//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            runs++;
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawARGB(255, 0, 0, 0);
        paint.setColor(Color.WHITE);

        if(words.size() < 20){
            for(int i = 20-words.size(); i > 0; i--) {
                Random r = new Random();
                words.add(new Word(dictionary.get(r.nextInt(dictionary.size() - 1)), r.nextInt(canvas.getWidth()) + 1, 20, r.nextInt(10) + 1, r.nextInt(30) + 20));
            }
        }

        for (int i = 0; i < words.size(); i++) {
            Word w = words.get(i);
            w.moveDown();
            paint.setTextSize(w.getSize());
            canvas.drawText(w.getText(), w.getX(), w.getY(), paint);
            Log.d("WordBackground", w.getText());
        }
        //init(canvas);
    }

}

