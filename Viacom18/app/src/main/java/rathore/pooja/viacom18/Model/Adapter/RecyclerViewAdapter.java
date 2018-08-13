package rathore.pooja.viacom18.Model.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rathore.pooja.viacom18.Model.Pojo.ListData;
import rathore.pooja.viacom18.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    Context context;
    int listSize;
    private ArrayList<ListData> listDataArrayList;

    public void setData(ArrayList<ListData> listData, int listSize) {
        this.listDataArrayList = listData;
        this.listSize = listSize;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_layout, viewGroup, false);
        context = viewGroup.getContext();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String wordName = listDataArrayList.get(position).getWordName();
        String wordCount = String.valueOf(listDataArrayList.get(position).getWordCount());
        holder.wordName.setText(wordName);
        holder.wordCount.setText(wordCount);
    }

    @Override
    public int getItemCount() {

        return listSize;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView wordName, wordCount;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            wordName = (TextView) itemView.findViewById(R.id.wordName);
            wordCount = (TextView) itemView.findViewById(R.id.wordCount);
        }

    }

}
