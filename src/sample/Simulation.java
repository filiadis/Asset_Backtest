package sample;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private List<Double> finRes = new ArrayList<>();

    public Simulation() {
        this.finRes = finRes;
    }

    public List<Double> getFinRes() {
        return finRes;
    }

    public void simulationResult(List<Double> close, List<Double> high, List<Double> low,
                                        List<Double> shortMA, List<Double> longMA, int shortLength, int longLength,
                                        double commission, boolean longShort, double profitTarget, double stopL) {



        boolean longPosition = false;

        boolean shortPosition = false;

        double signalPrice = 0;

        double priceTarget = 0;

        double stopLoss = 0;

        int difference = longLength - shortLength;

        for (int i = 0; i < close.size() - longLength-10; i++) {

            //Manage LONG Position
            if (shortMA.get(i + difference) < longMA.get(i)
                    && shortMA.get(i + difference + 1) > longMA.get(i + 1)
                    && longPosition == false && shortPosition == false) {

                longPosition = true;

                signalPrice = close.get(i + difference + 1);

                priceTarget = signalPrice + (signalPrice * profitTarget);

                stopLoss = signalPrice - (signalPrice * stopL);

            }

            if (longPosition == true) {

                if (high.get(i + difference + 2) >= priceTarget && low.get(i + difference + 2) > stopLoss) {


                    finRes.add((priceTarget / signalPrice) - (commission * 2) -1);

                    longPosition = false;

                } else if (low.get(i + difference + 2) < stopLoss) {

                    finRes.add((stopLoss / signalPrice) - (commission * 2) - 1);

                    longPosition = false;

                }

            }

            //Manage SHORT Position
            if (shortMA.get(i + difference) > longMA.get(i)
                    && shortMA.get(i + difference + 1) < longMA.get(i + 1)
                    && longPosition == false && shortPosition == false && longShort == true) {

                shortPosition = true;

                signalPrice = close.get(i + difference + 1);

                priceTarget = signalPrice - (signalPrice * profitTarget);

                stopLoss = signalPrice + (signalPrice * stopL);

            }

            if (shortPosition == true) {

                if (low.get(i + difference + 2) <= priceTarget && high.get(i + difference + 2) < stopLoss) {

                    finRes.add((signalPrice / priceTarget) - (commission * 2) - 1);

                    shortPosition = false;

                } else if (high.get(i + difference + 2) > stopLoss) {

                    finRes.add((signalPrice / stopLoss) - (commission * 2) - 1);

                    shortPosition = false;

                }

            }

        }


    }
}


