package predicate;

import com.hazelcast.mapreduce.KeyPredicate;
import models.Pair;

public class Predicate1Q4 implements KeyPredicate<Pair<Integer,String>> {
    String specie;

    public Predicate1Q4(String specie){
        this.specie=specie;
    }

    @Override
    public boolean evaluate(Pair<Integer,String> s ) {
        return s != null && s.getValue().contains(specie);
    }
}