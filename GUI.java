import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class GUI extends JComponent {

    //Jel
    int interval = 45;
    int diameter = 40;


    Graph board;
    ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();
    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public static boolean firstMove = false;
    static long start = System.nanoTime();
    int currentPlayer = 1;
    int turnsCount = 1;
    int nrsP;

    boolean player1 = true;

    public GUI() {

        this.board = new Graph();
        this.nodeList = board.getNodes();

        MousePressListener var1 = new MousePressListener();
        this.addMouseListener(var1);

        AIChoosesNode();

    }


    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        if (timeElapsed % 1000000000 == 0)
            repaint();

        GradientPaint gp1 = new GradientPaint(50, 1, Color.blue, 20, 20, Color.lightGray, true);
        GradientPaint gp2 = new GradientPaint(100, 100, Color.green, 5, 5, Color.lightGray, true);
        GradientPaint gp3 = new GradientPaint(45, 45, Color.yellow, 70, 70, Color.orange, true);
        GradientPaint gp4 = new GradientPaint(5, 5, Color.darkGray, 20, 20, Color.red, true);


        if (player1) {
            g2.setPaint(Color.BLUE);
            g2.drawString("It is Player's Blue turn", 1000, 100);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("It is Player's Red turn", 1000, 100);
        }


        g2.setPaint((Color.BLACK));
        g2.drawString("Turns count: " + turnsCount, 1000, 120);

        g2.setPaint((Color.BLACK));
        g2.drawString("Time elapsed: " + timeElapsed / 1000000000 + " seconds", 1000, 140);


        g2.setPaint(Color.WHITE);

        for(int i=0; i<nodeList.length; i++){
            g2.setPaint(board.getNodeColor(i));

            if(i<=44) {
                g2.fillOval(board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30, board.getNodeXCoords(i) * 40 + 100, 40, 40);
                g2.setPaint(Color.ORANGE);
                g2.drawString(board.getNodeLabel(i),15 + board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30,20 + board.getNodeXCoords(i) * 40 + 100);
            }
            else {
                g2.fillOval(board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30, board.getNodeXCoords(i) * 40 + 100, 40, 40);
                g2.setPaint(Color.GREEN);
                g2.drawString(board.getNodeLabel(i), 15 + board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30, 20 + board.getNodeXCoords(i) * 40 + 100);
            }
        }

        if(player1 && !firstMove) AIChoosesNode();

    }

    public boolean isWinningCondition() {


        if (player1) {       //checking for blue
            for (int i = 80; i > 71; i--) {
                if (!board.getNodeColor(i).equals(Color.BLUE))
                    return false;
            }
            return true;
        }





        else { //checking for red
            for (int i = 0; i <= 9; i++) {
                if (!board.getNodeColor(i).equals(Color.RED))
                    return false;
            }
            return true;
        }





    }


    public void clickedForNode(int var1, int var2) {    //player choosing node

        for(int i=0; i<81; i++){

            if(i<=44){
                if(var1>=board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30
                        && var1<=board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30 + 40
                        && var2>=board.getNodeXCoords(i) * 40 + 100
                        && var2<= board.getNodeXCoords(i) * 40 + 100 +40 ){
                    setMove(i);
                }
            }
            else{
                if(var1>=board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30
                        && var1<= board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30 + 40
                        && var2>= board.getNodeXCoords(i) * 40 + 100
                        && var2<= board.getNodeXCoords(i) * 40 + 100 + 40){
                    setMove(i);
                }
            }


        }

        System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    public void AIChoosesNode(){
        Random rand = new Random();

        for(int i=0; i<board.getBoardSize(); i++){  //tries to make a move
            if(player1 && board.getNodeColor(i).equals(Color.BLUE)){
                setMove(i);


                ArrayList<Node> popChoices = board.popularAIChoice(board.getSecNode(i)); //gets popular choices of i
                Object[] objChoices = popChoices.toArray();
                Node[] possibleChoices = new Node[objChoices.length];
                for (int j = 0; j < objChoices.length; j++) {
                    possibleChoices[j] = (Node) objChoices[j];
                }
               setMove(possibleChoices[rand.nextInt(3)].getIndex());
//               setMove(1);
            }
            else if(!player1){break;}   //if not its turn anymore, break
        }

    }

    public void setMove(int chosenNode) {
        higlight(board.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        ArrayList<Node> stopBugs = board.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;    //gets popular choices ?
        for(int i=0; i<stopBugs.size(); i++){
            if(stopBugs.get(i)==nodeList[selectedNode]){
                allgood = true;
            }
        }
        if(allgood==false){ //removes a bug?
            removeOneHighlight(nodeList[previousSelectedNode]);
        }

        higlight(board.getSecNode(chosenNode));

        if (previousSelectedNode == selectedNode) { //removes highlight from chosen node
            removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }

        //makes the move for either player
        if (((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && player1)
                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && !player1)

        )) {
            this.firstMove = false;


            //condition for moving a pawn only to yellow nodes
            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {
                //changes colors
                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                if (isWinningCondition()) { // /!\
                    if (player1) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
                    }
                }

                if(player1){player1=false;}
                else{player1 = true;}

                turnsCount++;
            }
            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        repaint();

    }

    public void higlight(Node n) {
        if (player1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        } else if (!player1 && n.getColor() == Color.RED) {
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

    public void removeOneHighlight(Node n){//method that removes the highlight when choosing another node
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
            GUI.this.clickedForNode(var2, var3);
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
