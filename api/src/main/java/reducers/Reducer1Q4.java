package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Reducer1Q4 implements ReducerFactory<String,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new rq4();
    }

    private static class rq4 extends Reducer<Integer,Integer>{
        private volatile int sum;

        @Override
        public synchronized void beginReduce() {
            sum = 0;
        }

        @Override
        public synchronized void reduce(Integer i) {
            sum+=i;
        }

        @Override
        public synchronized Integer finalizeReduce() {
            return sum;
        }
    }
}