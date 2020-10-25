package collators;

import com.hazelcast.mapreduce.Collator;
import models.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Collator1Q4 implements Collator<Map.Entry<String, Integer>, List<Pair<String,String>>> {
    static int min;

    public Collator1Q4(int min){
        Collator1Q4.min =min;
    }

    @Override
    public List<Pair<String,String>> collate( Iterable<Map.Entry<String, Integer>> values ) {
        List<Pair<String,String>> list= new LinkedList<>();
        for ( Map.Entry<String, Integer> entry1 : values ) {
            for (Map.Entry<String,Integer> entry2 : values){
                if(entry1.getKey().compareTo(entry2.getKey())<0 && entry1.getValue()>=min && entry2.getValue()>=min){
                    list.add(new Pair<>(entry1.getKey(), entry2.getKey()));
                }
            }
        }
        list.sort((a,b)->(a.getKey().compareTo(b.getKey())==0?a.getValue().compareTo(b.getValue()):a.getKey().compareTo(b.getKey())));
        return list;
    }
}