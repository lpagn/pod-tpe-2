package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.Pair;

public class ReducerQ1 implements ReducerFactory<String, Integer, Integer> {

    @Override
    public Reducer<Integer,Integer> newReducer(String s) {
        return new InnerQueryOneReducer();
    }

    private static class InnerQueryOneReducer extends Reducer<Integer, Integer> {
        private Integer sum;

        @Override
        public void beginReduce() {
            this.sum = 0;
        }

        @Override
        public void reduce(Integer i) {
            sum += i;
        }

        @Override
        public Integer finalizeReduce() {
            return sum;
        }
    }

}


