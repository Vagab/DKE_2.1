import java.awt.*;
import java.util.ArrayList;



public interface GraphSim {

    public Node[] getNodes();
    public Color getNodeColor(int i);
    public Node getSecNode(int node);
    public ArrayList<Node> popularChoice(Node n);
}
