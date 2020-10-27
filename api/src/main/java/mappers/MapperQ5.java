package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Tree;

public class MapperQ5 implements Mapper<Integer, Tree, String,Integer> {

    @Override
    public void map(Integer integer, Tree tree, Context<String,Integer> context) {
        context.emit(tree.getNeighbourhood(),1);
    }
}
