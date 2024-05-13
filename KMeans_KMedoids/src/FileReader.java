import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private double[] Xs = new double[9];
    private double[] Ys = new double[9];
    private double[] firstCentroidXY = new double[2];
    private double[] secondCentroidXY = new double[2];

    /**
     * Konstruktor třídy pro načtení souboru
     * @param fileName nízev souboru
     */
    public FileReader(String fileName) {
        readDataFromFile(fileName);
    }

    /**
     * Getter pole s X souřadnicemi
     * @return pole
     */
    public double[] getXs() {
        return Xs;
    }

    /**
     * Getter pole s Y souřadnicemi
     * @return
     */
    public double[] getYs() {
        return Ys;
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

    /**
     * Metoda pro zpracování vstupního souboru
     * @param fileName název souboru
     */
    private void readDataFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] modified = line.split(";");
                if (!line.contains("init")) {
                    Xs[i] = Integer.parseInt(modified[0]);
                    Ys[i] = Integer.parseInt(modified[1]);
                    i++;
                } else {
                    modified = scanner.nextLine().split(";");
                    firstCentroidXY[0] = Integer.parseInt(modified[0]);
                    firstCentroidXY[1] = Integer.parseInt(modified[1]);
                    modified = scanner.nextLine().split(";");
                    secondCentroidXY[0] = Integer.parseInt(modified[0]);
                    secondCentroidXY[1] = Integer.parseInt(modified[1]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
