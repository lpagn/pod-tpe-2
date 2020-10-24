package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.Pair;
import models.Tree;

import java.util.Map;

public class QueryOneMapper implements Mapper<Integer, Tree, Pair<String,Integer>, Integer> {

    // no se me ocurre otra forma de que lleguen los habitantes, salvo q se guarde
    // un neighbourhood en Tree y se repita toodo tres millones de veces
    private final Map<String,Integer> neighbourhoods;

    public QueryOneMapper(Map<String,Integer> neighbourhoods){
        this.neighbourhoods = neighbourhoods;
    }

    @Override
    public void map(Integer key, Tree tree, Context<Pair<String,Integer>, Integer> context) {
        context.emit(new Pair<>(tree.getNeighbourhood(), neighbourhoods.get(tree.getNeighbourhood())), 1);
    }

}
