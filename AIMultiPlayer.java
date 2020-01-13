import java.awt.*;

public interface AIMultiPlayer{

    final Color aiColor = Color.WHITE;

    public Node[] performMove(GraphOscar board);
}