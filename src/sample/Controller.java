package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.MovingAverages.LongMovingAverage;
import sample.MovingAverages.ShortMovingAverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Controller {

    @FXML
    private GridPane gridPane;

    @FXML
    private LineChart chart;

    @FXML
    public TextField dialogBox;
    @FXML
    private TextField shortMATextField;
    @FXML
    private TextField longMATextField;
    @FXML
    private TextField commissions;
    @FXML
    private TextField profitTarget;
    @FXML
    private TextField stopLoss;


    @FXML
    private CheckBox longShort;

    @FXML
    private VBox vBox;


    // Global variables
    public List<String> datesAsset = new ArrayList<>();

    public List<Double> closingPricesAsset = new ArrayList<>();

    public List<Double> highPricesAsset = new ArrayList<>();

    public List<Double> lowPricesAsset = new ArrayList<>();

    public static List<Double> finalResults = new ArrayList<>();

    public List<Double> shortMovingAverageAsset = new ArrayList<>();

    public List<Double> longMovingAverageAsset = new ArrayList<>();

    public int shortMA = 10;

    public int longMA = 20;

    public double commission = 0.015;

    public boolean longShortSelected = false;

    public double prTarget = 0.10;

    public double stLoss = 0.05;


    public void initialize() {
        dialogBox.setText("Upload CSV file from Yahoo Finance web site.");
        dialogBox.setEditable(false);
        vBox.setDisable(true);
        commissions.setText(String.valueOf(commission * 100));
        shortMATextField.setText(String.valueOf(shortMA));
        longMATextField.setText(String.valueOf(longMA));
        profitTarget.setText(String.valueOf(prTarget * 100));
        stopLoss.setText(String.valueOf(stLoss * 100));

    }


    @FXML
    public void handleClick() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Asset file data");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));

        List<File> file = chooser.showOpenMultipleDialog(gridPane.getScene().getWindow());

        ReadCsv readFile = new ReadCsv();
        List<List<String>> result = readFile.readCsvFile(file);


        if (result != null) {
            dialogBox.setText("Uploading successful.");
            dialogBox.setStyle("-fx-text-fill: green");
        } else {
            dialogBox.setText("Uploading failed!");
            dialogBox.setStyle("-fx-text-fill: red");
        }

        //Populate Close, high, low and dates lists


        RetrieveColumns asset = new RetrieveColumns();

        asset.datesColumn(result);

        datesAsset = asset.getDates();

        asset.closeColumn(result);

        closingPricesAsset = asset.getClose();

        asset.highColumn(result);

        highPricesAsset = asset.getHigh();

        asset.lowColumn(result);

        lowPricesAsset = asset.getLow();


        //Set x and y axis
        XYChart.Series<String, Double> series = new XYChart.Series();
        series.setName("Asset");

        try {

            for (int i = 0; i < asset.getClose().size(); i++) {
                series.getData().add(new XYChart.Data(asset.getDates().get(i), asset.getClose().get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogBox.setText("Problem with data plotting");
            dialogBox.setStyle("-fx-text-fill: red");
        }

        chart.getData().addAll(series);

        String startDate = datesAsset.get(0);

        String endDate = datesAsset.get(datesAsset.size()-1);

        //Edit chart
        chart.setTitle("Asset's Closing Price from (" + startDate + ") to (" + endDate + ")");
        chart.setCreateSymbols(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalGridLinesVisible(true);

        vBox.setDisable(false);

        //Initialize filed after import
        ShortMovingAverage foo = new ShortMovingAverage();
        shortMovingAverageAsset = foo.shortMa(closingPricesAsset, shortMA);

        LongMovingAverage foo2 = new LongMovingAverage();
        longMovingAverageAsset = foo2.longMa(closingPricesAsset, longMA);


    }

    @FXML
    public void shortMa(ActionEvent actionEvent) {

        if (actionEvent != null) {

            try {
                shortMA = Integer.parseInt(shortMATextField.getText());
                ShortMovingAverage sma = new ShortMovingAverage();
                shortMovingAverageAsset = sma.shortMa(closingPricesAsset, shortMA);
                dialogBox.setText("Short ma length was set correctly");
                dialogBox.setStyle("-fx-text-fill: green");

                if (shortMA != 0 && longMA != 0) {
                    if (shortMA >= longMA) {
                        dialogBox.setText("The length of short MA must be smaller than that of the long MA");
                        dialogBox.setStyle("-fx-text-fill: red");
                        shortMATextField.setStyle("-fx-background-color: #ed877e");
                        longMATextField.setStyle("-fx-background-color: #ed877e");
                    } else {
                        longMATextField.setStyle("-fx-background-color: #ffffff");
                        shortMATextField.setStyle("-fx-background-color: #ffffff");
                    }
                }

            } catch (Exception e) {
                dialogBox.setText("Import only integer numbers");
                dialogBox.setStyle("-fx-text-fill: red");
                shortMATextField.setStyle("-fx-background-color: #ed877e");

            }
        }

    }

    @FXML
    public void longMa(ActionEvent actionEvent) {

        if (actionEvent != null) {

            try {
                longMA = Integer.parseInt(longMATextField.getText());
                LongMovingAverage lma = new LongMovingAverage();
                longMovingAverageAsset = lma.longMa(closingPricesAsset, longMA);
                dialogBox.setText("Long ma length was set correctly");
                dialogBox.setStyle("-fx-text-fill: green");

                if (shortMA != 0 && longMA != 0) {
                    if (shortMA >= longMA) {
                        dialogBox.setText("The length of short MA must be smaller than that of the long MA");
                        dialogBox.setStyle("-fx-text-fill: red");
                        longMATextField.setStyle("-fx-background-color: #ed877e");
                        shortMATextField.setStyle("-fx-background-color: #ed877e");

                    } else {
                        longMATextField.setStyle("-fx-background-color: #ffffff");
                        shortMATextField.setStyle("-fx-background-color: #ffffff");
                    }
                }

            } catch (Exception e) {
                dialogBox.setText("Import only integer numbers");
                dialogBox.setStyle("-fx-text-fill: red");
                longMATextField.setStyle("-fx-background-color: #ed877e");


            }

        }
    }

    @FXML
    public void commissionSetting(ActionEvent actionEvent) {

        if (actionEvent != null) {

            try {

                double temp = Double.parseDouble(commissions.getText());
                commission = temp / 100;
                dialogBox.setText("Commissions field was set correctly");
                dialogBox.setStyle("-fx-text-fill: green");

            } catch (Exception e) {

                dialogBox.setText("Enter a valid number in commissions field");
                dialogBox.setStyle("-fx-text-fill: red");
                commissions.setStyle("-fx-background-color: #ed877e");

            }

        }

    }

    @FXML
    public void longShortSelection(ActionEvent actionEvent) {

        if (longShort.isSelected()) {

            longShortSelected = true;

        } else {

            longShortSelected = false;

        }

    }


    public void setProfitTarget(ActionEvent actionEvent) {

        if (actionEvent != null) {

            try {
                double temp = Double.parseDouble(profitTarget.getText());
                prTarget = temp / 100;
                dialogBox.setText("Profit target was set correctly");
                dialogBox.setStyle("-fx-text-fill: green");

            } catch (Exception e) {
                dialogBox.setText("Enter a valid number in Profit target field");
                dialogBox.setStyle("-fx-text-fill: red");
                commissions.setStyle("-fx-background-color: #ed877e");
            }
        }

    }

    @FXML
    public void setStopLoss(ActionEvent actionEvent) {

        if (actionEvent != null) {

            try {
                double temp = Double.parseDouble(stopLoss.getText());
                stLoss = temp / 100;
                dialogBox.setText("Stop loss was set correctly");
                dialogBox.setStyle("-fx-text-fill: green");

            } catch (Exception e) {
                dialogBox.setText("Enter a valid number in Stop loss field");
                dialogBox.setStyle("-fx-text-fill: red");
                commissions.setStyle("-fx-background-color: #ed877e");
            }
        }


    }

    @FXML
    public void runSimulation(ActionEvent actionEvent) throws IOException {

        if (actionEvent != null) {

            System.out.println("Sim button clicked");
            dialogBox.setText("Processing: Please wait");
            dialogBox.setStyle("-fx-text-fill: black");

            try {

                Simulation sim = new Simulation();

                sim.simulationResult(closingPricesAsset, highPricesAsset, lowPricesAsset,
                        shortMovingAverageAsset, longMovingAverageAsset, shortMA,
                        longMA, commission, longShortSelected, prTarget, stLoss);

                finalResults = sim.getFinRes();

                dialogBox.setText("Simulation completed");

            } catch (Exception e) {

                dialogBox.setText("Problem with simulation processing");
                dialogBox.setStyle("-fx-text-fill: red");
                e.printStackTrace();

            }


            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("NewWindow.fxml"));
                Stage newStage = new Stage();
                newStage.setTitle("Simulation backtesting results");
                newStage.setScene(new Scene(root2, 800, 600));
                newStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }



        }

    }

}











