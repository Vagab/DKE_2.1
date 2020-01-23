import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class introMenu {

    private static String guide = "Chinese Checkers is a game in which players race each other to see who can fill their destination triangle with colored pegs first. \n While the game is neither Chinese nor Checkers, it's a fun tactical game invented in Germany but based on an American game called Halma.\n You can play the game with two to six players.";
    private static int num = 0;
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

    public introMenu() {
        abClick = false;
        MCMCClick = false;
        maxNClick = false;
        humanClick = false;
        randomClick = false;
        greedyClick = false;

        frame = new JFrame("CHINESE CHECKERS");
        frame.setLocationRelativeTo(null);

        final JPanel status_panel = new JPanel();
        final JPanel gamestats_panel = new JPanel();

        gamestats_panel.setLayout(new GridLayout(40, 40));
        frame.add(status_panel, BorderLayout.SOUTH);
        frame.add(gamestats_panel, BorderLayout.EAST);

        final JLabel status = new JLabel("Start Game - Player 1 begins, then in clockwise order");
        final JButton ok = new JButton("go");
        ok.addActionListener(e -> {
            frame.setVisible(false);
            checkIfGood();
            launchGame();
        });

        status_panel.add(ok);
        status_panel.add(status);

        slider = new JSlider(1, 4, 1);
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
            if (abClick == false) {
                abClick = true;
                abPruning.setBackground(Color.RED);
            } else {
                abPruning.setBackground(null);
                abClick = false;
            }
        });


        final JButton MCMC = new JButton("MCMC");
        MCMC.addActionListener(e -> {
            //frame.setVisible(false);
            //new CCheckers(2);
            if (MCMCClick == false) {
                MCMCClick = true;
                MCMC.setBackground(Color.RED);
            } else {
                MCMC.setBackground(null);
                MCMCClick = false;
            }

        });


        final JButton maxN = new JButton("MaxN");
        maxN.addActionListener(e -> {
            //frame.setVisible(false);
            //new CCheckers(3);
            if (!maxNClick) {
                maxNClick = true;
                maxN.setBackground(Color.RED);
            } else {
                maxN.setBackground(null);
                maxNClick = false;
            }
        });


        final JButton human = new JButton("Human");
        human.addActionListener(e -> {
            if (humanClick == false) {
                humanClick = true;
                human.setBackground(Color.RED);
            } else {
                human.setBackground(null);
                humanClick = false;
            }

        });

        final JButton random = new JButton("Random");
        random.addActionListener(e -> {
            if (randomClick == false) {
                randomClick = true;
                random.setBackground(Color.RED);
            } else {
                random.setBackground(null);
                randomClick = false;
            }

        });

        final JButton greedy = new JButton("Greedy");
        greedy.addActionListener(e -> {
            if (greedyClick == false) {
                greedyClick = true;
                greedy.setBackground(Color.RED);
            } else {
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


    public static void main(String[] args) {

        frame = new JFrame("CHINESE CHECKERS");
        frame.setLocationRelativeTo(null);

        final JPanel status_panel = new JPanel();
        final JPanel gamestats_panel = new JPanel();

        gamestats_panel.setLayout(new GridLayout(40, 40));
        frame.add(status_panel, BorderLayout.SOUTH);
        frame.add(gamestats_panel, BorderLayout.EAST);

        final JLabel status = new JLabel("Start Game - Player 1 begins, then in clockwise order");
        final JButton ok = new JButton("go");
        ok.addActionListener(e -> {
            frame.setVisible(false);
            checkIfGood();
            launchGame();
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
            num = 1;
            if (abClick == false) {
                abClick = true;
                abPruning.setBackground(Color.RED);
            } else {
                abPruning.setBackground(null);
                abClick = false;
            }
        });


        final JButton MCMC = new JButton("MCMC");
        MCMC.addActionListener(e -> {
            if (MCMCClick == false) {
                MCMCClick = true;
                MCMC.setBackground(Color.RED);
            } else {
                MCMC.setBackground(null);
                MCMCClick = false;
            }

        });


        final JButton maxN = new JButton("MaxN");
        maxN.addActionListener(e -> {
            if (maxNClick == false) {
                maxNClick = true;
                maxN.setBackground(Color.RED);
            } else {
                maxN.setBackground(null);
                maxNClick = false;
            }
        });


        final JButton human = new JButton("Human");
        human.addActionListener(e -> {
            if (humanClick == false) {
                humanClick = true;
                human.setBackground(Color.RED);
            } else {
                human.setBackground(null);
                humanClick = false;
            }

        });

        final JButton random = new JButton("Random");
        random.addActionListener(e -> {
            if (randomClick == false) {
                randomClick = true;
                random.setBackground(Color.RED);
            } else {
                random.setBackground(null);
                randomClick = false;
            }

        });

        final JButton greedy = new JButton("Greedy");
        greedy.addActionListener(e -> {
            if (greedyClick == false) {
                greedyClick = true;
                greedy.setBackground(Color.RED);
            } else {
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


    public static void checkIfGood() {
        int value = slider.getValue();
        if (value == 4) {
            value = 6;
            System.out.println(6 + " players");
        } else {
            System.out.println((value + 1) + " players");
        }
        System.out.println("ABpruning: " + abClick);
        System.out.println("MCMC: " + MCMCClick);
        System.out.println("MAXN: " + maxNClick);
        System.out.println("GREEDY: " + greedyClick);
        System.out.println("RANDOM: " + randomClick);
        System.out.println("HUMAN: " + humanClick);
    }

    public static void launchGame() {
        int value = slider.getValue();
        System.out.println(value);
        switch (value) {
            case 1:
                if (maxNClick) {
                    System.out.println("Invalid combination!");
                    introMenu introMenu = new introMenu();
                    introMenu.getFrame().setVisible(true);
                    break;
                } else if (!abClick && MCMCClick && !greedyClick && !randomClick && humanClick) {
                    if(!abClick &&MCMCClick && !greedyClick && !randomClick && humanClick){
                        //new CCheckers(2,"1MCMC1human");
                        try {
                            // runProcess("pwd");
                            System.out.println("**********");
                            // runProcess("javac -cp Group10/MCMC/introMenu.java");
                            //  C:\Users\lucas\Desktop\DKE\CCheckers_QLearning\src
                            System.out.println("**********");

                            String chosenPlayers = "2";

                            File dir = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC\\src");
                            String cmd = "javac introMenu.java";
                            Process process = Runtime.getRuntime().exec(cmd, null, dir);

                            File dir2 = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC\\src");
                            String cmd2 = "java introMenu " + chosenPlayers;
                            Process process2 = Runtime.getRuntime().exec(cmd2, null, dir2);

                            System.exit(0);
                            // runProcess("java -cp introMenu " + chosenPlayers);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("crash");
                        }
                    }
                    break;
                } else if (abClick && !MCMCClick && !greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "2minimax");
                    break;
                } else if (abClick && MCMCClick && !greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "1minimax1MCMC");
                    break;
                } else if (abClick && !MCMCClick && greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "1minimax1greedy");
                    break;
                } else if (abClick && !MCMCClick && !greedyClick && randomClick && !humanClick) {
                    new CCheckers(2, "1minimax1random");
                    break;
                } else if (abClick && !MCMCClick && !greedyClick && !randomClick && humanClick) {
                    new CCheckers(2, "1minimax1human");
                    break;
                } else if (!abClick && MCMCClick && !greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "2MCMC");
                    break;
                } else if (!abClick && MCMCClick && greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "1MCMC1greedy");
                    break;
                } else if (!abClick && MCMCClick && !greedyClick && randomClick && !humanClick) {
                    new CCheckers(2, "1MCMC1random");
                    break;
                } else if (!abClick && !MCMCClick && greedyClick && !randomClick && !humanClick) {
                    new CCheckers(2, "2greedy");
                    break;
                } else if (!abClick && !MCMCClick && greedyClick && randomClick && !humanClick) {
                    new CCheckers(2, "1greedy1random");
                    break;
                } else if (!abClick && !MCMCClick && greedyClick && !randomClick && humanClick) {
                    new CCheckers(2, "1greedy1human");
                    break;
                } else if (!abClick && !MCMCClick && !greedyClick && randomClick && !humanClick) {
                    new CCheckers(2, "2random");
                    break;
                } else if (!abClick && !MCMCClick && !greedyClick && randomClick && humanClick) {
                    new CCheckers(2, "1random1human");
                    break;
                } else if (!abClick && !MCMCClick && !greedyClick && !randomClick && humanClick) {
                    new CCheckers(2, "2human");
                    break;
                }
                break;
            case 2:
                if (abClick ||(!humanClick && (greedyClick || randomClick))) {
                    System.out.println("Invalid combination!");
                    introMenu introMenu = new introMenu();
                    introMenu.getFrame().setVisible(true);
                    break;
                } else if (!MCMCClick && maxNClick && !humanClick) { //maxN 3x
                    new CCheckers(3, "3maxN");
                    break;
                } else if (!MCMCClick && maxNClick && !greedyClick && !randomClick && humanClick) { //2x maxN vs 1 human
                    System.out.println("Clicked on 2maxN1human");
                    new CCheckers(3, "2maxN1human");
                    break;
                } else if (!MCMCClick && !maxNClick && greedyClick && !randomClick && humanClick) {
                    new CCheckers(3, "2greedy1human");
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && randomClick && humanClick) {
                    new CCheckers(3, "2random1human");
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && !randomClick && humanClick ){
                    new CCheckers(3,"3human");
                    break;
                }

                else if (MCMCClick && !maxNClick && humanClick && !greedyClick && !randomClick) {//2x MCMC vs 1 human
                    try {
                        // runProcess("pwd");
                        System.out.println("**********");
                        // runProcess("javac -cp Group10/MCMC/introMenu.java");
                        //  C:\Users\lucas\Desktop\DKE\CCheckers_QLearning\src
                        System.out.println("**********");

                        String chosenPlayers = "3";

                        File dir = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd = "javac introMenu.java";
                        Process process = Runtime.getRuntime().exec(cmd, null, dir);

                        File dir2 = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd2 = "java introMenu " + chosenPlayers;
                        Process process2 = Runtime.getRuntime().exec(cmd2, null, dir2);
                        System.exit(0);
                        // runProcess("java -cp introMenu " + chosenPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("crash");
                    }
                }
                break;
            case 3:
                if (abClick || (!humanClick && (greedyClick || randomClick))) {
                    System.out.println("Invalid combination!");
                    introMenu introMenu = new introMenu();
                    introMenu.getFrame().setVisible(true);
                    break;
                } else if (!MCMCClick && maxNClick && !humanClick) {
                    new CCheckers(4, "4maxN");
                    break;
                } else if (!MCMCClick && maxNClick && !greedyClick && !randomClick && humanClick) {
                    new CCheckers(4, "3maxN1human");
                    break;
                } else if (!MCMCClick && !maxNClick && greedyClick && !randomClick && humanClick) {
                    new CCheckers(4, "3greedy1human");
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && randomClick && humanClick) {
                    new CCheckers(4, "3random1human");
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && !randomClick && humanClick ){
                    new CCheckers(4,"4human");
                    break;
                } else if (MCMCClick && !maxNClick && humanClick && !greedyClick && !randomClick) {
                    try {
                        // runProcess("pwd");
                        System.out.println("**********");
                        // runProcess("javac -cp Group10/MCMC/introMenu.java");
                        //  C:\Users\lucas\Desktop\DKE\CCheckers_QLearning\src
                        System.out.println("**********");

                        String chosenPlayers = "4";

                        File dir = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd = "javac introMenu.java";
                        Process process = Runtime.getRuntime().exec(cmd, null, dir);

                        File dir2 = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd2 = "java introMenu " + chosenPlayers;
                        Process process2 = Runtime.getRuntime().exec(cmd2, null, dir2);
                        System.exit(0);
                        // runProcess("java -cp introMenu " + chosenPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("crash");
                    }
                }
                break;
            case 4:
                if (abClick ||(!humanClick && (greedyClick || randomClick))) {
                    System.out.println("Invalid combination!");
                    introMenu introMenu = new introMenu();
                    introMenu.getFrame().setVisible(true);
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && !randomClick && humanClick) {
                    new CCheckers(6, "6human");
                    break;
                } else if (!MCMCClick && !maxNClick && greedyClick && !randomClick && humanClick) {
                    new CCheckers(6,"5greedy1human");
                    break;
                } else if (!MCMCClick && !maxNClick && !greedyClick && randomClick && humanClick) {
                    new CCheckers(6,"5random1human");
                    break;
                } else if (!MCMCClick && maxNClick && !greedyClick && !randomClick && humanClick) {
                    new CCheckers(6,"5maxN1human");
                    break;
                } else if (MCMCClick && !maxNClick && humanClick && !greedyClick && !randomClick) {
                    try {
                        // runProcess("pwd");
                        System.out.println("**********");
                        // runProcess("javac -cp Group10/MCMC/introMenu.java");
                        //  C:\Users\lucas\Desktop\DKE\CCheckers_QLearning\src
                        System.out.println("**********");

                        String chosenPlayers = "6";

                        File dir = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd = "javac introMenu.java";
                        Process process = Runtime.getRuntime().exec(cmd, null, dir);

                        File dir2 = new File("C:\\Users\\Jel Vankan\\IdeaProjects\\ChineseCheckers__Final__\\MCMC");
                        String cmd2 = "java introMenu " + chosenPlayers;
                        Process process2 = Runtime.getRuntime().exec(cmd2, null, dir2);
                        System.exit(0);
                        // runProcess("java -cp introMenu " + chosenPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("crash");
                    }
                }
                break;
        }


    }

    public static JFrame getFrame() {
        return frame;
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            //   System.out.println(cmd + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        //  System.out.println(command + " exitValue() " + pro.exitValue());
    }

}
