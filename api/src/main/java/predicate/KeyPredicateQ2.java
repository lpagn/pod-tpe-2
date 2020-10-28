package predicate;

import com.hazelcast.mapreduce.KeyPredicate;

import java.util.Map;
import java.util.Set;

public class KeyPredicateQ2 implements KeyPredicate<Map.Entry<String,String>> {

    private Set<String> neighs;

    public KeyPredicateQ2(Set<String> neighs){
        this.neighs = neighs;
    }

    @Override
    public boolean evaluate(Map.Entry<String, String> key) {
        return neighs.contains(key.getValue());
    }
}
