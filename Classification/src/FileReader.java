import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private Point[] points = new Point[9];

    private Point[] addedPoints = new Point[4];

    /**
     * Konstruktor třídy pro načtení souboru
     * @param fileName název souboru
     */
    public FileReader(String fileName) {
        readDataFromFile(fileName);
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
                if (!line.contains("test.x")) {
                    points[i] = new Point(Double.parseDouble(modified[0]), Double.parseDouble(modified[1]),
                            modified[2].charAt(0));
                    i++;
                } else {
                    i = 0;
                    while(scanner.hasNextLine()){
                        line = scanner.nextLine();
                        modified = line.split(";");
                        addedPoints[i] =  new Point(Double.parseDouble(modified[0]), Double.parseDouble(modified[1]),
                                '-');
                        i++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter již seshlukovaných bodů
     * @return Point[] body
     */
    public Point[] getPoints() {
        return points;
    }

    /**
     * Getter bodů, které budeme klasifikovat
     * @return Point[] body
     */
    public Point[] getAddedPoints() {
        return addedPoints;
    }
}
