package io.github.najclark.backwardsgame;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nicholas on 6/26/16.
 */
public class UtilsHandler {

    private static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l","m", "n", "o", "p", "q", "r", "s", "t", "u", "w", "x", "y", "z"};

    public static ArrayList<String> populateDictionary(Context context){
        ArrayList<String> dictionary = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        BufferedReader br;
        try {
            Resources res = context.getResources();
            InputStream ins = res.openRawResource(R.raw.dict);
            br = new BufferedReader(new InputStreamReader(ins));

            String str;
            while ((str = br.readLine()) != null) {
                dictionary.add(str);
            }
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public static String mangleText(String original){
        //TODO, complete method.
        Random r = new Random();
        String changed = "";
        for(int i = 0; i < r.nextInt(3)+1; i++){
            int changeIndex = r.nextInt(original.length());
            changed = original.substring(0, changeIndex) + alphabet[r.nextInt(alphabet.length)].toUpperCase() + original.substring(changeIndex+1);
        }
        return changed;
    }

    public static void toastIt(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static double avg(ArrayList<Integer> list){
        double sum = 0;
        for(int i : list){
            sum += i;
        }
        return sum/list.size();
    }
}
