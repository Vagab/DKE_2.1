import java.awt.*;

public interface AI {
    
    final Color aiColor = Color.WHITE;

    public Node[] performMove(Graph board);
}