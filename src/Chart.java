import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Chart extends JFrame {
    private JFreeChart lineChart;

    public Chart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        lineChart = ChartFactory.createLineChart(
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


}


