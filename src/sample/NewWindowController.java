package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import sample.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewWindowController extends Controller {

    @FXML
    private TextField totalReturnPercent;
    @FXML
    private TextField maxDailyDrawdown;
    @FXML
    private TextField numWinTrades;
    @FXML
    private TextField numLosingTrades;
    @FXML
    private TextField numPosOpenings;
    @FXML
    private TextField profitFactor;
    @FXML
    private LineChart chart;



    public void initialize() {


        totalReturnPercent.setText(String.format("%.2f", (totalReturn(finalResults))));

        maxDailyDrawdown.setText(String.format("%.2f", dailyDrawdown(finalResults)));

        numPosOpenings.setText(String.valueOf(finalResults.size()));

        numWinTrades.setText(String.format("%.2f", winningTrades(finalResults)));

        numLosingTrades.setText(String.format("%.2f", losingTrades(finalResults)));

        profitFactor.setText(String.format("%.2f", profitFact(finalResults)));

        XYChart.Series<String, Double> series = new XYChart.Series();
        series.setName("Cumulative returns");

        List<Double> cumResults = new ArrayList<>(cumulativeReturnsYaxis(finalResults));

        List<String> rollingNums = new ArrayList<>();

        for (int i=0; i<cumResults.size(); i++) {
            rollingNums.add(String.valueOf(i));
        }


        try {

            for (int i = 0; i < cumResults.size(); i++) {
                series.getData().add(new XYChart.Data( rollingNums.get(i) , cumResults.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogBox.setText("Problem with data plotting");
            dialogBox.setStyle("-fx-text-fill: red");
        }

        chart.getData().addAll(series);

        //Edit chart
        chart.setTitle("System's cumulative returns %");
        chart.setCreateSymbols(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalGridLinesVisible(true);
    }

    public double totalReturn (List<Double> results) {

        double temp = 0;


        for (int i=0; i<results.size(); i++) {

            temp += results.get(i);
        }



        return (temp * 100);

    }

    public double dailyDrawdown (List<Double> results) {

        return Collections.min(results);

    }


    public double winningTrades (List<Double> results) {

        double temp = 0;

        for (int i=0; i<results.size(); i++) {

            if (results.get(i) > 0) {

                temp += 1;

            }

        }

        return (temp / results.size()) * 100;

    }

    public double losingTrades (List<Double> results) {

        double temp = 0;

        for (int i=0; i<results.size(); i++) {

            if (results.get(i) < 0) {

                temp += 1;

            }

        }

        return (temp/ results.size()) * 100;

    }

    public double profitFact (List<Double> results) {

        double temp1 = 0;

        for (int i=0; i<results.size(); i++) {

            if (results.get(i) > 0) {

                temp1 += 1;

            }

        }

        double temp2 = 0;

        for (int i=0; i<results.size(); i++) {

            if (results.get(i) < 0) {

                temp2 += 1;

            }

        }

        return temp1 / temp2;

    }


    public List<Double> cumulativeReturnsYaxis (List<Double> results) {

        List<Double> cumResults = new ArrayList<>();

        double temp = 0;

        for (int i=0; i<results.size(); i++) {

            temp += results.get(i);

            cumResults.add(temp);

        }
        return cumResults;

    }


}
