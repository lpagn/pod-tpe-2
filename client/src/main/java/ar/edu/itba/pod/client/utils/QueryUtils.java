package ar.edu.itba.pod.client.utils;

import java.time.Instant;
import java.time.LocalDateTime;

public class QueryUtils {
    public static String now(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = Instant.now();
        return localDateTime.getDayOfMonth() + "/" + localDateTime.getMonth().getValue() + "/" + localDateTime.getYear() + " " + localDateTime.getHour()
                + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond()+ ":" + localDateTime.getNano() + " ";
    }
}
