
import java.util.LinkedList;
import java.util.List;

class Node {
    public List<List<Integer>> data;
    public Node parent;
    public List<Node> children;

    public Node(List<List<Integer>> data, Node parent) {
        this.data = data;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public List<List<Integer>> getData() {
        return data;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}