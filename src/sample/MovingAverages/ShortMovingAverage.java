package sample.MovingAverages;

import java.util.ArrayList;
import java.util.List;

public class ShortMovingAverage {


    public List<Double> shortMa (List<Double> close, int shortMaLength) {

        List<Double> shortMAOutput = new ArrayList<>();

        for (int i=0; i < close.size()-shortMaLength; i++) {

            List<Double> slice = new ArrayList<>(close.subList(i,i+shortMaLength));

            double sum=0;

            for (int j=0; j < slice.size(); j++){

                sum+= slice.get(j);

            }

            shortMAOutput.add(sum/shortMaLength);

        }

        return shortMAOutput;


    }

}
