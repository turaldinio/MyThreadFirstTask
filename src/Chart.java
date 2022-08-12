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
                "arraySize", "Time, ms",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1080, 700));
        setContentPane(chartPanel);

    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int a = 1; a <= 99_000_000; a *= 10) {
            int[] array = Main.initArray(a);
            dataset.addValue(Main.singleThreadSolution(array), "single", String.valueOf(a));
            dataset.addValue(Main.multithreadedProgramRunnable(array), "multithreading(Runnable)", String.valueOf(a));
            dataset.addValue(Main.multithreadedProgram(array), "multithreading(Callable)", String.valueOf(a));
        }


        return dataset;
    }


    public Chart(String windowName) {
        super(windowName);
        XYDataset dataset = createOldDataset();

        JFreeChart chart = ChartFactory.createScatterPlot("Зависимость кол-ва элементов от времени работы"
                , "Время, мс", "Кол-во элементов, шт ", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));


        ChartPanel panel = new ChartPanel(chart);

        setContentPane(panel);

    }

    private XYDataset createOldDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();


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


