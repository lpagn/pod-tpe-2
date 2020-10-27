package predicate;

import com.hazelcast.mapreduce.KeyPredicate;
import models.Pair;

import java.util.Map;

public class KeyPredicateQ4 implements KeyPredicate<Map.Entry<Integer,String>> {
    String specie;

    public KeyPredicateQ4(String specie){
        this.specie=specie;
    }

    @Override
    public boolean evaluate(Map.Entry<Integer,String> s ) {
        return s != null && s.getValue().toLowerCase().contains(specie.toLowerCase());
    }
}