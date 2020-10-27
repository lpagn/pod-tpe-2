package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

public class MapperQ4 implements Mapper<Pair<Integer,String>, String, String,Integer> {

    @Override
    public void map(Pair<Integer,String> pair, String neigh, Context<String, Integer> context) {
        context.emit(neigh.toLowerCase(), 1);
    }
}
