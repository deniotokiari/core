package by.deniotokiari.android.core.helper;

import android.net.Uri;
import by.deniotokiari.android.core.annotation.contract.Contract;
import by.deniotokiari.android.core.provider.CoreProvider;

import java.lang.annotation.Annotation;

public class ContractHelper {

    public static final String URI_PREFIX = "content://" + CoreProvider.class.getName() + "/";
    public static final String TYPE_PREFIX = "vnd.android.cursor.dir/";

    public static Uri getUri(Class<?> contract) {
        Contract annotation = contract.getAnnotation(Contract.class);
        if (annotation.uri() != null) {
            return Uri.parse(annotation.uri());
        } else if (annotation.tableName() != null) {
            return Uri.parse(URI_PREFIX + annotation.tableName());
        } else {
            return Uri.parse(URI_PREFIX + contract.getName());
        }
    }

    public static String getType(Class<?> contract) {
        Contract annotation = contract.getAnnotation(Contract.class);
        if (annotation.type() != null) {
            return annotation.type();
        } else if (annotation.tableName() != null) {
            return TYPE_PREFIX + annotation.tableName();
        } else {
            return TYPE_PREFIX + contract.getName();
        }
    }

    public static String getTableName(Class<?> contract) {
        String tableName = contract.getAnnotation(Contract.class).tableName();
        if (tableName != null) {
            return tableName;
        } else {
            return contract.getName();
        }
    }

}
