package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.Pair;

public class QueryOneReducer implements ReducerFactory<Pair<String, Integer>, Integer, Pair<String,Double>> {

    @Override
    public Reducer<Integer, Pair<String,Double>> newReducer(Pair<String,Integer> stringIntegerPair) {
        return new InnerQueryOneReducer(stringIntegerPair);
    }

    private static class InnerQueryOneReducer extends Reducer<Integer, Pair<String,Double>> {
        private final String neighbourhood;
        private final Integer population;
        private Integer sum;

        InnerQueryOneReducer(Pair<String,Integer> pair){
            this.neighbourhood = pair.getKey();
            this.population = pair.getValue();
        }

        @Override
        public void beginReduce() {
            this.sum = 0;
        }

        @Override
        public void reduce(Integer i) {
            sum++;
        }

        @Override
        public Pair<String,Double> finalizeReduce() {
            return new Pair<>(neighbourhood, (double) (sum/population));
        }
    }

}


