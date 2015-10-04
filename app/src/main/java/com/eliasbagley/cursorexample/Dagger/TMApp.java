package com.eliasbagley.cursorexample.Dagger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.app.Application;
import com.eliasbagley.cursorexample.BuildConfig;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by eliasbagley on 2/2/15.
 */
public class TMApp extends Application {
    public static Context appContext;
    public static TMApp tmApp;
    private ObjectGraph objectGraph;

    @Inject Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


        appContext = getBaseContext();
        tmApp = this;

        initialize();
    }

    public void initialize() {
        buildObjectGraphAndInject();
        bus.register(this);
    }

    private void buildObjectGraphAndInject() {
        objectGraph = ObjectGraph.create(Modules.list(this));
        objectGraph.inject(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public static TMApp get(Context context) {
        return (TMApp) context.getApplicationContext();
    }

    public static void injectActivity(Activity activity) {
        ButterKnife.inject(activity);
        get(activity).inject(activity);
    }

    public static void injectFragment(Fragment fragment, View view) {
        tmApp.inject(fragment);
        ButterKnife.inject(fragment, view);
    }

    public static void injectView(View view) {
        tmApp.inject(view);
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

    public static void shortToast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
    }

    public static ProgressDialog loadingHud;

    public static void showLoadingHUD(Context context) {

        if (loadingHud != null) {
            return;
        }

        loadingHud = new ProgressDialog(context);
        loadingHud.setMessage("Loading...");
        loadingHud.setCancelable(false);
        loadingHud.show();
    }

    public static void hideLoadingHUD() {
        if (loadingHud != null) {
            loadingHud.dismiss();
            loadingHud = null;
        }
    }
}

