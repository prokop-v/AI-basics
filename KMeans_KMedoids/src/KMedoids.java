import java.util.ArrayList;
import java.util.Random;

public class KMedoids {
    private double[] Xs;
    private double[] Ys;
    private double[] firstCentroidXY;
    private double[] secondCentroidXY;
    private double min = Double.POSITIVE_INFINITY;

    /**
     * Konstrutor K-Medoids
     * @param Xs X souřadnice
     * @param Ys Y souřadnice
     * @param firstCentroidXY Souřadnice prvního centroidu
     * @param secondCentroidXY Souřadnice druhého centroidu
     */
    public KMedoids(double[] Xs, double[] Ys, double[] firstCentroidXY, double[] secondCentroidXY) {
        this.Xs = Xs;
        this.Ys = Ys;
        this.firstCentroidXY = firstCentroidXY;
        this.secondCentroidXY = secondCentroidXY;
    }

    /**
     * Metoda pracující s K-Medoids algoritmem
     */
    public void kMedoidsAlgorithm() {
        Random r = new Random();
        int random;

        //uložíme pozice prvních medoidů
        double medoid1X = firstCentroidXY[0];
        double medoid1Y = firstCentroidXY[1];
        double medoid2X = secondCentroidXY[0];
        double medoid2Y = secondCentroidXY[1];

        //medoid algotihm proběhne v 1000 iteracích
        for (int x = 0; x < 1000; x++) {
            ArrayList<Double> closerToFirstX = new ArrayList<>();
            ArrayList<Double> closerToFirstY = new ArrayList<>();
            ArrayList<Double> closerToSecondX = new ArrayList<>();
            ArrayList<Double> closerToSecondY = new ArrayList<>();

            double[] firstCentroid = {medoid1X, medoid1Y};
            double[] secondCentroid = {medoid2X, medoid2Y};

            //stejný výpočet vzdálenosti jako v K-Means
            for (int i = 0; i < Xs.length; i++) {
                Double distance1 = calculateDistance(Xs[i], Ys[i], medoid1X, medoid1Y);
                Double distance2 = calculateDistance(Xs[i], Ys[i], medoid2X, medoid2Y);
                if (distance1 < distance2) {
                    closerToFirstX.add(Xs[i]);
                    closerToFirstY.add(Ys[i]);
                } else if (distance1 > distance2) {
                    closerToSecondX.add(Xs[i]);
                    closerToSecondY.add(Ys[i]);
                } else {
                    int random2 = r.nextInt(0, 2);
                    if (random2 == 1) {
                        closerToFirstX.add(Xs[i]);
                        closerToFirstY.add(Ys[i]);
                    } else {
                        closerToSecondX.add(Xs[i]);
                        closerToSecondY.add(Ys[i]);
                    }
                }
            }
            //vypočítáme sumu vzdáleností v obou shlucích
            double calculateSum = 0;

            //pro všechny body patřící do shluku 1
            for (int i = 0; i < closerToFirstX.size(); i++) {
                if (closerToFirstX.get(i) == medoid1X && closerToFirstY.get(i) == medoid1Y)
                    continue;
                calculateSum += calculateDistance(closerToFirstX.get(i), closerToFirstY.get(i), medoid1X, medoid1Y);
            }

            //pro všechny body patřící do shluku 2
            for (int i = 0; i < closerToSecondX.size(); i++) {
                if (closerToSecondX.get(i) == medoid2X && closerToSecondY.get(i) == medoid2Y)
                    continue;
                calculateSum += calculateDistance(closerToSecondX.get(i), closerToSecondY.get(i), medoid2X, medoid2Y);
            }

            //a takto to děláme v iteracích, čím je SUM menší, tím je výsledek lepší
            if (calculateSum < min) {
                //pokud je sum menší než předchozí min, vyměníme, a pozice medoidů se změní
                min = calculateSum;
                firstCentroidXY = firstCentroid;
                secondCentroidXY = secondCentroid;
            }

            //Na konci každé iterace vyměníme jeden medoid za jiný, je to zcela náhodně
            do {
                random = r.nextInt(0, 2);
                if (random == 0) {
                    random = r.nextInt(0, 9);
                    medoid1X = Xs[random];
                    medoid1Y = Ys[random];
                } else {
                    random = r.nextInt(0, 9);
                    medoid2X = Xs[random];
                    medoid2Y = Ys[random];
                }
                //děláme do dokud se vyberou dva jiné medoidy
                //takovéto vybrání medoidu zvyšuje efektivitu vybrání nejlepších souřadnic
            } while (medoid1X == medoid2X && medoid1Y == medoid2Y);
        }
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

    /**
     * Getter pole s souřadnicemi prvního medoidu
     * @return double[]
     */
    public double[] getFirstCentroidXY() {
        return firstCentroidXY;
    }

    /**
     * Getter pole s souřadnicemi druhého medoidu
     * @return double[]
     */
    public double[] getSecondCentroidXY() {
        return secondCentroidXY;
    }
}
