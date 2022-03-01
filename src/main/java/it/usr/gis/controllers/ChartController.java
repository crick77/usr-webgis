/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.controllers;

import it.usr.gis.domain.Accesso;
import it.usr.gis.services.ChartService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class ChartController implements Serializable {
    @Inject
    ChartService cs;
    LineChartModel annoChart;
    LineChartModel annoMeseChart;
    List<Integer> anni;
    List<Integer> mesiAnno;
    int anno;
    int mese;
    public final static String MESI[] = {" Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic" };
   
    public void init() {
        anni = cs.getAnni();
        anno = anni.get(0);
                
        update();
    }

    public void update() {
        mesiAnno = cs.getMesiAnno(anno);
        mese = mesiAnno.get(0);

        loadAnnoChart();
        loadAnnoMeseChart();
    }
    
    public void loadAnnoChart() {
        List<Accesso> lData = cs.getAccessiAnno(anno);
        annoChart = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        lData.forEach(d -> {
            values.add(d.getAccessi());
        });
        dataSet.setData(values);
        dataSet.setLabel("# accessi");
        dataSet.setYaxisID("left-y-axis");
        dataSet.setFill(true);
        dataSet.setTension(0.5);
        
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        lData.forEach(d -> {
            labels.add(MESI[d.getQuando()-1]);
        });
        data.setLabels(labels);
        annoChart.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");

        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Accessi totali nell'anno "+anno);
        options.setTitle(title);

        annoChart.setOptions(options);
    }
    
    public void loadAnnoMeseChart() {
        List<Accesso> lData = cs.getAccessiMeseAnno(mese, anno);
        annoMeseChart = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        lData.forEach(d -> {
            values.add(d.getAccessi());
        });
        dataSet.setData(values);
        dataSet.setLabel("# accessi");
        dataSet.setYaxisID("left-y-axis");
        dataSet.setFill(true);
        dataSet.setTension(0.5);
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setBackgroundColor("rgba(75, 192, 192, 0.6)");
        
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        lData.forEach(d -> {
            labels.add(String.valueOf(d.getQuando()));
        });
        data.setLabels(labels);
        annoMeseChart.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");

        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Accessi del mese di "+MESI[mese-1]+"/"+anno);
        options.setTitle(title);

        annoMeseChart.setOptions(options);
    }
    
    public LineChartModel getAnnoChart() {
        return annoChart;
    }
    
    public LineChartModel getAnnoMeseChart() {
        return annoMeseChart;
    }     

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }        

    public List<Integer> getAnni() {
        return anni;
    }

    public void setAnni(List<Integer> anni) {
        this.anni = anni;
    }   

    public List<Integer> getMesiAnno() {
        return mesiAnno;
    }

    public void setMesiAnno(List<Integer> mesiAnno) {
        this.mesiAnno = mesiAnno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }
    
    public String decodeMese(int mese) {
        return MESI[mese-1];
    }   
}
