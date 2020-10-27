package collators;

import com.hazelcast.mapreduce.Collator;
import models.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollatorQ3 implements Collator<Map.Entry<String, Double>, List<Pair<String,Double>>> {
    int N;
    public CollatorQ3(int N){
        this.N = N;
    }

    @Override
    public List<Pair<String,Double>> collate( Iterable<Map.Entry<String, Double>> values ) {
        List<Pair<String,Double>> list= new LinkedList<>();

        for ( Map.Entry<String, Double> entry : values ) {
            list.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        return list.stream().sorted((x1, x2) -> x2.getValue().compareTo(x1.getValue())).collect(Collectors.toList()).subList(0,N);
    }
}


