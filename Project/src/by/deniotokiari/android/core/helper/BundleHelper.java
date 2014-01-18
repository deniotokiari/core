package by.deniotokiari.android.core.helper;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class BundleHelper {

    @SuppressWarnings("unchecked")
    public static <T> void put(String key, Bundle bundle, T value) {
        if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) value);
        } else if (value instanceof ArrayList<?>) {
            bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
        } else if (value instanceof String[]) {
            bundle.putStringArray(key, (String[]) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Bundle bundle) {
        return (T) bundle.get(key);
    }

}
