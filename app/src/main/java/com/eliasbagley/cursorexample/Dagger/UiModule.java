package com.eliasbagley.cursorexample.Dagger;

import com.eliasbagley.cursorexample.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by eliasbagley on 2/2/15.
 */
@Module(
        injects = {
                MainActivity.class
        },
        complete = false,
        library = true
)
public class UiModule {
    @Provides @Singleton
    AppContainer provideAppContiner() {
        Timber.d("Providing default app container");
        return AppContainer.DEFAULT;
    }
}

