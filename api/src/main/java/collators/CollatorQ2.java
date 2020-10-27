package collators;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class CollatorQ2 implements Collator<Map.Entry<Map.Entry<String,String>,Integer>, Set<Map.Entry<String,String>>> {

    int min;
    public CollatorQ2(int min){
        this.min = min;
    }

    @Override
    public Set<Map.Entry<String, String>> collate(Iterable<Map.Entry<Map.Entry<String, String>, Integer>> iterable) {
        Map<String,Map<String,Integer>> m = new HashMap<>();

        for(Map.Entry<Map.Entry<String, String>, Integer> p : iterable){
            m.putIfAbsent(p.getKey().getKey(),new HashMap<>());
            m.get(p.getKey().getKey()).putIfAbsent(p.getKey().getValue(),p.getValue());
        }

        TreeSet<Map.Entry<String,String>> s = new TreeSet<>(Map.Entry.comparingByKey());

        for(String neigh : m.keySet()){
            Map.Entry<String,Integer> e= getMax(m.get(neigh));
            if(e.getValue()>min){
                s.add(new AbstractMap.SimpleEntry<>(neigh,e.getKey()+";"+e.getValue()));
            }
        }


        return s;
    }

    private <K, V extends Comparable<? super V>> Map.Entry<K,V> getMax(Map<K,V> map){
        Map.Entry<K, V> maxEntry = null;

        for (Map.Entry<K, V> entry : map.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

}
