import javax.swing.*;
import java.awt.*;

public class CCheckers {

    private static JFrame frame;
    private static GUI disp;
    private static GUI6players dispMulti;
    private static int numberOfPlayers;
    private static String gameType;


    CCheckers(int numberOfPlayers,String gameType) {

        this.numberOfPlayers = numberOfPlayers;
        this.gameType = gameType;
        frame = new JFrame("Chinese Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1200, 1000);

        if (numberOfPlayers == 2){
            if (gameType.equals("2minimax")) {
                disp = new GUI("2minimax");
                disp.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp);
            }
            else if (gameType.equals("2MCMC")){
                disp = new GUI("2MCMC");
                disp.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp);
            }
            else if (gameType.equals("1minimax1MCMC")) {
                disp = new GUI("1minimax1MCMC");
                disp.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp);
            }
            else if (gameType.equals("HumanVHuman")) {
                disp = new GUI("HumanVHuman");
                disp.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp);
            }
        }
        else if (numberOfPlayers == 3){
            if (gameType.equals("2maxN1MCMC")) {
                GUI6players disp6 = new GUI6players(3,"2maxN1MCMC");
                disp6.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp6);
            }
        }
        else if (numberOfPlayers == 4){
            if (gameType.equals("4maxN")) {
                GUI6players disp6 = new GUI6players(4,"4maxN");
                disp6.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp6);
            }
        }
        else if (numberOfPlayers == 6){
            if (gameType.equals("6maxN")) {
                GUI6players disp6 = new GUI6players(6,"6maxN");
                disp6.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp6);
            }
            else if (gameType.equals("4maxN2MCMC")) {
                GUI6players disp6 = new GUI6players(6,"4maxN2MCMC");
                disp6.setLayout(new GridLayout(0, 10));
                frame.setContentPane(disp6);
            }
        }
//        else {
//            dispMulti = new GUI6players(numberOfPlayers);
//            dispMulti.setLayout(new GridLayout(0, 10));
//            frame.setContentPane(dispMulti);
//        }

        frame.setVisible(true);
    }

}
