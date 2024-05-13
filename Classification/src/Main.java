public class Main {
    public static void main(String[] args) {
        //načteme soubor
        FileReader fileReader = new FileReader("cv8_vstup.txt");
        //uložíme body a body, které budeme přidávat
        Point[] points = fileReader.getPoints();
        Point[] addedPoints = fileReader.getAddedPoints();

        //řešení dle nejbližšího centroidu
        Classification byCentroid = new Classification(points);
        byCentroid.setUp();
        byCentroid.byNearestCentroid(addedPoints);

        //vykreslení grafu
        Graph graph = new Graph(byCentroid.A, byCentroid.B, byCentroid.C,
                byCentroid.getFirstCentroid(), byCentroid.getSecondCentroid(), byCentroid.getThirdCentriod());

        graph.drawKMeansGraph();

        /////////////////////////////////////////

        //řešení dle nejbližšího souseda (protože K=1)
        Classification byNeighbour = new Classification(points);

        byNeighbour.setUp();
        byNeighbour.byNearestNeighbour(addedPoints);

        //vykreslení grafu
        Graph graph2 = new Graph(byNeighbour.A, byNeighbour.B, byNeighbour.C,
                byNeighbour.getFirstCentroid(), byNeighbour.getSecondCentroid(), byNeighbour.getThirdCentriod());

        graph2.drawKMeansGraph();
    }
}
