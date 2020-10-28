package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.AbstractMap;
import java.util.Map;

public class MapperQ3 implements Mapper<Integer, Tree, String, Map.Entry<Double,Integer>> {

    @Override
    public void map(Integer integer, Tree tree, Context<String, Map.Entry<Double, Integer>> context) {
        context.emit(tree.getName(), new AbstractMap.SimpleEntry<>(tree.getDiameter(), 1));
        //System.out.println(tree.getName() + " " + tree.getDiameter());
    }
}
