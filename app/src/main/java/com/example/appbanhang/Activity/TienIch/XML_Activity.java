package com.example.appbanhang.Activity.TienIch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhang.Adapter.News_Adapter;
import com.example.appbanhang.R;
import com.example.appbanhang.model.News_Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XML_Activity extends AppCompatActivity {
    private ListView lvNews;
    public News_Adapter baseAdapter;
    public List<News_Item> news = new ArrayList<>();
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        baseAdapter = new News_Adapter(getBaseContext(),news);
        new FeedService().execute("https://vnexpress.net/rss/so-hoa.rss");
//        new FeedService().execute("https://thanhnien.vn/rss/video/the-gioi-348.rss");

//        toolbar = findViewById(R.id.toobar_XML);

        lvNews = findViewById(R.id.lv_News);
        lvNews.setAdapter(baseAdapter);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String link = news.get(i).getLink();
                Intent intent = new Intent(XML_Activity.this, WedView_Activity.class);
                intent.putExtra( "link", link);
                startActivity(intent);
            }
        });
    }




    public  List <News_Item> feedRss(String url) throws IOException, XmlPullParserException {
        ArrayList <News_Item> list = new ArrayList<>();
        InputStream stream;
        stream = new URL(url).openConnection().getInputStream();
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(stream,"UTF-8");


        News_Item item = new News_Item();
        String text = "";

        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = parser.getName();
            switch (eventType){
                case XmlPullParser.START_TAG:
                    System.out.println("START_TAG");
                    if (name.equalsIgnoreCase("item")){
                        item = new News_Item();
                    }
                    break;
                case XmlPullParser.TEXT:
                    System.out.println("TEXT");
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    System.out.println("END_TAG");
                    if (name.equalsIgnoreCase("item")){
                        list.add(item);
                    }

                    if(name.equalsIgnoreCase("title")){
                        if (text != null){
                            item.setTitle(text);
                        }
                    }


                    else if(name.equalsIgnoreCase("description")){
                        if (text != null){
                            item.setDescription(text.replaceAll("^<.*./br>",""));
                        }


                    }else if(name.equalsIgnoreCase("link")){
                        if (text != null){
                            item.setLink(text);
                        }

                    }
                    break;
            }
            eventType = parser.next();

        }
        return list;
    }
    class FeedService extends AsyncTask<String,Integer,Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            List <News_Item> items = new ArrayList<>();
            try {
                news = feedRss(strings[0]);

            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println(items.size());
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            baseAdapter.updateBaseAdapter(news);


        }
    }
}