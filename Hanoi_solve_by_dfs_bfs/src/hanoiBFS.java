import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class hanoiBFS {
    public int count;
    public int ciselnik = 0;
    public final int DISKS = 3;
    public List<List<Integer>> arrayOfTowers;
    public List<List<Integer>> rightPosition;
    public HashSet<List<List<Integer>>> setOfNodes = new HashSet<>();
    public Queue queue;
    public Tree tree;
    public boolean foundSolution = false;

    /**
     * Konstruktor
     */
    public hanoiBFS(){
        arrayOfTowers = new LinkedList<>();
        rightPosition = new LinkedList<>();
    }

    /**
     *Metoda nastavující počáteční pozici a pozici potřebné pro vyřešení
     */
    public void initialize(){
        List<Integer> a = new LinkedList<>();
        //cisla disku znaci jejich velikost 1 - nejvetsi disk
        for(int i = 1; i <= DISKS; i++){
            a.add(i);
        }
        arrayOfTowers.add(0, a);
        arrayOfTowers.add(1, new LinkedList<>());
        arrayOfTowers.add(2, new LinkedList<>());

        rightPosition.add(0, new LinkedList<>());
        rightPosition.add(1, new LinkedList<>());
        rightPosition.add(2, a);

        queue = new Queue();
        setOfNodes.add(arrayOfTowers);
    }

    /**
     *  Metoda generující všechny možnosti přesunutí disku z aktuálního stavu
     * @param node node ze které se generují možnosti
     * @throws Exception když je fronta vyčerpána
     */
    public void generateOptions(Node node) throws Exception {
        List<List<Integer>> list = node.getData();
        //platí, že vždy jsou pouze maximalně 2 potomci
        count = 0;
        queue.remove();
        outer: for (int source = 0; source < 3; source++) {
            for (int destination = 0; destination < 3; destination++) {
                if (source != destination && isValid(list.get(source), list.get(destination))) {

                    List<List<Integer>> newConfiguration = new LinkedList<>();
                    for (List<Integer> tower : list) {
                        if (tower == null) {
                            newConfiguration.add(new LinkedList<>());
                        } else {
                            newConfiguration.add(new LinkedList<>(tower)); //kopie stavu
                        }
                    }

                    int diskToMove = newConfiguration.get(source).removeLast();
                    newConfiguration.get(destination).add(diskToMove);

                    if (!setOfNodes.contains(newConfiguration)) {
                        count++;
                        setOfNodes.add(newConfiguration);
                        Node node1 = new Node(newConfiguration, node);
                        ciselnik++;
                        node.addChild(node1);
                        queue.add(node1);

                        if(newConfiguration.equals(rightPosition)){
                            printToConsole(node1);
                            foundSolution = true;
                        }
                        //max 2 potomci
                        if(count == 2){
                            break outer;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda kontrolující, zdali je přesun validní
     * @param source ukládá pozici, ze které se bere kámen
     * @param destination pozice, na kterou má být kámen položen
     * @return true pokud ano, false pokud ne
     */
    public boolean isValid(List<Integer> source, List<Integer> destination) {
        return !source.isEmpty() && (destination == null || destination.isEmpty() || source.get(source.size() - 1) > destination.get(destination.size() - 1));
    }

    /**
     * Metoda, která generuje strom a postupně v něm hledá vyhovující pozici
     * @throws Exception (pokud je fronta vyprázdněna)
     */
    public void solve() throws Exception {
        tree = new Tree(arrayOfTowers);
        queue.add(tree.getRoot());
        generateOptions(tree.getRoot());
        while(queue.get() != null && !foundSolution){
            generateOptions(queue.get());
        }
    }

    /**
     * Metoda, která probíhá pokud jsme našli vyhovující větev ve stromě, následně
     * uloží všechny předchůdce až po kořen stromu do Listu, který následně v opačném pořadí vypíše
     *
     * @param node (vyhovující node se správným výsledkem)
     */
    public void printToConsole(Node node){
        List<Node> reserved = new LinkedList<>();
        reserved.add(node);
        while(node.getParent() != null){
            reserved.add(node.getParent());
            node = node.getParent();
        }
        List<Node> result = reserved.reversed();
        System.out.println("///////////////////////");
        System.out.println("by BFS:");
        System.out.println("BFS: " +ciselnik);
        for(int i = 0; i < reserved.size(); i++){
            System.out.println(i +". " + result.get(i).getData());
        }
        System.out.println("///////////////////////");
    }
}
