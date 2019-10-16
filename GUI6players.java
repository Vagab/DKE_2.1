import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;


public class GUI6players extends JComponent {

    private Image backgroundImage;
    Graphics g;
    GraphOscar board;
    //Region regions;
    int delay = 1000;
    Scanner scan = new Scanner(System.in);

    ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();


    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public static boolean firstMove = false;

    static long start = System.nanoTime();
    boolean player1 = true;
    int currentPlayer = 1;
    int turnsCount = 1;
    int nrsP;

    public GUI6players(int n) {

        this.nrsP = n;
        if(n==4){
            currentPlayer=2;
        }
        colorsOfPlayers.add(Color.BLUE);  //player 1
        colorsOfPlayers.add(Color.RED);   //player 2
        /*
        colorsOfPlayers.add(Color.YELLOW);
        colorsOfPlayers.add(Color.GREEN);
        colorsOfPlayers.add(Color.BLACK);
        colorsOfPlayers.add(Color.GRAY);
        */

        //new Timer(delay, taskPerformer).start();
        this.board = new GraphOscar(n);
        this.nodeList = board.getNodes();

        GUI6players.MousePressListener var1 = new GUI6players.MousePressListener();
        this.addMouseListener(var1);

    }


    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        if (timeElapsed % 1000000000 == 0)
            repaint();

    /*     try {   //sets the background picture
        backgroundImage = ImageIO.read(new File("C:\\Users\\lucas\\Desktop\\DKE\\CCheckers\\src\\pic1.jpg"));
        g2.drawImage(backgroundImage, 0, 0, null);
        } catch(IOException e){
          throw new RuntimeException(e);
      }*/

        GradientPaint gp1 = new GradientPaint(50, 1, Color.blue, 20, 20, Color.lightGray, true);
        GradientPaint gp2 = new GradientPaint(100, 100, Color.green, 5, 5, Color.lightGray, true);
        GradientPaint gp3 = new GradientPaint(45, 45, Color.yellow, 70, 70, Color.orange, true);
        GradientPaint gp4 = new GradientPaint(5, 5, Color.darkGray, 20, 20, Color.red, true);
        //GradientPaint selected = new GradientPaint()
        //  g2.setPaint(Color.CYAN);
        //  g2.fillRect(0,0,2500,2500);


