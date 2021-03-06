package com.davidju.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to manage status of idling resources in app for UI testing.
 */
public class AppIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean b) {
        isIdleNow.set(b);
        if (b && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
