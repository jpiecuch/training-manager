package pl.jakubpiecuch.trainingmanager.web.ui;

import java.io.Serializable;

public class SeriesUI implements Serializable {
    
    private Integer value;

    public SeriesUI() {
    }

    SeriesUI(String series) {
        this.value = Integer.parseInt(series);
    }

    public Integer getValue() {
        return value;
    }

}
