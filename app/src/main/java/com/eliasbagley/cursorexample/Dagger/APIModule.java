package com.eliasbagley.cursorexample.Dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.eliasbagley.cursorexample.ArticleAPI;
import com.eliasbagley.cursorexample.Network.MockServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

/**
 * Created by eliasbagley on 2/2/15.
 */

@Module(
        library = true,
        complete = false
)
public class APIModule {
    public static final String API_URL = "http://" + MockServer.getIPAddress(true) + ":8080";
//    public static final String API_URL = "http://localhost:8080";

    public static String getBaseUrl() {
        Timber.e("ip: " + API_URL);
        return API_URL;
    }

    public static String urlByAppendingPath(String path) {
        return getBaseUrl() + path;
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("Example", Context.MODE_PRIVATE);
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        // Sets the default timeout to 5 seconds
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        client.setReadTimeout(20, TimeUnit.SECONDS);

        return client;
    }

    @Provides @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides @Singleton
    Gson provideGson() {
        //Note there is a bug in GSON 2.3.1 that can cause a stack overflow when working with RealmObjects.
        // To work around this, use the ExclusionStrategy below or downgrade to 1.7.1
        // See more here: https://code.google.com/p/google-gson/issues/detail?id=440
        Gson gson = new GsonBuilder()
                .create();

        return gson;
    }

    @Provides @Singleton
    GsonConverter provideGsonConverter(final Gson g) {
        return new GsonConverter(g);
    }


    @Provides @Singleton
    RestAdapter provideRestAdapter(Client client, GsonConverter gsonConverter) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(client)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(new Endpoint() {
                    @Override
                    public String getUrl() {
                        return API_URL;
                    }

                    @Override
                    public String getName() {
                        return "Localhost";
                    }
                })
                .setConverter(gsonConverter)
                .build();

        return restAdapter;
    }


    @Provides @Singleton
    ArticleAPI provideArticleAPI(RestAdapter restAdapter) {
        return restAdapter.create(ArticleAPI.class);
    }

}

