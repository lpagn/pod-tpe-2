package ar.edu.itba.pod.client.utils;

import java.time.Instant;
import java.time.LocalDateTime;

public class QueryUtils {
    public static String now(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = Instant.now();
        String nano = String.format("%4d",localDateTime.getNano()).substring(0,4);
        return localDateTime.getDayOfMonth() + "/" + localDateTime.getMonth().getValue() + "/" + localDateTime.getYear() + " " + localDateTime.getHour()
                + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond()+ ":" + nano + " ";
    }
}
