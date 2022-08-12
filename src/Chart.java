import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Chart extends JFrame {

    public Chart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Time, ms", "arraySize",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int a = 1; a <= 1_000_000; a *= 10) {
            int[] array = Main.initArray(a);
            dataset.addValue(a, "single", String.valueOf(Main.singleThreadSolution(array)));
        }

        for (int a = 1; a <= 1_000_000; a *= 10) {
            int[] array = Main.initArray(a);
            dataset.addValue(a, "multithreading", String.valueOf(Main.multithreadedProgram(array)));
        }


        return dataset;
    }


    public Chart(String windowName) {
        super(windowName);
        XYDataset dataset = createoldDataset();

        JFreeChart chart = ChartFactory.createScatterPlot("Зависимость кол-ва элементов от времени работы"
                , "Время, мс", "Кол-во элементов, шт ", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));


        ChartPanel panel = new ChartPanel(chart);

        setContentPane(panel);

    }

    private XYDataset createoldDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();


        // XYSeries series1 = new XYSeries("Однопоточная программа");
        XYSeries singleThread = new XYSeries("Однопоточная программа");

        for (int a = 1; a <= 1_000_000; a *= 10) {
            int[] array = Main.initArray(a);
            singleThread.add(Main.singleThreadSolution(array), array.length);
        }

        dataset.addSeries(singleThread);

        XYSeries multithreading = new XYSeries("Многопоточная программа");

        for (int a = 1; a <= 1_000_000; a *= 10) {
            int[] array = Main.initArray(a);
            multithreading.add(Main.multithreadedProgram(array), array.length);
        }

        dataset.addSeries(multithreading);

        return dataset;
    }

}


