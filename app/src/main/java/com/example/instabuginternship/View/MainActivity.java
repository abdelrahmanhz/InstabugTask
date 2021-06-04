package com.example.instabuginternship.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.instabuginternship.Adapter.WordsListAdapter;
import com.example.instabuginternship.Methods.Methods;
import com.example.instabuginternship.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private ProgressBar progressBar;
    private HashMap<String, Integer> words;
    WordsListAdapter wordsListAdapter;
    Methods methods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        new GetWordsTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuSortItem = menu.findItem(R.id.menu_sort_item);
        MenuItem menuSortDescItem = menu.findItem(R.id.menu_sort_desc_item);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if(words.containsKey(query))
                    Toast.makeText(MainActivity.this, "The word: " + query + "is already here " + words.get(query).toString(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "There Is No Such Word!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_sort_item:

                HashMap<String, Integer> temp = sortByValue(words);
                Log.i("sort", temp.toString());
                wordsListAdapter.setWords(temp);
                wordsListAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_sort_desc_item:

                wordsListAdapter.setWords(methods.sortDescByValue(words));
                wordsListAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashMap)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hashMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> tempHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            tempHashMap.put(aa.getKey(), aa.getValue());
        }
        return tempHashMap;
    }

    public HashMap<String, Integer> sortDescByValue(HashMap<String, Integer> hashMap)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hashMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> tempHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            tempHashMap.put(aa.getKey(), aa.getValue());
        }
        return tempHashMap;
    }


    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        list = findViewById(R.id.list);
    }

    class GetWordsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            words = new Methods().getWords();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            populateWordsIntoList(words);
        }
    }

    private void populateWordsIntoList(HashMap<String, Integer> words){

        wordsListAdapter = new WordsListAdapter(words);
        list.setAdapter(wordsListAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
    }
}

