package sample.MovingAverages;

import java.util.ArrayList;
import java.util.List;

public class LongMovingAverage {

    public List<Double> longMa (List<Double> close, int longMaLength) {

        List<Double> longMAOutput = new ArrayList<>();

        for (int i=0; i < close.size()-longMaLength; i++) {

            List<Double> slice = new ArrayList<>(close.subList(i,i+longMaLength));

            double sum=0;

            for (int j=0; j < slice.size(); j++){

                sum+= slice.get(j);

            }

            longMAOutput.add(sum/longMaLength);

        }

        return longMAOutput;


    }
}
