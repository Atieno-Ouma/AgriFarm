package com.fyp.agrifarm.News;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.agrifarm.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadNews extends AsyncTask<Void, Void, Void> {
    String imagelink="";
    protected Void doInBackground(Void... voids) {
        String[] links=new String[] {
                "http://blog.agcocorp.com/category/agco/feed/",
                "http://www.mediterraneadeagroquimicos.cat/wordpress_2/feed/",
                "http://sustainableagriculture.net/blog/feed/",
        };

        for (String link : links) {
        try {

            RequestQueue queue= Volley.newRequestQueue(MainActivity.getAppContext());
            StringRequest request=new StringRequest(link, response -> {
                Document document= Jsoup.parse(response);
                Elements itemElements =document.getElementsByTag("item");
                for (int i = 0;i<itemElements.size();i++){
                    Element item=itemElements.get(i);
                    String title=item.getElementsByTag("title").text();
                    String publishdate=item.getElementsByTag("pubDate").text();
                    String description=item.getElementsByTag("description").text();
                    String guid=item.getElementsByTag("guid").text();
//                    String link=item.getElementsByTag("link").text();
                    String content=item.getElementsByTag("content:encoded").text();

                    try{
                        Log.i("one", imagelink);
                        Elements imageurl=item.getElementsByTag("image");
                        if (imagelink.isEmpty()){
                            throw new NullPointerException(imagelink);
                        }
                    }
                    catch (NullPointerException decs)
                    {
                        try{
                            Log.i("two", imagelink);
                            Elements srcs = Jsoup.parse(content).select("[src]");
                            imagelink=(srcs.get(0).attr("abs:src"));
                            if (imagelink.isEmpty()){
                                throw new NullPointerException(imagelink);
                            }
                        }
                        catch (Exception contnt)
                        {
                            try {
                                Log.i("three", imagelink);
                                Elements srcs = Jsoup.parse(description).select("[src]"); //get All tags containing "src"
                                imagelink = (srcs.get(0).attr("abs:src"));
                                if (imagelink.isEmpty()){
                                    throw new NullPointerException(imagelink);
                                }
                            }
                            catch (Exception e){
                                imagelink="https://blog.agcocorp.com/wp-content/uploads/2019/07/190717-CropTourTillage-Fig2.jpg";
                            }
                        }
                    }

                    NewsViewModel ViewModel = new NewsViewModel((Application) MainActivity.getAppContext());
                    ViewModel.insert(new NewsEntity(title, description, imagelink,publishdate));
                    imagelink="";
                }
            }, error -> {
                Log.i("error", "onErrorResponse: " + error.getMessage());
                error.printStackTrace();
            });
            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }}

        return null;
    }
}