package com.eliasbagley.cursorexample.Dagger;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;

import com.eliasbagley.cursorexample.MainActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by eliasbagley on 2/2/15.
 */


@Module(
        includes = {
                APIModule.class,
                UiModule.class
        },
        injects = {
                MainActivity.class,
                TMApp.class,
        },
        library = true
)

public class AppModule {
    private final TMApp app;

    public AppModule(TMApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    NotificationManager provideNotificationManager() {
        return (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Timber.e("Failed to load image: %s", uri);
                    }
                })
                .build();
    }


}
