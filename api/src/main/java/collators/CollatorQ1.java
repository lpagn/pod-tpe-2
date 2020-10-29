package collators;

import com.hazelcast.mapreduce.Collator;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CollatorQ1 implements Collator<Map.Entry<String,Integer>, List<Map.Entry<String, Double>>> {
    private final Map<String,Integer> neighbourhood;

    public CollatorQ1(Map<String,Integer> neighbourhood){
        this.neighbourhood = neighbourhood;
    }

    @Override
    public List<Map.Entry<String, Double>> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        Map<String,Double> beforeReturn = new TreeMap<>();
        DecimalFormat df = new DecimalFormat("#.##");

        for(Map.Entry<String,Integer> entry : iterable){
            beforeReturn.put(entry.getKey(), Double.valueOf(df.format((double) entry.getValue() / neighbourhood.get(entry.getKey()))));
        }

        List<Map.Entry<String, Double>> toReturn = new ArrayList<>(beforeReturn.entrySet());
        return toReturn.stream().sorted(new EntryComparator()).collect(Collectors.toList());
    }

    private static class EntryComparator implements Comparator<Map.Entry<String,Double>> {

        @Override
        public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
            if(Double.compare(o1.getValue(), o2.getValue()) == 0){
                return o1.getKey().compareTo(o2.getKey());
            }

            return -1 * Double.compare(o1.getValue(), o2.getValue());
        }

    }
}
