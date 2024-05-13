import java.util.LinkedList;
import java.util.List;

public class Tree {
    private Node root;

    public Tree(List<List<Integer>> rootData) {
        root = new Node(rootData, null);
    }

    public Node getRoot() {
        return root;
    }
}