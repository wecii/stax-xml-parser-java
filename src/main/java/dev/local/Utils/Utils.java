package dev.local.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static void log(String ctext, String className){
        System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS").format(LocalDateTime.now()) +" -- { "+className+" } -- "+ctext);
    }

}
