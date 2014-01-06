package by.deniotokiari.android.core.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import by.deniotokiari.android.core.R;

public class ManifestHelper {

    public static boolean isDebugMode(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String data = bundle.getString(context.getResources().getString(R.string.DEBUG_MODE), "0");
            return data.equals("1");
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
