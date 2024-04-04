package fr.cermak.gamesuite.utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageMapping {
    String path() default "default";
}
