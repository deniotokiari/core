package by.deniotokiari.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractFragment extends Fragment {

    protected abstract void onViewCreated(View view);

    protected abstract int getViewLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(getViewLayout(), container, false);
        onViewCreated(view);
        return view;
    }

    protected <T> T findFirstResponderFor(Class<T> clazz) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return null;
        }
        if (clazz.isInstance(activity)) {
            return clazz.cast(activity);
        }
        Fragment fragment = getParentFragment();
        while (fragment != null) {
            if (clazz.isInstance(fragment)) {
                return clazz.cast(fragment);
            }
            fragment = fragment.getParentFragment();
        }
        return null;
    }

}
