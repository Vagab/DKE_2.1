import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CCheckers {

    private static JFrame frame;
    private static GUI disp;
    private static GUI6players disp6;
    private static int numberOfPlayers;
    private static String gameType;


    CCheckers(int numberOfPlayers, String gameType) {

        this.numberOfPlayers = numberOfPlayers;
        this.gameType = gameType;
        frame = new JFrame("Chinese Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1200, 1000);

        if (numberOfPlayers == 2) {
            switch (gameType) {
                case "1MCMC1Human":
                    disp = new GUI("1MCMC1human");
                    break;
                case "2minimax":
                    disp = new GUI("2minimax");
                    break;
                case "2MCMC":
                    disp = new GUI("2MCMC");
                    break;
                case "1minimax1MCMC":
                    disp = new GUI("1minimax1MCMC");
                    break;
                case "1minimax1greedy":
                    System.out.println("Click!!!&!");
                    disp = new GUI("1minimax1greedy");
                    break;
                case "1minimax1random":
                    disp = new GUI("1minimax1random");
                    break;
                case "1greedy1random":
                    disp = new GUI("1greedy1random");
                    break;
                case "2greedy":
                    disp = new GUI("2greedy");
                    break;
                case "2random":
                    disp = new GUI("2random");
                    break;
                case "1MCMC1greedy":
                    disp = new GUI("1MCMC1greedy");
                    break;
                case "1MCMC1random":
                    disp = new GUI("1MCMC1random");
                    break;
                case "1minimax1human":
                    disp = new GUI("1minimax1human");
                    break;
                case "1greedy1human":
                    disp = new GUI("1greedy1human");
                    break;
                case "1random1human":
                    disp = new GUI("1random1human");
                    break;
                case "2human":
                    disp = new GUI("2human");
                    break;
            }
            disp.setLayout(new GridLayout(0, 10));
            frame.setContentPane(disp);
        } else if (numberOfPlayers == 3) {
            switch (gameType) {
                case "2maxN1MCMC":
                    disp6 = new GUI6players(3, "2maxN1MCMC");
                    break;
                case "3maxN":
                    disp6 = new GUI6players(3, "3maxN");
                    break;
                case "2maxN1human":
                    disp6 = new GUI6players(3, "2maxN1human");
                    break;
                case "2greedy1human":
                    disp6 = new GUI6players(3, "2greedy1human");
                    break;
                case "2random1human":
                    disp6 = new GUI6players(3, "2random1human");
                    break;
                case "3human":
                    disp6 = new GUI6players(3,"3human");
                    break;
            }
            disp6.setLayout(new GridLayout(0, 10));
            frame.setContentPane(disp6);
        } else if (numberOfPlayers == 4) {
            switch (gameType) {
                case "3MCMC1Human":
                    disp = new GUI("3MCMC1Human");
                    break;
                case "4maxN": {
                    disp6 = new GUI6players(4, "4maxN");
                    break;
                }
                case "3maxN1human": {
                    disp6 = new GUI6players(4, "3maxN1human");
                    break;
                }
                case "3greedy1human": {
                    disp6 = new GUI6players(4, "3greedy1human");
                    break;
                }
                case "3random1human": {
                    disp6 = new GUI6players(4, "3random1human");
                    break;
                }
                case "4human": {
                    disp6 = new GUI6players(4,"4human");
                    break;
                }
            }
            disp6.setLayout(new GridLayout(0, 10));
            frame.setContentPane(disp6);
        } else if (numberOfPlayers == 6) {
            switch (gameType) {
                case "6maxN":
                    disp6 = new GUI6players(6, "6maxN");
                    break;
                case "6human":
                    disp6 = new GUI6players(6, "6human");
                    break;
                case "5greedy1human":
                    disp6 = new GUI6players(6, "5greedy1human");
                    break;
                case "5random1human":
                    disp6 = new GUI6players(6, "5random1human");
                    break;
                case "5maxN1human":
                    disp6 = new GUI6players(6, "5greedy1human");
                    break;
            }
            disp6.setLayout(new GridLayout(0, 10));
            frame.setContentPane(disp6);
        }
        JButton exitButton = new JButton("Exit To Menu");
        exitButton.addActionListener(e -> {
                    frame.setVisible(false);
                    introMenu introMenu = new introMenu();
                    JFrame frame2 = introMenu.getFrame();
                    frame2.setVisible(true);
                }
        );
        frame.add(exitButton);

        frame.setVisible(true);
    }

    private void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            //   System.out.println(cmd + " " + line);
        }
    }

    private void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        //  System.out.println(command + " exitValue() " + pro.exitValue());
    }

}
