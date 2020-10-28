package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class MapperQ2 implements Mapper< String, Map.Entry<String,String>,Map.Entry<String, String>,Integer> {
    private Set<String> neighs;
    public MapperQ2(Set<String> neighs){
        this.neighs = neighs;
    }

    @Override
    public void map(String id,Map.Entry<String, String> entry, Context<Map.Entry<String, String>, Integer> context) {
        if (neighs.contains(entry.getKey())){
            context.emit(entry,1);
        }
    }
}
