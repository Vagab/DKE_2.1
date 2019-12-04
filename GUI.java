import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class GUI extends JComponent {

    //Jel
    int interval = 45;
    int diameter = 40;


    private Graph board;
    private ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();
    private Node[] nodeList;
    private int selectedNode, previousSelectedNode = -1;
    private static boolean firstMove = false;
    private static long start = System.nanoTime();
    private int currentPlayer = 1;
    private int turnsCount = 1;
    int nrsP;

    private Node lastAIPlayed;
    private Node previousAIPlayed;

    private boolean player1 = true;
    private Random rand;
    private LobbySimulator simulator;

    public GUI() {

        this.board = new Graph();
        this.nodeList = board.getNodes();

        MousePressListener var1 = new MousePressListener();
        this.addMouseListener(var1);

        lastAIPlayed = new Node("null", -1,-1,-1);
        previousAIPlayed = new Node("null",-1,-1,-1);


        rand = new Random(); //for random element in AIChoosesNode
        AIChoosesNode();

    }


    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        if (timeElapsed % 1000000000 == 0)
            repaint();


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

       if(player1 && !firstMove){ AIChoosesNode();}

    }

    private boolean isWinningCondition() {


        if (player1) {       //checking for blue
            for (int i = 80; i > 75; i--) {
                if (!board.getNodeColor(i).equals(Color.BLUE))
                    return false;
            }
            return true;
        }





        else { //checking for red
            for (int i = 0; i < 6; i++) {
                if (!board.getNodeColor(i).equals(Color.RED))
                    return false;
            }
            return true;
        }





    }


    public void clickedForNode(int var1, int var2) {    //player choosing node

    if(!player1) {
        for (int i = 0; i < 81; i++) {

            if (i <= 44) {
                if (var1 >= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 100
                        && var2 <= board.getNodeXCoords(i) * 40 + 100 + 40) {
                    setMove(i);
                }
            } else {
                if (var1 >= board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30
                        && var1 <= board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30 + 40
                        && var2 >= board.getNodeXCoords(i) * 40 + 100
                        && var2 <= board.getNodeXCoords(i) * 40 + 100 + 40) {
                    setMove(i);
                }
            }


        }
    }
        System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    private void AIChoosesNode(){

        for(int i=0; i<board.getBoardSize(); i++){  //tries to make a move
            if(player1 && board.getNodeColor(i).equals(Color.BLUE)){

                simulator = new LobbySimulator(this.getBluePositions(), this.getRedPositions());
                simulator.launch();

                boolean done = false;
                System.out.println("Launching Simulations...");
                while(!simulator.isDone()){}
                System.out.println("Finished!");

                int iNode = simulator.getBestCandidate();
                //int iNode = i;
               // if(board.getNodeColor(iNode).equals(Color.BLUE)){System.out.println("good");}
                //System.out.println("Simulator returns " + board.getSecNode(simulator.getBestCandidate()).getLabel());
                //System.out.println("AI Chooses " + board.getSecNode(iNode).getLabel());


               // previousSelectedNode = iNode;
                setMove(iNode);


                ArrayList<Node> popChoices = board.popularChoice(board.getSecNode(iNode)); //gets popular choices of i



                Node[] possibleChoice = new Node[popChoices.size()];

                int index = 0;
                for(int j=0; j<popChoices.size(); j++){ //convert ArrayList<Node> to Node[]
                    if(popChoices.get(j).getLabel().equals("null")
                            //forbids to make moves up
                            || popChoices.get(j).getdRight().getLabel().equals(board.getSecNode(iNode).getLabel())
                            || popChoices.get(j).getdLeft().getLabel().equals(board.getSecNode(iNode).getLabel())) {
                      }
                    else {
                        possibleChoice[index] = popChoices.get(j);
                        index++;
                    }
                }

                Node[] finalPossibleChoice = new Node[index];//copies array with correct size
                for(int k=0; k<index; k++){finalPossibleChoice[k]=possibleChoice[k];}


                if(index>0){//display elements/
                    for(int k=0;k<finalPossibleChoice.length;k++){System.out.println(finalPossibleChoice[k].getLabel());}


                    int n;
                    Node max = new Node("max", -1, -1,-1);

                    for(int ind=0; ind<finalPossibleChoice.length;ind++){
                        if(finalPossibleChoice[ind].getScore()>max.getScore()){
                            max = finalPossibleChoice[ind];
                        }
                    }



                    // setMove(finalPossibleChoice[n].getIndex());//sets the next move
                    if(board.getSecNode(iNode).getScore()!=max.getScore()) {
                        System.out.println("AI move is " + max.getLabel() + " " + max.getIndex());
                        //previousSelectedNode = max.getIndex();
                        setMove(max.getIndex());
                        break;
                    }
                    else{
                        System.out.println("bug");
                        setMove(iNode);
                    }
                }

            }
            else if(!player1){break;}   //if not its turn anymore, break
        }

    }

    private void setMove(int chosenNode) {
        higlight(board.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        System.out.println(previousSelectedNode + " " + selectedNode);

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
            System.out.println("Ok");
            this.firstMove = false;


            //condition for moving a pawn only to yellow nodes
            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {
                //changes colors
                System.out.println("Ok2");
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

                if(player1){
                    player1=false;
                    System.out.println("PLAYER 2");
                }
                else{
                    player1 = true;
                    System.out.println("PLAYER 1");
                }

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

    public int[] getBluePositions(){
        int[] bluePos = new int[6]; //amount of blue pawns
        int iter = 0;
        for(int i=0; i<nodeList.length; i++){
            if(board.getNodeColor(i).equals(Color.BLUE)){
                bluePos[iter] = board.getSecNode(i).getIndex();
                System.out.print(board.getNodeLabel(bluePos[iter]) + " ");
                iter++;
            }
        }
        return bluePos;
    }
    public int[] getRedPositions(){
        int[] redPos = new int[6]; //amount of blue pawns
        int iter = 0;
        for(int i=0; i<nodeList.length; i++){
            if(board.getNodeColor(i).equals(Color.RED)){
                redPos[iter] = board.getSecNode(i).getIndex();
                System.out.print(board.getNodeLabel(redPos[iter]) + " ");
                iter++;
            }
        }
        return redPos;
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
