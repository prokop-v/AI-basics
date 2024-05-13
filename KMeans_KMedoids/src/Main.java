
public class Main {
    public static void main(String[] args) {
        //načteme soubor
        FileReader fileReader = new FileReader("cv7_vstup.txt");

        //uložíme data ze souboru
        double[] Xs = fileReader.getXs();
        double[] Ys = fileReader.getYs();
        double[] firstCentroidXY = fileReader.getFirstCentroidXY();
        double[] secondCentroidXY = fileReader.getSecondCentroidXY();

        //spuštění K-Means
        KMeans kMeans = new KMeans(Xs, Ys, firstCentroidXY, secondCentroidXY);
        kMeans.kMeansAlgorithm();

        //vykreslení K-Means
        Graph graphDrawer = new Graph(Xs, Ys, kMeans.getFirstCentroidXY(), kMeans.getSecondCentroidXY());
        graphDrawer.drawKMeansGraph();

        //spuštění K-Medoids
        KMedoids kMedoids = new KMedoids(Xs, Ys, firstCentroidXY, secondCentroidXY);
        kMedoids.kMedoidsAlgorithm();

        //vykreslení K-Medoids
        graphDrawer = new Graph(Xs, Ys, kMedoids.getFirstCentroidXY(), kMedoids.getSecondCentroidXY());
        graphDrawer.drawKMedoidsGraph();
    }
}
