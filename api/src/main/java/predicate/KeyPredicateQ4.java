package predicate;

import com.hazelcast.mapreduce.KeyPredicate;
import models.Pair;

public class KeyPredicateQ4 implements KeyPredicate<Pair<Integer,String>> {
    String specie;

    public KeyPredicateQ4(String specie){
        this.specie=specie;
    }

    @Override
    public boolean evaluate(Pair<Integer,String> s ) {
        return s != null && s.getValue().toLowerCase().contains(specie.toLowerCase());
    }
}