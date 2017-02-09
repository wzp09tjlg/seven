package com.jia.seven.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


/**
 * by tiny
 */
@SuppressWarnings("All")
public class ActivityUtils {
    /**
     * add fragment to activity
     *
     * @param transaction transaction
     * @param fragment    fragment
     * @param frameId     layout id
     */
    public static void addFragmentToActivity(@NonNull FragmentTransaction transaction, @NonNull Fragment fragment, int frameId) {
        addFragmentToActivity(transaction, fragment, frameId, true);
    }

    /**
     * add fragment to activity with back stack
     *
     * @param transaction          transaction
     * @param fragment             fragment
     * @param frameId              layout id
     * @param enabledFragmentStack is added to the back stack
     */
    public static void addFragmentToActivity(
            @NonNull FragmentTransaction transaction, @NonNull Fragment fragment, int frameId, boolean enabledFragmentStack) {
        transaction.add(frameId, fragment, fragment.getClass().getSimpleName());
        if (enabledFragmentStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * repalce fragment
     *
     * @param transaction transaction
     * @param fragment    fragment
     * @param frameId     layout id
     */
    public static void replaceFragmentToActivity(@NonNull FragmentTransaction transaction, @NonNull Fragment fragment, int frameId) {
        transaction.replace(frameId, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * jump to others activity
     *
     * @param context       context
     * @param activityClass activity.class
     */
    public static void jumpActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    /**
     * jump to others activity
     *
     * @param context       context
     * @param activityClass activity.class
     * @param Bundle        bundle
     */
    public static void jumpActivity(Context context, Class activityClass, Bundle bundle){
        Intent intent = new Intent(context, activityClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
