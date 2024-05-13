import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph {
    private ArrayList<Point> pointsA;
    private ArrayList<Point> pointsB;
    private ArrayList<Point> pointsC;
    private Point firstCentroid;
    private Point secondCentroid;
    private Point thirdCentroid;

    /**
     * Konstrutor grafu
     * @param pointsA Body patřící do skupiny A
     * @param pointsB Body patřící do skupiny B
     * @param pointsC Body patřící do skupiny C
     * @param firstCentroid Souřadnice prvního centroidu
     * @param secondCentroid Souřadnice druhého centroidu
     * @param thirdCentroid Souřadnice třetího centroidu
     */
    public Graph(ArrayList<Point> pointsA, ArrayList<Point> pointsB, ArrayList<Point> pointsC,
                             Point firstCentroid, Point secondCentroid, Point thirdCentroid) {
            this.firstCentroid = firstCentroid;
            this.secondCentroid = secondCentroid;
            this.thirdCentroid = thirdCentroid;
            this.pointsA = pointsA;
            this.pointsB = pointsB;
            this.pointsC = pointsC;
    }

    /**
     * Metoda pro vykreslení grafu
     */
    public void drawKMeansGraph() {
        XYSeries firstCentroidSeries = new XYSeries("První centroid");
        firstCentroidSeries.add(firstCentroid.getX(), firstCentroid.getY());

        XYSeries secondCentroidSeries = new XYSeries("Druhý centroid");
        secondCentroidSeries.add(secondCentroid.getX(), secondCentroid.getY());

        XYSeries thirdCentroidSeries = new XYSeries("Třetí centroid");
        thirdCentroidSeries.add(thirdCentroid.getX(), thirdCentroid.getY());

        XYSeries dataSeriesA = new XYSeries("Shluk 1");
        XYSeries dataSeriesB = new XYSeries("Shluk 2");
        XYSeries dataSeriesC = new XYSeries("Shluk 3");
        addToDataseries(dataSeriesA, pointsA);
        addToDataseries(dataSeriesB, pointsB);
        addToDataseries(dataSeriesC, pointsC);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(firstCentroidSeries);
        dataset.addSeries(secondCentroidSeries);
        dataset.addSeries(thirdCentroidSeries);
        dataset.addSeries(dataSeriesA);
        dataset.addSeries(dataSeriesB);
        dataset.addSeries(dataSeriesC);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Klasifikace",
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

        JFrame frame = new JFrame("Klasifikace");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Metoda pro přidání dat do určitého datasetu
     * @param dataseries dataset, do kterého přidáváme
     * @param points data, které tam přidáváme
     */
    private void addToDataseries(XYSeries dataseries, ArrayList<Point> points){
        for (int i = 0; i < points.size(); i++){
            dataseries.add(points.get(i).getX(), points.get(i).getY());
        }
    }
}
