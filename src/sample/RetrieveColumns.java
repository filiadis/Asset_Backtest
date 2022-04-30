package sample;

import java.util.ArrayList;
import java.util.List;

public class RetrieveColumns {

    private List<String> dates = new ArrayList<>();

    private List<Double> close = new ArrayList<>();

    private List<Double> high = new ArrayList<>();

    private List<Double> low = new ArrayList<>();


    public RetrieveColumns() {
        this.dates = dates;
        this.close = close;
        this.high = high;
        this.low = low;
    }

    public List<String> getDates() {
        return dates;
    }

    public List<Double> getClose() {
        return close;
    }

    public List<Double> getHigh() {
        return high;
    }

    public List<Double> getLow() {
        return low;
    }



    public void datesColumn(List<List<String>> listData) {


        for (int i=1; i< listData.size(); i++) {
            dates.add(listData.get(i).get(0));
        }

    }

    public void closeColumn(List<List<String>> listData) {


        for (int i=1; i< listData.size(); i++) {
            close.add(Double.parseDouble(listData.get(i).get(4)));
        }

    }

    public void lowColumn(List<List<String>> listData) {


        for (int i=1; i< listData.size(); i++) {
            low.add(Double.parseDouble(listData.get(i).get(3)));
        }

    }

    public void highColumn(List<List<String>> listData) {


        for (int i=1; i< listData.size(); i++) {
            high.add(Double.parseDouble(listData.get(i).get(2)));
        }

    }

}
