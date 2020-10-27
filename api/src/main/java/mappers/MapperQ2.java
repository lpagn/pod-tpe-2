package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.AbstractMap;
import java.util.Map;

public class MapperQ2 implements Mapper<Map.Entry<Integer,String>, Tree, Map.Entry<String, String>,Integer> {

    @Override
    public void map(Map.Entry<Integer, String> entry, Tree tree, Context<Map.Entry<String, String>, Integer> context) {
        context.emit(new AbstractMap.SimpleEntry<>(tree.getNeighbourhood(),tree.getStreet()),1);
    }
}
