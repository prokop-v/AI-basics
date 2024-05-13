import java.util.LinkedList;
import java.util.List;

public class Queue {
    public List<Node> array;

    public Queue(){
        array = new LinkedList<>();
    }
    public Node get() throws Exception {
        if(array.isEmpty())
            throw new Exception();
        return array.getFirst();
    }
    void add(Node e){
        array.add(e);
    }

    void remove() throws Exception {
        if (array.isEmpty())
            throw new Exception();
        array.remove(array.getFirst());
    }

}
