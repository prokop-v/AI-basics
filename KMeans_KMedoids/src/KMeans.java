import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KMeans {
    private double[] Xs;
    private double[] Ys;
    private double[] firstCentroidXY;
    private double[] secondCentroidXY;

    /**
     * Konstrutor K-Means
     * @param Xs X souřadnice
     * @param Ys Y souřadnice
     * @param firstCentroidXY Souřadnice prvního centroidu
     * @param secondCentroidXY Souřadnice druhého centroidu
     */
        public KMeans(double[] Xs, double[] Ys, double[] firstCentroidXY, double[] secondCentroidXY) {
        this.Xs = Xs;
        this.Ys = Ys;
        this.firstCentroidXY = firstCentroidXY;
        this.secondCentroidXY = secondCentroidXY;
    }

    /**
     * Metoda pracující s K-Means algoritmem
     */
    public void kMeansAlgorithm() {
        ArrayList<Double> closerToFirstX = new ArrayList<>();
        ArrayList<Double> closerToFirstY = new ArrayList<>();
        ArrayList<Double> closerToSecondX = new ArrayList<>();
        ArrayList<Double> closerToSecondY = new ArrayList<>();

        //pro uchování starých pozice centroidů
        double[] oldFirstCentroid = firstCentroidXY.clone();
        double[] oldSecondCentroid = secondCentroidXY.clone();

        //Spočtení, k jakému centroidu jsou naměřená data blíže
        for (int i = 0; i < Xs.length; i++) {
            //naměříme vzdálenost
            double distance1 = calculateDistance(Xs[i], Ys[i], firstCentroidXY[0], firstCentroidXY[1]);
            double distance2 = calculateDistance(Xs[i], Ys[i], secondCentroidXY[0], secondCentroidXY[1]);
            if (distance1 < distance2) {
                closerToFirstX.add(Xs[i]);
                closerToFirstY.add(Ys[i]);
            } else if (distance1 > distance2) {
                closerToSecondX.add(Xs[i]);
                closerToSecondY.add(Ys[i]);
            } else {
                //pokud je vzdálenost z obou centroidů stejná, je to 50 na 50
                Random r = new Random();
                int random = r.nextInt(0, 2);
                if (random == 1) {
                    closerToFirstX.add(Xs[i]);
                    closerToFirstY.add(Ys[i]);
                } else {
                    closerToSecondX.add(Xs[i]);
                    closerToSecondY.add(Ys[i]);
                }
            }
        }

        //spočtení nových souřadnic centroidu
        firstCentroidXY = calculateCentroid(closerToFirstX, closerToFirstY);
        secondCentroidXY = calculateCentroid(closerToSecondX, closerToSecondY);

        //kontroluje, jestli se pozice mění
        if (Arrays.equals(oldFirstCentroid, firstCentroidXY) && Arrays.equals(oldSecondCentroid, secondCentroidXY)) {
            return;
        }

        //voláme rekurzivně dokud se pozice Centroidu nemění
        kMeansAlgorithm();
    }

    /**
     * Metoda pro vypočítání nové pozice centroidu
     * @param xList (x pozice patřící centroidu)
     * @param yList (y pozice patřící centroidu
     * @return součadnice nového centroidu
     */
    private double[] calculateCentroid(ArrayList<Double> xList, ArrayList<Double> yList) {
        double sumX = 0;
        double sumY = 0;
        //sečteme y a x pozice
        for (int i = 0; i < xList.size(); i++) {
            sumX += xList.get(i);
            sumY += yList.get(i);
        }
        //a vydělíme počtem
        double centroidX = sumX / xList.size();
        double centroidY = sumY / yList.size();
        return new double[]{centroidX, centroidY};
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
     * Getter pole s souřadnicemi prvního centroidu
     * @return double[]
     */
    public double[] getFirstCentroidXY() {
        return firstCentroidXY;
    }

    /**
     * Getter pole s souřadnicemi druhého centroidu
     * @return double[]
     */
    public double[] getSecondCentroidXY() {
        return secondCentroidXY;
    }
}
