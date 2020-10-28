package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.AbstractMap;
import java.util.Map;

public class MapperQ2 implements Mapper<Map.Entry<String,String>, String, Map.Entry<String, String>,Integer> {

    @Override
    public void map(Map.Entry<String, String> entry, String street, Context<Map.Entry<String, String>, Integer> context) {
        context.emit(new AbstractMap.SimpleEntry<>(entry.getValue(),street),1);
    }
}
