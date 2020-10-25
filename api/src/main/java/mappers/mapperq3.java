package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

public class mapperq3 implements Mapper<Integer, Tree, String,Double> {

    @Override
    public void map(Integer integer, Tree tree, Context<String,Double> context) {
        context.emit(tree.getName(),tree.getDiameter());
        //System.out.println(tree.getName() + " " + tree.getDiameter());
    }
}