        if (isWinningCondition(currentPlayer)) {
            if (currentPlayer == 1) {
                JOptionPane.showMessageDialog(this,
                        currentPlayer + " has won!", "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
            } else if (currentPlayer == 2) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
            } else if (currentPlayer == 3) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 3 HAS WON", JOptionPane.WARNING_MESSAGE);
            }
            else if (currentPlayer == 4) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 4 HAS WON", JOptionPane.WARNING_MESSAGE);
            }
            else if (currentPlayer == 5) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 5 HAS WON", JOptionPane.WARNING_MESSAGE);
            }
            else if (currentPlayer == 6) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 6 HAS WON", JOptionPane.WARNING_MESSAGE);
            }

        } else if (currentPlayer == 1) {
            g2.setPaint(Color.BLUE);
            g2.drawString("It is Player " + currentPlayer + "'turn", 1000, 100);
        } else if (currentPlayer == 2) {
            g2.setPaint(Color.GRAY);
            g2.drawString("It is Player " + currentPlayer + "'turn", 1000, 100);
        } else if (currentPlayer == 3){
            g2.setPaint(Color.ORANGE);
            g2.drawString("It is player " + currentPlayer + "'turn", 1000,100);
        } else if(currentPlayer == 4) {
            g2.setPaint(Color.RED);
            g2.drawString("It is player " + currentPlayer + "'turn", 1000,100);
        }
        else if(currentPlayer == 5) {
            g2.setPaint(Color.BLACK);
            g2.drawString("It is player " + currentPlayer + "'turn", 1000, 100);
        }
        else {
            g2.setPaint(Color.GREEN);
            g2.drawString("It is player " + currentPlayer + "' turn",1000,100);
        }


        g2.setPaint((Color.BLACK));
        g2.drawString("Turns count: " + turnsCount, 1000, 120);

        g2.setPaint((Color.BLACK));
        g2.drawString("Time elapsed: " + timeElapsed / 1000000000 + " seconds", 1000, 140);


        g2.setPaint(Color.WHITE);

        for (int i = 0; i < nodeList.length; i++) {

            g2.setPaint(board.getNodeColor(i));

            if (i < 10) {    // 0 <= i <= 9  Player 1 zone
                g2.fillOval(board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 100, 40, 40);
                g2.setPaint(Color.ORANGE);
                g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * 60 + 715 - board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 120);
            }
           else if (i <= 55) {

                g2.fillOval( + board.getNodeYCoords(i) * 60 + 715 - board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 110, 40, 40);
                g2.setPaint(Color.BLACK);
                g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * 60 + 730 - board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 130);;

            }

            else if (i <= 64) {
                g2.fillOval(15 + board.getNodeYCoords(i) * 60 + 220 + board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 113, 40, 40);
                g2.setPaint(Color.BLACK);
                g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * 60 + 248 + board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 135);
                ;
            } else if (i <= 110) {

                g2.fillOval(board.getNodeYCoords(i) * 60  + 235 + board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 120, 40, 40);
                g2.setPaint(Color.GREEN);
                g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * 60 + 245 + board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 140);
            } else {
                g2.fillOval(board.getNodeYCoords(i) * 60 + 725 - (16 - board.getNodeXCoords(i)) * 30, board.getNodeXCoords(i) * 40 + 130, 40, 40);
                g2.setPaint(Color.GREEN);
                g2.drawString(board.getNodeLabel(i), 15 + board.getNodeYCoords(i) * 60 + 725 - (16 - board.getNodeXCoords(i)) * 30, 20 + board.getNodeXCoords(i) * 40 + 130);
            }


        }

    }

    public boolean isWinningCondition(int currentPlayer) {

        /*
                 Winning Condition


                  Triangle 1(RED)

        Triangle 6(ORANGE)     Triangle 2(BLACK)

        Triangle 5(GRAY)     Triangle 3(GREEN)

                 Triangle 4(BLUE)
         */

        //for triangles 1 and 4 it is easy to check if the opponent succeeded in moving all his pieces in there


        if (currentPlayer == 1) {
            for (int i = 0; i <= 9; i++) {
                if (!board.getNodeColor(i).equals(Color.RED))
                    return false;
            }
            return false;
        }
        else if (currentPlayer == 2) {    //checking for BLACK

            for (int i = 0; i < 4; i++) {
                if (!board.getNodeColor(15 + i).equals(Color.BLACK))
                    return false;
            }
            for (int i = 0; i < 3; i++) {
                if (!board.getNodeColor(29 + i).equals(Color.BLACK))
                    return false;
            }
            for (int i = 0; i < 2; i++) {
                if (!board.getNodeColor(42 + i).equals(Color.BLACK))
                    return false;
            }
            if (!board.getNodeColor(54).equals(Color.BLACK))
                return false;

            return true;
        }

        else if (currentPlayer == 3) {   //checking for green

                for (int i = 0; i < 4; i++) {
                    if (!board.getNodeColor(103 + i).equals(Color.GREEN))
                        return false;
                }
                for (int i = 0; i < 3; i++) {
                    if (!board.getNodeColor(92 + i).equals(Color.GREEN))
                        return false;
                }
                for (int i = 0; i < 2; i++) {
                    if (!board.getNodeColor(82 + i).equals(Color.GREEN))
                        return false;
                }
                if (!board.getNodeColor(73).equals(Color.GREEN))
                    return false;

                return true;
            }


        else if (currentPlayer == 4) {

            for (int i = 120; i > 110; i--) {
                if (!board.getNodeColor(i).equals(Color.BLUE))
                    return false;
            }
            return true;
        }

        else if (currentPlayer == 5) {   // checking for GRAY

            for (int i = 0; i < 4; i++) {
                if (!board.getNodeColor(110 - i).equals(Color.GRAY))
                    return false;
            }
            for (int i = 0; i < 3; i++) {
                if (!board.getNodeColor(97 - i).equals(Color.GRAY))
                    return false;
            }
            for (int i = 0; i < 2; i++) {
                if (!board.getNodeColor(85 - i).equals(Color.GRAY))
                    return false;
            }
            if (!board.getNodeColor(74).equals(Color.GRAY))
                return false;

            return true;
        }

        else if (currentPlayer == 6) {    // checking for ORANGE

            for (int i = 0; i < 4; i++) {
                if (!board.getNodeColor(22 - i).equals(Color.ORANGE))
                    return false;
            }
            for (int i = 0; i < 3; i++) {
                if (!board.getNodeColor(34 - i).equals(Color.ORANGE))
                    return false;
            }
            for (int i = 0; i < 2; i++) {
                if (!board.getNodeColor(45 - i).equals(Color.ORANGE))
                    return false;
            }
            if (!board.getNodeColor(55).equals(Color.ORANGE))
                return false;

            return true;
        }
        return false;




    }


    public void clickedForNode(int var1, int var2) {

        for (int i = 0; i <= 120; i++) {

            if (i <= 10) {

                if (var1 >= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 100
                        && var2 <= board.getNodeXCoords(i) * 40 + 100 + 40) {

                    setMove(i);
                }


                // g2.fillOval(15 + board.getNodeYCoords(i) * 60 + 710 - board.getNodeXCoords(i) * 30, board.getNodeXCoords(i) * 40 + 103, 40, 40);

            } else if (i <= 54) {
                if (var1 >= board.getNodeYCoords(i) * 60 + 710 - board.getNodeXCoords(i) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 710 - board.getNodeXCoords(i) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 100
                        && var2 <= board.getNodeXCoords(i) * 40 + 100 + 40) {
                    setMove(i);
                }

            } else if (i <= 64) {

                if (var1 >= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 100
                        && var2 <= board.getNodeXCoords(i) * 40 + 100 + 40) {
                    setMove(i);
                }
            } else if (i <= 110) {

                if (var1 >= board.getNodeYCoords(i) * 60 + 235 + board.getNodeXCoords(i) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 235 + board.getNodeXCoords(i) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 120
                        && var2 <= board.getNodeXCoords(i) * 40 + 120 + 40) {
                    setMove(i);
                }



            } else {    // 111 <= i <= 120

                if (var1 >= board.getNodeYCoords(i) * 60 + 725 - (16 - board.getNodeXCoords(i)) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 720 - (16 - board.getNodeXCoords(i)) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 130
                        && var2 <= board.getNodeXCoords(i) * 40 + 130 + 40) {
                    setMove(i);
                }

            }
        }

        /*System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);*/
    }

    public void setMove(int chosenNode) {
        higlight(board.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        ArrayList<Node> stopBugs = board.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;
        for(int i=0; i<stopBugs.size(); i++){
            if(stopBugs.get(i)==nodeList[selectedNode]){
                allgood = true;
            }
        }
        if(allgood==false){
            removeOneHighlight(nodeList[previousSelectedNode]);
        }

        higlight(board.getSecNode(chosenNode));

        if (previousSelectedNode == selectedNode) {
            removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }

        if (((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && currentPlayer == 1)
                || (nodeList[previousSelectedNode].getColor().equals(Color.GRAY) && currentPlayer == 2)
                || (nodeList[previousSelectedNode].getColor().equals(Color.ORANGE) && currentPlayer == 3)
                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && currentPlayer == 4)
                || (nodeList[previousSelectedNode].getColor().equals(Color.BLACK) && currentPlayer == 5)
                || (nodeList[previousSelectedNode].getColor().equals(Color.GREEN) && currentPlayer == 6)


        ))

        {
            this.firstMove = false;


            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {

                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                switch(nrsP){
                    case 2:
                        if(currentPlayer==4){
                            currentPlayer=1;
                            turnsCount++;
                        }
                        else{
                            currentPlayer=currentPlayer+3;
                        }
                        break;
                    case 3:
                        if(currentPlayer==5){
                            currentPlayer=1;
                            turnsCount++;
                        }
                        else{
                            currentPlayer=currentPlayer+2;
                        }
                        break;
                    case 4:
                        if(currentPlayer==3){
                            currentPlayer=5;
                            turnsCount++;
                        }
                        else if(currentPlayer==6){
                            currentPlayer=2;
                        }
                        else{
                            currentPlayer++;
                        }
                        break;
                    case 6:
                        if (currentPlayer == 6) {
                            currentPlayer = 1;
                            turnsCount++;
                        } else {
                            currentPlayer++;
                        }
                        break;
                }
            }
        }


        repaint();

    }

    public void higlight(Node n) {
        if (currentPlayer == 1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        } else if (currentPlayer == 2 && n.getColor() == Color.GRAY) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }
        else if (currentPlayer == 3 && n.getColor() == Color.ORANGE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }

        else if (currentPlayer == 4 && n.getColor() == Color.RED) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }
        else if (currentPlayer == 5 && n.getColor() == Color.BLACK) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }

        else if (currentPlayer == 6 && n.getColor() == Color.GREEN) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }

    }

    public void removehighlight(Node n, Node m) {
        ArrayList<Node> tr = board.popularChoice(n);
        for (int i = 0; i < tr.size(); i++) {
            if (tr.get(i) == m) {

            } else {
                tr.get(i).setColor(Color.WHITE);
            }
        }

        ArrayList<Node> te = board.popularChoice(m);
        for (int i = 0; i < tr.size(); i++) {

            tr.get(i).setColor(Color.WHITE);

        }
    }

    public void removeOneHighlight(Node n){//method that removes the higlight when choosing another node
        ArrayList<Node> tr = board.popularChoice(n);
        for(int i=0; i<tr.size(); i++){
            tr.get(i).setColor(Color.WHITE);
        }
    }


    class MousePressListener implements MouseListener {
        MousePressListener() {
        }

        public void mousePressed(MouseEvent var1) {
        }

        public void mouseReleased(MouseEvent var1) {
        }

        public void mouseClicked(MouseEvent var1) {
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI6players.this.clickedForNode(var2, var3);
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
