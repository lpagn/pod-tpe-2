package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Tree;

public class Mapper1Q4 implements Mapper<Integer, Tree, String,Integer> {
    private final String NAME;

    public Mapper1Q4(String name){
        this.NAME=name;
    }

    @Override
    public void map(Integer key, Tree tree, Context<String, Integer> context) {
        if(tree.getName().equals(NAME)) {
            context.emit(tree.getNeighbourhood(), 1);
        }
    }
}
