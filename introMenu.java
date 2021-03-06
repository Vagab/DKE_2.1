import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;


public class introMenu {

    private static JFrame frame;
    private static GUI6players disp;
    private static String guide = "Chinese Checkers is a game in which players race each other to see who can fill their destination triangle with colored pegs first. \n While the game is neither Chinese nor Checkers, it's a fun tactical game invented in Germany but based on an American game called Halma.\n You can play the game with two to six players.";


    private static void helpScreen() {
        JDialog dialog = new JDialog(new Frame(), "Help");
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea text = new JTextArea(guide);
        text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        text.setEditable(false);
        dialog.setLocationRelativeTo(null);
        dialog.add(text);
        dialog.pack();
        dialog.setVisible(true);
    }


    public static void main(String[] args) {

            final JFrame frame = new JFrame("CHINESE CHECKERS");
            frame.setLocationRelativeTo(null);

            final JPanel status_panel = new JPanel();
            final JPanel gamestats_panel = new JPanel();

            gamestats_panel.setLayout(new GridLayout(3,21));
            frame.add(status_panel, BorderLayout.SOUTH);
            frame.add(gamestats_panel, BorderLayout.EAST);

            final JLabel status = new JLabel("Start Game - Player 1 begins, then in clockwise order");

            Font font = new Font("Serif", Font.BOLD, 25);

            status_panel.add(status);

            // start buttons
            final JPanel control_panel = new JPanel();
            frame.add(control_panel, BorderLayout.NORTH);


            final JButton start1v1 = new JButton("2 Players");
            start1v1.addActionListener(e -> {
                frame.setVisible(false);
                new CCheckers(2);
            });


            final JButton start1v2 = new JButton("3 Players");
            start1v2.addActionListener(e -> {
                frame.setVisible(false);
                new CCheckers(3);
            });


            final JButton start4v4 = new JButton("4 Players");
            start4v4.addActionListener(e -> {
                frame.setVisible(false);
                new CCheckers(4);
            });


            final JButton start6players = new JButton("6 Players");
            start6players.addActionListener(e -> {
                frame.setVisible(false);
                new CCheckers(6);
            });


        control_panel.add(start1v1);
        control_panel.add(start1v2);
        control_panel.add(start4v4);
        control_panel.add(start6players);

            final JButton help = new JButton("Help");
            help.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    helpScreen();
                }
            });
            control_panel.add(help);


            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        }


    public static void changeScene(){
        // disp = new DisplayEarth(true, 2000);
        disp.setLayout(new GridLayout(0, 10));

        //ads the GUI to the frame
        frame.setContentPane(disp);
        frame.setVisible(true);
    }

    }
