public class Point {

    private double X;
    private double Y;
    private char cClass;

    /**
     * Bod obsahující data
     * @param X X souřadnice
     * @param Y Y souřadnice
     * @param cClass třída, do které bod patří
     */
    public Point(double X, double Y, char cClass) {
        this.X = X;
        this.Y = Y;
        this.cClass = cClass;
    }

    /**
     * X pozice bodu
     * @return double
     */
    public double getX() {
        return X;
    }

    /**
     * Y pozice bodu
     * @return double
     */
    public double getY() {
        return Y;
    }

    /**
     * Getter třídy, do které bod patří
     * @return char
     */
    public char getcClass() {
        return cClass;
    }

}
