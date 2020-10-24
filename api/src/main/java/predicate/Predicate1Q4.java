package predicate;

import com.hazelcast.mapreduce.KeyPredicate;

public class Predicate1Q4 implements KeyPredicate<String> {
    String specie;

    Predicate1Q4(String specie){
        this.specie=specie;
    }

    @Override
    public boolean evaluate( String s ) {
        return s != null && s.toLowerCase().contains("specie");
    }
}