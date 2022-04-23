package com.example.autiobook;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OB2JOGoybntkTfGtZ7c5HuZVtOxLY4Wl9SMQZs45")
                .clientKey("wXZCw3z3eDf7td6KCS9xlRGHyHRHXYJ9dSlkfleh")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
