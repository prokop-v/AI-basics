import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static int backpackCapacity;
    public static Item[] itemsArray;
    public static int[][] newPopulation;

    public static void main(String[] args) {
        Item[] items = readInput();
        itemsArray = items;

        //uděláme dvourozměrné pole o populaci velké 10 chromozomů, a každý chromozom má itemsArray prvků
        int[][] population = generateRandomPopulation(10, itemsArray.length);
        newPopulation = new int[population.length][];

        //sem se uloží nejlepší populace po 100 iteracích
        int[][] bestPopulation = geneticAlgorithm(population);


        // a nakonec z nejlepší populace vybereme nejhodnotnější chromozóm
        int maxvalue = 0;
        int index = 0;
        int[] fitness = new int[bestPopulation.length];
        for (int i = 0; i < bestPopulation.length; i++) {
            fitness[i] = evaluateFitness(bestPopulation[i]);
            if (fitness[i] > maxvalue) {
                maxvalue = fitness[i];
                index = i;
            }
        }
        //Závěrečný výpis vybraných předmětů a jejich ceny
        System.out.println("///////////////////////////////");
        System.out.println("Byly vybrány tyto předměty:");
        for (int i = 0; i < itemsArray.length; i++) {
            if (bestPopulation[index][i] == 1) {
                System.out.println("Předmět " + (i + 1) + ", s cenou: " + itemsArray[i].value + " a váhou: " + itemsArray[i].weight);
            }
        }
        System.out.println("\nCelková cena je: " + fitness[index]);
        System.out.println("///////////////////////////////");

    }

    /**
     * Metoda vytvářející potomky rodičů, crossover point bude index cislo 2 (od indexu 3 změna)
     * @param child1 prvni potomek
     * @param child2 druhy potomek
     */
    public static void crossover(int[] child1, int[] child2){
        int[] temp = child1.clone();
        for (int i = 3; i < child1.length; i++){
            child1[i] = child2[i];
            child2[i] = temp[i];
        }
    }


    /**
     * Metoda, která se snaží zmutovat potomka
     * @param child vytvořený potomek, který prochází mutací
     */
    public static void mutate(int[] child) {
        Random random = new Random();
        double mutationRate = 0.05; //šance na zmutování

        for (int i = 0; i < child.length; i++) {
            if (random.nextDouble() < mutationRate) {
                child[i] = 1 - child[i]; //pokud je random nizsi nez mutationRate, pak prohazujeme bit
            }
        }
    }

    /**
     * Metoda generující random populaci na začátku simulace
     * @param populationSize požadovaná délka populace
     * @param chromosomeLength délka jednoho chromozómu populace
     * @return populace
     */
    public static int[][] generateRandomPopulation(int populationSize, int chromosomeLength) {
        int[][] population = new int[populationSize][chromosomeLength];
        Random random = new Random();

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < chromosomeLength; j++) {
                population[i][j] = random.nextInt(0,2); //Do populace generujeme náhodně čísla 1 a 0
            }
        }

        return population;
    }

    /**
     * Metoda parentBattle, kde probíhá souboj dvou náhodně vybraných chromozómů, ten který je hodnotnější, vracíme. Vede
     * k efektivnějšímu genetickému algoritmu
     * @param possibleParent1 možný rodič
     * @param possibleParent2 možný rodič
     * @return rodič
     */
    public static int[] parentBattle(int[] possibleParent1, int[] possibleParent2){
        int parentfit1 = evaluateFitness(possibleParent1);
        int parentfit2 = evaluateFitness(possibleParent2);
        if(parentfit1 > parentfit2)
            return possibleParent1;
        return possibleParent2;
    }


    /**
     * Metoda, která vypočítá fitness funkci - ta je součtem cen předmětů v batohu,
     * která nesmí překročit určitou váhu, pokud překročí, automaticky je value 0
     * @param chromozome chromozóm, jehož fitness funkci počítáme
     * @return cena chromozómu
     */
    public static int evaluateFitness(int[] chromozome) {

        int totalWeight = 0;
        int totalValue = 0;

        for (int i = 0; i < chromozome.length; i++) {
            //Sečteme hodnoty prvků které jsou vybrané
            if (chromozome[i] == 1) {
                totalWeight += itemsArray[i].weight;
                totalValue += itemsArray[i].value;
            }
        }
        // když je překrocena kapacita batohu, nastavíme value na 0
        if (totalWeight > backpackCapacity) {
            totalValue = 0;
        }
        //Vrátíme cenu chromozómu
        return totalValue;
    }

    /**
     * Metoda genetického algoritmu, ve kterém tvoříme potomky z vybraných rodičů, ty zkusíme zmutovat a poté
     * je přidáváme do nové populace, na konci přidáme do populace elitní chromozóm minulé populace
     * @param population první populace
     * @return populace po sto iteracích
     */
    public static int[][] geneticAlgorithm(int[][] population) {
        //Vytvoříme novou populaci celkem stokrát
        for (int x = 0; x < 100; x++) {

            //vytvoříme novou populaci
            for (int i = 0; i < 10; i = i + 2) {

                //Vybereme z populace 2 náhodný chromozómy, a ty mezi sebou bojují o to, kdo bude rodičem.
                int[] possibleParent1 = population[(int)(Math.random()*10)];
                int[] possibleParent2 = population[(int)(Math.random()*10)];
                int[] parent1 = parentBattle(possibleParent1, possibleParent2);

                //Vybereme z populace 2 náhodný chromozómy, a ty mezi sebou bojují o to, kdo bude rodičem.
                int[] possibleParent3 = population[(int)(Math.random()*10)];
                int[] possibleParent4 = population[(int)(Math.random()*10)];
                int[] parent2 = parentBattle(possibleParent3, possibleParent4);

                int[] child1 = parent1.clone();
                int[] child2 = parent2.clone();

                //zde vytvoříme potomky
                crossover(child1, child2);

                //a zkusíme je zmutovat
                mutate(child1);
                mutate(child2);

                //a přidáme do nového pole potomku
                newPopulation[i] = child1;
                newPopulation[i + 1] = child2;

                //nyní máme novou populaci plnou potomků
            }

            //ale ideálně pro efektivnost nechceme ztratit elitní chromozóm z předchozí generace, proto ho natvrdo nahradíme
            //za jiný chromozón vytvořený crossoverem i za cenu ztráty kvalitního chromozómu
            int maxvalue = 0;
            int index = 0;
            int[] fitness = new int[population.length];
            for (int i = 0; i < population.length; i++) {
                fitness[i] = evaluateFitness(population[i]);
                if (fitness[i] > maxvalue) {
                    maxvalue = fitness[i];
                    index = i;

                }
            }

            //na nulty index presunem nejlepsi prvek z prechozi varky
            newPopulation[0] = population[index];
            population = newPopulation.clone();
        }
        //A poslední populaci vrátíme
        return newPopulation;
    }

    /**
     * Metoda načítající vstupní soubor
     * @return pole Itemů
     */
    public static Item[] readInput() {
        try {
            File file = new File("cv6_vstup.txt");
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            backpackCapacity = Integer.parseInt(scanner.nextLine());
            scanner.nextLine();
            ArrayList<Item> items = new ArrayList<>();

            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                String neededData = data.substring(2, data.length());
                String[] itemData = neededData.split(";");
                int weight = Integer.parseInt(itemData[0]);
                int value = Integer.parseInt(itemData[1]);
                Item item = new Item(weight, value);
                items.add(item);
            }

            Item[] itemsArray = new Item[items.size()];
            int temp = items.size();
            for(int i = 0; i < temp; i++) {
            itemsArray[i] = items.getFirst();
            items.removeFirst();
            }

            scanner.close();
            return itemsArray;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }
}
