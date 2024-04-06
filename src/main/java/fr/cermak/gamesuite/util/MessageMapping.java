package fr.cermak.gamesuite.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageMapping {
    byte path() default 0;
}
