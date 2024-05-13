import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Graph {
    private double[] Xs;
    private double[] Ys;
    private double[] firstCentroidXY;
    private double[] secondCentroidXY;


    /**
     * Konstrutor grafu
     * @param Xs X souřadnice
     * @param Ys Y souřadnice
     * @param firstCentroidXY Souřadnice prvního centroidu
     * @param secondCentroidXY Souřadnice druhého centroidu
     */
    public Graph(double[] Xs, double[] Ys, double[] firstCentroidXY, double[] secondCentroidXY) {
        this.Xs = Xs;
        this.Ys = Ys;
        this.firstCentroidXY = firstCentroidXY;
        this.secondCentroidXY = secondCentroidXY;
    }

    /**
     * Metoda pro vykreslení grafu K-Means
     */
    public void drawKMeansGraph() {
        XYSeries firstCentroidSeries = new XYSeries("První centroid");
        firstCentroidSeries.add(firstCentroidXY[0], firstCentroidXY[1]);

        XYSeries secondCentroidSeries = new XYSeries("Druhý centroid");
        secondCentroidSeries.add(secondCentroidXY[0], secondCentroidXY[1]);

        XYSeries dataSeries1 = new XYSeries("Shluk 1");
        XYSeries dataSeries2 = new XYSeries("Shluk 2");

        //výpočet pro přiřazení dat ke shlukům
        for (int i = 0; i < Xs.length; i++) {
            Double distance1 = calculateDistance(Xs[i], Ys[i], firstCentroidXY[0], firstCentroidXY[1]);
            Double distance2 = calculateDistance(Xs[i], Ys[i], secondCentroidXY[0], secondCentroidXY[1]);
            if (distance1 < distance2) {
                dataSeries1.add(Xs[i], Ys[i]);
            } else if (distance1 > distance2) {
                dataSeries2.add(Xs[i], Ys[i]);
            } else {
                Random r = new Random();
                int random = r.nextInt(0, 2);
                if (random == 1) {
                    dataSeries1.add(Xs[i], Ys[i]);
                } else {
                    dataSeries2.add(Xs[i], Ys[i]);
                }
            }
        }

        XYDataset dataset = new XYSeriesCollection();
        ((XYSeriesCollection) dataset).addSeries(firstCentroidSeries);
        ((XYSeriesCollection) dataset).addSeries(secondCentroidSeries);
        ((XYSeriesCollection) dataset).addSeries(dataSeries1);
        ((XYSeriesCollection) dataset).addSeries(dataSeries2);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "K-Means",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("K-Means");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Metoda pro vykreslení grafu K-Medoids
     */
    public void drawKMedoidsGraph() {
        XYSeries firstCentroidSeries = new XYSeries("První medoid");
        firstCentroidSeries.add(firstCentroidXY[0], firstCentroidXY[1]);

        XYSeries secondCentroidSeries = new XYSeries("Druhý medoid");
        secondCentroidSeries.add(secondCentroidXY[0], secondCentroidXY[1]);

        XYSeries dataSeries1 = new XYSeries("Shluk 1");
        XYSeries dataSeries2 = new XYSeries("Shluk 2");

        //výpočet, do kterého shluku přiřadit prvek
        for (int i = 0; i < Xs.length; i++) {
            Double distance1 = calculateDistance(Xs[i], Ys[i], firstCentroidXY[0], firstCentroidXY[1]);
            Double distance2 = calculateDistance(Xs[i], Ys[i], secondCentroidXY[0], secondCentroidXY[1]);
            if (distance1 < distance2) {
                dataSeries1.add(Xs[i], Ys[i]);
            } else if (distance1 > distance2) {
                dataSeries2.add(Xs[i], Ys[i]);
            } else {
                Random r = new Random();
                int random = r.nextInt(0, 2);
                if (random == 1) {
                    dataSeries1.add(Xs[i], Ys[i]);
                } else {
                    dataSeries2.add(Xs[i], Ys[i]);
                }
            }
        }

        XYDataset dataset = new XYSeriesCollection();
        ((XYSeriesCollection) dataset).addSeries(secondCentroidSeries);
        ((XYSeriesCollection) dataset).addSeries(firstCentroidSeries);
        ((XYSeriesCollection) dataset).addSeries(dataSeries2);
        ((XYSeriesCollection) dataset).addSeries(dataSeries1);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "K-Medoids",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("K-Medoids");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Metoda pro výpočet euklidovské vzdálenosti
     * @param x1 pozice x1
     * @param y1 pozice y1
     * @param x2 pozice x2
     * @param y2 pozice y2
     * @return double vzdálenost
     */
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
