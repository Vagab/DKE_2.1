import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import java.util.Hashtable;

public class introMenu {

    private static String guide = "Chinese Checkers is a game in which players race each other to see who can fill their destination triangle with colored pegs first. \n While the game is neither Chinese nor Checkers, it's a fun tactical game invented in Germany but based on an American game called Halma.\n You can play the game with two to six players.";
    private static int num=0;
    private static boolean abClick = false;
    private static boolean MCMCClick = false;
    private static boolean maxNClick = false;
    private static boolean humanClick = false;
    private static boolean randomClick = false;
    private static boolean greedyClick = false;
    private static JSlider slider;
    private static JFrame frame;
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

        frame = new JFrame("CHINESE CHECKERS");
        frame.setLocationRelativeTo(null);

        final JPanel status_panel = new JPanel();
        final JPanel gamestats_panel = new JPanel();

        gamestats_panel.setLayout(new GridLayout(40,40));
        frame.add(status_panel, BorderLayout.SOUTH);
        frame.add(gamestats_panel, BorderLayout.EAST);

        final JLabel status = new JLabel("Start Game - Player 1 begins, then in clockwise order");
        final JButton ok = new JButton("go");
        ok.addActionListener(e -> {
            frame.setVisible(false);
            checkifGood();
            launchGame();
            /*
            new CCheckers(1);
            num = 1;
            if(v1sclick==false){
                v1sclick = true;
                start1v1.setBackground(Color.RED);
            }
            else {
                start1v1.setBackground(null);
                v1sclick = false;
            }*/
        });

        status_panel.add(ok);
        status_panel.add(status);

        slider = new JSlider(1, 4);
        //slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Hashtable position = new Hashtable();
        position.put(1, new JLabel("2"));
        position.put(2, new JLabel("3"));
        position.put(3, new JLabel("4"));
        position.put(4, new JLabel("6"));

        final JPanel slid = new JPanel();

        slider.setLabelTable(position);
        slid.add(new JLabel("NUMBER OF PLAYERS"));
        slid.add(slider);
        frame.add(slid);

        // start buttons
        final JPanel control_panel = new JPanel();

        frame.add(control_panel, BorderLayout.NORTH);
        control_panel.add(new JLabel("AI MODE"));

        final JButton abPruning = new JButton("ABprunin");
        abPruning.addActionListener(e -> {
            //frame.setVisible(false);
            //new CCheckers(1);
            num = 1;
            if(abClick ==false){
                abClick = true;
                abPruning.setBackground(Color.RED);
            }
            else {
                abPruning.setBackground(null);
                abClick = false;
            }
        });


        final JButton MCMC = new JButton("MCMC");
        MCMC.addActionListener(e -> {
            //frame.setVisible(false);
            //new CCheckers(2);
            if(MCMCClick ==false){
                MCMCClick = true;
                MCMC.setBackground(Color.RED);
            }
            else {
                MCMC.setBackground(null);
                MCMCClick = false;
            }

        });


        final JButton maxN = new JButton("MaxN");
        maxN.addActionListener(e -> {
            //frame.setVisible(false);
            //new CCheckers(3);
            if(maxNClick ==false){
                maxNClick = true;
                maxN.setBackground(Color.RED);
            }
            else {
                maxN.setBackground(null);
                maxNClick = false;
            }
        });


        final JButton human = new JButton("Human");
        human.addActionListener(e -> {
            if(humanClick==false){
                humanClick = true;
                human.setBackground(Color.RED);
            }
            else {
                human.setBackground(null);
                humanClick = false;
            }

        });

        final JButton random = new JButton("Random");
        random.addActionListener(e -> {
            if(randomClick==false){
                randomClick = true;
                random.setBackground(Color.RED);
            }
            else {
                random.setBackground(null);
                randomClick = false;
            }

        });

        final JButton greedy = new JButton("Greedy");
        greedy.addActionListener(e -> {
            if(greedyClick==false){
                greedyClick = true;
                greedy.setBackground(Color.RED);
            }
            else {
                greedy.setBackground(null);
                greedyClick = false;
            }

        });


        control_panel.add(abPruning);
        control_panel.add(MCMC);
        control_panel.add(maxN);
        control_panel.add(human);
        control_panel.add(random);
        control_panel.add(greedy);

        final JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpScreen();
            }
        });
        control_panel.add(help);


        frame.setSize(500, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void checkifGood(){
        int value = slider.getValue();
        if(value==4){
            value=6;
            System.out.println(6+" players");
        }
        else{
            System.out.println((value+1)+" players");
        }
        System.out.println("ABpruning: "+ abClick);
        System.out.println("MCMC: "+ MCMCClick);
        System.out.println("MAXN: "+ maxNClick);
    }
    public static void launchGame(){
        int value = slider.getValue();
        switch(value){
            case 1:
                if(abClick &&!MCMCClick && !greedyClick && !randomClick){
                    new CCheckers(2,"2minimax");
                }
                else if(abClick && MCMCClick && !greedyClick && !randomClick){
                    new CCheckers(2, "1minimax1MCMC");
                }
                else if(abClick && !MCMCClick && greedyClick && !randomClick) {
                    new CCheckers(2,"1minimax1greedy");
                }
                else if(abClick && !MCMCClick  && !greedyClick && randomClick) {
                    new CCheckers(2,"1minimax1random");
                }
                else if(!abClick && MCMCClick  && !greedyClick && !randomClick){
                    new CCheckers(2,"2MCMC");
                }
                else if(!abClick && MCMCClick  && greedyClick && !randomClick){
                    new CCheckers(2,"1MCMC1greedy");
                }
                else if(!abClick && MCMCClick  && !greedyClick && randomClick){
                    new CCheckers(2,"1MCMC1random");
                }
                else if(!abClick && !MCMCClick  && greedyClick && !randomClick){
                    new CCheckers(2,"2greedy");
                }
                else if(!abClick && !MCMCClick  && greedyClick && randomClick){
                    new CCheckers(2,"1greedy1random");
                }
                else if(!abClick && !MCMCClick  && !greedyClick && randomClick){
                    new CCheckers(2,"2random");
                }

//                else if (!abClick &&!MCMCClick &&!maxNClick) {
//                    new CCheckers(2,"HumanVHuman");
//                }
//                else{
//                    frame.setVisible(true);
//                    System.out.println("dumbass");
//                }
            case 2:
                if(!abClick &&!MCMCClick && maxNClick){
                    System.out.println("test");
                    new CCheckers(3,"3maxN" ); //What is this?
                }
            case 3:
                if(!abClick &&!MCMCClick && maxNClick){
                    new CCheckers(4,"4maxN");
                }
            case 4:
                if(!abClick &&!MCMCClick && maxNClick){
                    new CCheckers(6,"6maxN");
                }
                else if(!abClick &&!MCMCClick &&!maxNClick &&humanClick){
                    new CCheckers(6,"6Human");
                }
                else if(!abClick && MCMCClick && maxNClick &&!humanClick){
                    new CCheckers(6,"4maxN2MCMC");
                }
        }


    }

}
