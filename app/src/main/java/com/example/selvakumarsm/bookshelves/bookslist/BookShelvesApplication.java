package com.example.selvakumarsm.bookshelves.bookslist;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

//Single entry class which acts as Singleton class as well for providing network apis
public class BookShelvesApplication extends Application {

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;
    private static BookShelvesApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        requestQueue = getVolleyRequestQueue();

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                //TODO: Log the error and send it to the private server for further analysis.
//
//            }
//        });
    }

    public static Context getAppContext(){
        return context;
    }

    public static synchronized RequestQueue getVolleyRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public static synchronized ImageLoader getVolleyImageLoader() {
        if (imageLoader == null) {
            RequestQueue requestQueue = getVolleyRequestQueue();
            imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap>
                        cache = new LruCache<String, Bitmap>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });
        }
        return imageLoader;
    }
}
