package com.example.instabuginternship.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.instabuginternship.R;
import java.util.HashMap;


public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.ViewHolder> {

    private HashMap<String, Integer> words;
    private String[] mKeys;

    public WordsListAdapter(HashMap<String, Integer> words) {
        this.words = words;
        mKeys = words.keySet().toArray(new String[words.size()]);
    }

    public void setWords(HashMap<String, Integer> words) {

        this.words = words;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.wordContent.setText(mKeys[position]);
        holder.wordCount.setText(words.get(mKeys[position]).toString());
        Log.i("HashMap Value : " , words.get(mKeys[position]).toString());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder{

        TextView wordContent, wordCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordContent = itemView.findViewById(R.id.word_content);
            wordCount = itemView.findViewById(R.id.word_count);
        }
    }
}
