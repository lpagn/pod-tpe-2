package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.Pair;

import java.util.Map;

public class reducerq2 implements ReducerFactory<Map.Entry<String,String>,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer( Map.Entry<String,String> s) {
        return new rq2();
    }

    private class rq2 extends Reducer<Integer,Integer>{
        private volatile int sum;

        @Override
        public void beginReduce() {
            sum = 0;
        }

        @Override
        public synchronized void reduce(Integer i) {
            sum+=i;
        }

        @Override
        public Integer finalizeReduce() {
            return sum;
        }
    }
}
