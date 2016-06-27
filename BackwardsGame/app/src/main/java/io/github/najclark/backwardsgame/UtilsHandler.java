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

/**
 * Created by nicholas on 6/26/16.
 */
public class UtilsHandler {

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
        return original;
    }
}
