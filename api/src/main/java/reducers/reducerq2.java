package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class reducerq2 implements ReducerFactory<String,Integer,Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new rq2();
    }

    private class rq2 extends Reducer<Integer,Integer>{
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
