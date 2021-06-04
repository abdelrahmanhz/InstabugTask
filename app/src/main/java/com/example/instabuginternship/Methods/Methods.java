package com.example.instabuginternship.Methods;

import android.text.Html;
import android.text.Spanned;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

    public static final String INSTABUG_URL = "https://www.instabug.com";
    public HashMap<String, Integer> wordsHashMap;

    public HashMap<String, Integer> getWords(){

            try {
                URL url = new URL(INSTABUG_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                StringBuilder output = new StringBuilder();
                String str;
                while ((str = br.readLine()) != null)
                    output.append(str);
                Pattern pattern = Pattern.compile("<body>(.*?)</body>");
                Matcher matcher = pattern.matcher(output.toString());
                matcher.find();
                Spanned spanned = Html.fromHtml(matcher.group(1), Html.FROM_HTML_MODE_LEGACY);
                String body = spanned.toString();
                String[] wordsArray = body.trim().split("\\s+");
                wordsHashMap = new HashMap<>();
                for (String word : wordsArray) {
                    Integer count = wordsHashMap.get(word);
                    if (count != null)
                        count++;
                    else {
                        count = 1;
                    }
                    wordsHashMap.put(word, count);
                }
                return wordsHashMap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return wordsHashMap;
    }



}
