package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer1Q4 implements ReducerFactory<String,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new rq4();
    }

    private static class rq4 extends Reducer<Integer,Integer>{
        private volatile int sum;

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
            System.out.println(sum);
            return sum;
        }
    }
}