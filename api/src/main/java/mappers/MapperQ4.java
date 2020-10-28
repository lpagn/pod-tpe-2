package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.Map;

public class MapperQ4 implements Mapper<String, Map.Entry<String,String>, String,Integer> {

    String name;

    public MapperQ4(String name){
        this.name=name;
    }

    @Override
    public void map(String key, Map.Entry<String,String> pair, Context<String, Integer> context) {
        if(pair.getValue().toLowerCase().equals(name.toLowerCase()))
            context.emit(pair.getKey().toLowerCase(), 1);
    }
}
