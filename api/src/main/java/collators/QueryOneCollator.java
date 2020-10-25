package collators;

import com.hazelcast.mapreduce.Collator;

import java.util.HashMap;
import java.util.Map;

public class QueryOneCollator implements Collator<Map.Entry<String,Integer>, Map<String, Double>> {
    private final Map<String,Integer> neighbourhood;

    public QueryOneCollator(Map<String,Integer> neighbourhood){
        this.neighbourhood = neighbourhood;
    }

    @Override
    public Map<String, Double> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        Map<String,Double> toReturn = new HashMap<>();

        for(Map.Entry<String,Integer> entry : iterable){
            toReturn.put(entry.getKey(), (double) entry.getValue() / neighbourhood.get(entry.getKey()));
        }

        return toReturn;
    }
}
