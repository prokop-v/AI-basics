import java.util.LinkedList;
import java.util.List;

public class Stack {
    public List<Node> array;

    public Stack(){
        array = new LinkedList<>();
    }

    public void add(Node e){
        array.add(e);
    }

    public Node get() throws Exception {
        if(array.isEmpty())
            throw new Exception();
        return array.getLast();
    }

    void remove() throws Exception {
        if (array.isEmpty())
            throw new Exception();
        array.remove(array.getLast());
    }

}
