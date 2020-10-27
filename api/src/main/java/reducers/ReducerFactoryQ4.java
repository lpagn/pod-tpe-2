package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class ReducerFactoryQ4 implements ReducerFactory<String,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new ReducerQ4();
    }

    private static class ReducerQ4 extends Reducer<Integer,Integer>{
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