import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static char target;
    public static char start;
    public static final int INFINITY = Integer.MAX_VALUE;
    public static boolean init = false;
    public static LinkedList<String> arrayOfStrings;
    public static Map<Character, Integer> heuristicMap;
    public static Map<Character, Integer> nodeIndices;
    public static int distance;

    /**
     * Spouštěcí metoda
     */
    public static void main(String[] args) {
        boolean withHeuristic = true;
        loadFile("cv4_vstup_test.txt");
        int[][] adjancencyMatrix = makeAdjancencyMatrix(arrayOfStrings);
        List<Character> result = aStar(adjancencyMatrix, nodeIndices, start, target, withHeuristic);

        if (result != null) {
            System.out.println("Nejkratší cesta z " + start + " do " + target + " je: " + result + "\nvzdálenost mezi nimi je: " + distance);
        } else {
            System.out.println("Cesta do vrcholu nevede.");
        }
    }

    /**
     * Metoda, která zpracuje daný soubor
     * @param data data v souboru
     */
    public static void loadFile(String data){
        arrayOfStrings = new LinkedList<>();
        LinkedList<String> heuristic = new LinkedList<>();

        try {
        File file = new File(data);
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            String line = sc.nextLine();

            if(line.contains(":")){
                continue;
            }
            if (!init){
                start = line.charAt(0);
                line = sc.nextLine();
                if(line.contains(":")){
                    line = sc.nextLine();
                }
                target = line.charAt(0);
                init = true;
                continue;
            }
            if(line.contains(";"))
                arrayOfStrings.add(line);
            else
                heuristic.add(line);
        }
        processHeuristicData(heuristic);
        sc.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda zpracující heuristická data do dále použitelné podoby
     * @param heuristic obsahuje nezpracovaná data o heuristice
     */
    public static void processHeuristicData(LinkedList<String> heuristic) {
        heuristicMap = new HashMap<>();
        for (String line : heuristic) {
            String[] parts = line.split("=");
            char node = parts[0].charAt(0);
            int value = Integer.parseInt(parts[1]);
            //přidání dat do hashmapy
            heuristicMap.put(node, value);
        }
    }

    /**
     * Metoda, která data zpracuje a následně z nich vytvoří matici sousednosti, se kterou můžeme dále pracovat
     * @param input List stringů, který obsahuje část dat ze souboru
     * @return int[][] matice sousednosti
     */
    public static int[][] makeAdjancencyMatrix(LinkedList<String> input){
        // rozdělení dat podle středníku a uložení do hashmapy
        Map<Character, Map<Character, Integer>> adjacencyList = new HashMap<>();
        for (int x = 0; x < input.size(); x++) {
            String[] parts = input.get(x).split(";");
            char node = parts[0].charAt(0);
            Map<Character, Integer> neighbors = new HashMap<>();
            for (int i = 1; i < parts.length; i++) {
                String[] pair = parts[i].split("=");
                char neighbor = pair[0].charAt(0);
                int cost = Integer.parseInt(pair[1]);
                neighbors.put(neighbor, cost);
            }
            adjacencyList.put(node, neighbors);
        }

        //Přiřadíme písmenům indexy A 0, B 1, ...
        nodeIndices = new HashMap<>();
        int index = 0;
        for (char node : adjacencyList.keySet()) {
            nodeIndices.put(node, index++);
        }

        //vytvoření matice sousednosti
        int size = adjacencyList.size();
        int[][] adjacencyMatrix = new int[size][size];

        // do které vložíme data z hash tabulky
        for (char node : adjacencyList.keySet()) {
            //vezmem index v matici kam ukládáme
            int nodeIndex = nodeIndices.get(node);
            //do neighbors si uložíme vrcholy a vzdalenosti indexu, ktery jsme si uložili
            Map<Character, Integer> neighbors = adjacencyList.get(node);
            for (char neighbor : neighbors.keySet()) {
                int neighborIndex = nodeIndices.get(neighbor); //vezmeme index daneho pismena
                int cost = neighbors.get(neighbor);
                adjacencyMatrix[nodeIndex][neighborIndex] = cost;  //nodeindex = pismeno, se kterym pracujeme, neighbour = dany soused
            }
        }
        return adjacencyMatrix;
    }


    /**
     * Hlavní metoda pro vypracování nejkratší vzdálenosti
     * @param adjacencyMatrix - Matice sousednosti
     * @param nodeIndices - mapa přiřazení písmen indexům
     * @param startNode - písmeno vrcholu, ze kterého se začíná
     * @param endNode - písmeno vrcholu, ve kterém se končí
     * @param withHeuristic - chceme zavést i heuristiku? true/false
     * @return List obsahující vrcholy od startNode do endNode nejkratší cestou
     */
    public static List<Character> aStar(int[][] adjacencyMatrix, Map<Character, Integer> nodeIndices, char startNode, char endNode, boolean withHeuristic) {
        int n = adjacencyMatrix.length;
        int startIndex = nodeIndices.get(startNode);
        int endIndex = nodeIndices.get(endNode);

        //pole opravdových cen
        int[] gCosts = new int[n];
        Arrays.fill(gCosts, INFINITY);
        gCosts[startIndex] = 0;

        //pole heruistických cen
        int[] fCosts = new int[n];
        Arrays.fill(fCosts, INFINITY);
        fCosts[startIndex] = heuristicCost(startNode, withHeuristic);

        //budeme si hlídat vrcholy již prohledané
        boolean[] visited = new boolean[n];
        int[] parents = new int[n];
        Arrays.fill(parents, -1);

        //do prioritní fronty vložíme prvek ze kterého začíná hledání
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> fCosts[node]));
        priorityQueue.add(startIndex);

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            //pokud je první prvek v prioritní fronte koncovým indexem, uložíme cenu a vypíšeme cestu
            if (current == endIndex) {
                distance = gCosts[endIndex];
                return reconstructPath(parents, nodeIndices, endNode);
            }

            //označíme index
            visited[current] = true;

            for (int neighbor = 0; neighbor < n; neighbor++){
                if (adjacencyMatrix[current][neighbor] != 0 && !visited[neighbor]) {
                    //k vrcholu dáme příslušnou prozatimní cenu
                    int tempCost = gCosts[current] + adjacencyMatrix[current][neighbor];

                    //pokud je tato cena menší než ceny v matici vzdalenosti, vymenime
                    if (tempCost < gCosts[neighbor]) {
                        parents[neighbor] = current;  //pokud projde, je jasne ze nejkratsi cesta k sousedovi vede z nynejsiho vrcholu, ulozime do pole parents abychom se dostali k vrcholu

                        //ulozeni cen bez heuristiky a s ní
                        gCosts[neighbor] = tempCost;
                        fCosts[neighbor] = gCosts[neighbor] + heuristicCost(getKeyFromValue(nodeIndices, neighbor), withHeuristic);
                        //a nasledne pridame do prioritni fronty souseda
                        if (!priorityQueue.contains(neighbor)) {
                            priorityQueue.add(neighbor);
                        }
                        //a provádíme dokola, dokud nejsou vypočtené všechny vrcholy
                    }
                }
            }
        }
        return null;
    }

    /**
     * Metoda přičítající heuristickou cenu k té reálné.
     * @param node vrchol, se kterým chceme pracovat
     * @param withHeuristic true - h=ODHAD VZDÁLENOSTI VZDUŠNOU ČAROU, false - h=0
     * @return cena
     */
    public static int heuristicCost(char node, boolean withHeuristic) {
    if (withHeuristic) {
        //muzou byt pismena, ktere nemaji heuristiku
        if (heuristicMap.get(node) == null) {
            return 0;
        }
        return heuristicMap.get(node);
    }
    return 0;
    }

    /**
     * Metoda, která vrátí list vrcholů vedoucí nejkratší cestou ze startu do konce
     * @param parents pole předchůdců, díky němu dokážeme vypsat vrcholy
     * @param nodeIndices hashtabulka obsahující indexy jednotlivých vrcholů
     * @param endNode písmeno vrcholu, kterým se končí
     * @return List vrcholů path
     */
    public static List<Character> reconstructPath(int[] parents, Map<Character, Integer> nodeIndices, char endNode) {
        List<Character> path = new ArrayList<>();
        int endIndex = nodeIndices.get(endNode); //získáme si index
        int current = endIndex; //a jdeme od konce
        while (current != -1) { //-1 první vrchol
            path.add(getKeyFromValue(nodeIndices, current)); //získáme dané písmeno
            current = parents[current]; //a jdeme o index dále
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Metoda vracející klíč za pomocí hodnoty za ním uložené
     * @param map (tabulka, se kterou se bude pracovat)
     * @param value (hodnota, jejíž klíč se snažíme nalézt)
     * @return klíč, patřící k hodnotě/null
     */
    public static Character getKeyFromValue(Map<Character, Integer> map, int value) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }
}