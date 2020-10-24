package collators;

import com.hazelcast.mapreduce.Collator;

import java.util.HashMap;
import java.util.Map;

public class Collator1Q4 implements Collator<Map.Entry<String, Long>, Map<String,String>> {
    @Override
    public Map<String, String> collate( Iterable<Map.Entry<String, Long>> values ) {
        Map<String,String> map= new HashMap<>();
        long sum = 0;
        for ( Map.Entry<String, Long> entry : values ) {
            sum += entry.getValue().longValue();
        }
        return map;
    }
}