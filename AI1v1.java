import java.awt.*;


public interface AI1v1 {

    final Color aiColor = Color.WHITE;

    public Node[] performMove(Graph board);
    public double getBestValue();
    public double[] getBestFeatures();
    public double getRawScore();
    public Color getColor();
    public void resetTrajectory();
}
