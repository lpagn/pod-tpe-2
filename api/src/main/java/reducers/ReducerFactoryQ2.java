package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.Pair;

import java.util.Map;

public class ReducerFactoryQ2 implements ReducerFactory<Map.Entry<String,String>,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer( Map.Entry<String,String> s) {
        return new ReducerQ2();
    }

    private class ReducerQ2 extends Reducer<Integer,Integer>{
        private int sum;

        @Override
        public void beginReduce() {
            sum = 0;
        }

        @Override
        public void reduce(Integer i) {
            sum+=i;
        }

        @Override
        public Integer finalizeReduce() {
            return sum;
        }
    }
}
