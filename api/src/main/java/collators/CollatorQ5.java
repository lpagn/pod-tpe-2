package collators;

import com.hazelcast.mapreduce.Collator;
import models.Pair;
import models.Q5ans;

import java.util.*;
import java.util.stream.Collectors;

public class CollatorQ5 implements Collator<Map.Entry<String, Integer>, List<Q5ans>> {
    public CollatorQ5(){
    }

    @Override
    public List<Q5ans> collate(Iterable<Map.Entry<String, Integer>> values ) {
        List<Q5ans> list = new LinkedList<>();
        for (Map.Entry<String, Integer> entryA : values) {
            for(Map.Entry<String, Integer> entryB : values){
                if(entryA.getValue().equals(entryB.getValue()) && entryA.getKey().compareTo(entryB.getKey())<0 && !entryA.getKey().equals(entryB.getKey())){
                    Q5ans q5ans = new Q5ans(entryA.getValue(),entryA.getKey(),entryB.getKey());
                    list.add(q5ans);
                }
            }
        }

        return list.stream().sorted(Q5ans::compareTo).collect(Collectors.toList());

    }
}
