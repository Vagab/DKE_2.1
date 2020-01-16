import javax.swing.*;
import java.awt.*;

public class CCheckers {

    private static JFrame frame;
    private static GUI disp;
    private static GUI6players dispMulti;
    private static int numberOfPlayers;


    CCheckers(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        frame = new JFrame("Chinese Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1200, 1000);

        //our GUI + physics
        if (numberOfPlayers == 2){
            disp = new GUI();
            disp.setLayout(new GridLayout(0, 10));
            frame.setContentPane(disp);

        }
        else {
            dispMulti = new GUI6players(numberOfPlayers);
            dispMulti.setLayout(new GridLayout(0, 10));
            frame.setContentPane(dispMulti);
        }

        frame.setVisible(true);
    }

}
