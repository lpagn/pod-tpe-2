package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.Map;

public class MapperQ4 implements Mapper<Map.Entry<String,String>, String, String,Integer> {

    @Override
    public void map(Map.Entry<String,String> pair, String neigh, Context<String, Integer> context) {
        context.emit(neigh.toLowerCase(), 1);
    }
}
