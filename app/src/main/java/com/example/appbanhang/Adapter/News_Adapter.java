package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbanhang.model.News_Item;
import com.example.appbanhang.R;

import java.util.List;

public class News_Adapter extends BaseAdapter {
    Context context;
    List <News_Item> news;

    public News_Adapter(Context baseContext, List<News_Item> news) {
        this.context = baseContext;
        this.news = news;
    }


    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            TextView txtTitle = view.findViewById(R.id.txt_Title);
            TextView txtDes = view.findViewById(R.id.txt_Text);

            News_Item item = news.get(i);
            txtTitle.setText(item.getTitle());
            txtDes.setText(item.getDescription());
            return view;

        }
        public void updateBaseAdapter (List<News_Item> list){
        this.news = list;
        notifyDataSetChanged();
        }

}
