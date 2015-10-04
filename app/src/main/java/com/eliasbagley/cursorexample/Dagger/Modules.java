package com.eliasbagley.cursorexample.Dagger;

final class Modules {
    static Object[] list(TMApp app) {
        return new Object[] {
                new AppModule(app)
        };
    }

    private Modules() {
        // No instances.
    }
}
