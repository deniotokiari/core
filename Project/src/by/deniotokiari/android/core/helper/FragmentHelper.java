package by.deniotokiari.android.core.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentHelper {

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T getInstance(Context context, Bundle bundle, Class<T> fragment) {
        Fragment resultFragment = Fragment.instantiate(context, fragment.getName(), bundle);
        return (T) resultFragment;
    }

    public static <T> T findFirstResponderFor(FragmentActivity activity, Fragment fragment, Class<T> clazz) {
        if (activity == null) {
            return null;
        }
        if (clazz.isInstance(activity)) {
            return clazz.cast(activity);
        }
        Fragment localFragment = fragment.getParentFragment();
        while (localFragment != null) {
            if (clazz.isInstance(localFragment)) {
                return clazz.cast(localFragment);
            }
            localFragment = localFragment.getParentFragment();
        }
        return null;
    }

}
