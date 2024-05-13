import java.util.ArrayList;

public class Classification {
    private Point[] points;
    private Point firstCentroid;
    private Point secondCentroid;
    private Point thirdCentriod;
    public ArrayList<Point> A = new ArrayList<>();
    public ArrayList<Point> B = new ArrayList<>();
    public ArrayList<Point> C = new ArrayList<>();

    /**
     * Kontruktor třídy, kde budeme klasifikovat
     * @param points (body, které jsou již klasifikováné)
     */
    public Classification(Point[] points) {
        this.points = points;
    }

    /**
     * Metoda přiřazující již klasifikováné body do Arraylistů
     */
    public void setUp() {

        for (int i = 0; i < points.length; i++) {
            //všechny prvky, které už mají třídu přiřadíme do správného seznamu
            if (points[i].getcClass() == 'a') {
                A.add(points[i]);
            } else if (points[i].getcClass() == 'b') {
                B.add(points[i]);
            } else {
                C.add(points[i]);
            }
        }

        //a poté u všech tříd spočítame jejich centroid
        firstCentroid = calculateCentroid(A);
        secondCentroid = calculateCentroid(B);
        thirdCentriod = calculateCentroid(C);
    }

    /**
     * Metoda pro vypočítání nové pozice centroidu
     *
     * @param List (list bodů patřící centroidu)
     * @return souřadnice nového centroidu
     */
    private Point calculateCentroid(ArrayList<Point> List) {
        double sumX = 0;
        double sumY = 0;
        //sečteme y a x pozice
        for (int i = 0; i < List.size(); i++) {
            sumX += List.get(i).getX();
            sumY += List.get(i).getY();
        }
        //a vydělíme počtem
        double centroidX = sumX / List.size();
        double centroidY = sumY / List.size();

        return new Point(centroidX, centroidY, List.get(0).getcClass());
    }

    /**
     * Metoda, která ke každýmu bodu, který chceme přidat vypočítá nejbližší centroid, a díky tomu ho zařadí do jeho třídy,
     * po přidání bodu se centroid přepočítá
     * @param addedPoints Point[] body, které chceme klasifikovat
     */
    public void byNearestCentroid(Point[] addedPoints) {
        //Spočtení, k jakému centroidu jsou naměřená data blíže, spočteme pro všechna data
        for (int i = 0; i < addedPoints.length; i++) {

            //distance ke všem centroidům
            double distance1 = calculateDistance(addedPoints[i].getX(), addedPoints[i].getY(), firstCentroid.getX(), firstCentroid.getY());
            double distance2 = calculateDistance(addedPoints[i].getX(), addedPoints[i].getY(), secondCentroid.getX(), secondCentroid.getY());
            double distance3 = calculateDistance(addedPoints[i].getX(), addedPoints[i].getY(), thirdCentriod.getX(), thirdCentriod.getY());

            //porovnání, ke kterému centroidu je bod nejblíže
            if (distance1 <= distance2 && distance1 <= distance3) {
                A.add(addedPoints[i]); //přiřazení
            } else if (distance2 <= distance1 && distance2 <= distance3) {
                B.add(addedPoints[i]);
            } else {
                C.add(addedPoints[i]);
                }
        }
    }

    /**
     * Metoda, která ke každýmu bodu, který chceme přidat vypočítá nejbližšího souseda, a díky tomu ho zařadí do jeho třídy,
     * po přidání bodu se centroid přepočítá
     * @param addedPoints Point[] body, které chceme klasifikovat
     */
    public void byNearestNeighbour(Point[] addedPoints) {
        char minNeighbour = '-';

        //Spočtení, k jakému bodu jsou naměřená data blíže
        for (int i = 0; i < addedPoints.length; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < points.length; j++) {
                //projíždíme ve dvou for cyklech, pro každý bod, který chceme přiřadit porovnáme vzdálenosti ke všem ostatním bodům
                double distance = calculateDistance(addedPoints[i].getX(), addedPoints[i].getY(), points[j].getX(), points[j].getY());

                //pokud je nová distance menší, změníme minimum
                if (distance < min) {
                    min = distance;
                    minNeighbour = points[j].getcClass(); //uložíme char nejbližšího souseda
                }
            }
            //přiřadíme do seznamu a vypočítáme nový centroid
            if (minNeighbour == 'a') {
                A.add(addedPoints[i]);
            } else if (minNeighbour == 'b') {
                B.add(addedPoints[i]);
            } else {
                C.add(addedPoints[i]);
            }
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
    private double calculateDistance ( double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Getter pole s souřadnicemi prvního centroidu
     * @return Point
     */
    public Point getFirstCentroid () {
        return firstCentroid;
    }

    /**
     * Getter pole s souřadnicemi druhého centroidu
     * @return Point
     */
    public Point getSecondCentroid () {
        return secondCentroid;
    }

    /**
     * Getter pole s souřadnicemi třetího centroidu
     * @return Point
     */
    public Point getThirdCentriod () {
        return thirdCentriod;
    }
}
