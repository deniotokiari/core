package by.deniotokiari.android.core.annotation.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Contract {

    String tableName() default "";

    String uri() default "";

    String type() default "";

}
