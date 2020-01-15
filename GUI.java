import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Random;



public class GUI extends JComponent {

    //Jel
    //Jel
    private int diameter = 30;
    private double interval = diameter*2.0/Math.sqrt(3);
    private double offset = diameter/2.0;
    private double xOffset = 700;
    private double yOffset = 100;


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
    private int bestPreviousScore = -1;

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

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

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


        double yTop = board.getNodeXCoords(0) * Math.sqrt(3)/2.0 * interval + yOffset - offset;
        double yRef = board.getNodeXCoords(120) * Math.sqrt(3)/2.0 * interval + yOffset + diameter + offset;
        double diameterCircle = yRef - yTop;
        double xRef = board.getNodeYCoords(0) * interval + xOffset - board.getNodeXCoords(0) * interval/2.0 + diameter/2.0;
        double xLeft = xRef - diameterCircle/2.0;

        Ellipse2D.Double bigCircle = new Ellipse2D.Double(xLeft, yTop, diameterCircle, diameterCircle);
        g2.setPaint(Color.pink);
        g2.fill(bigCircle);
        g2.setPaint(new Color(255,102,102));
        g2.fill(triangle(board.getSecNode(6),0));
        g2.setPaint(new Color(204,204,204));
        g2.fill(triangle(board.getSecNode(98),0));
        g2.setPaint(new Color(102,255,102));
        g2.fill(triangle(board.getSecNode(107),0));
        g2.setPaint(new Color(255,153,0));
        g2.fill(triangle(board.getSecNode(10),1));
        g2.setPaint(new Color(51,51,51));
        g2.fill(triangle(board.getSecNode(19),1));
        g2.setPaint(new Color(51,204,255));
        g2.fill(triangle(board.getSecNode(111),1));


        g2.setPaint((Color.BLACK));
        g2.drawString("Turns count: " + turnsCount, 1000, 120);

        g2.setPaint((Color.BLACK));
        g2.drawString("Time elapsed: " + timeElapsed / 1000000000 + " seconds", 1000, 140);


        g2.setPaint(Color.WHITE);

        /*for(int i=0; i<nodeList.length; i++){
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
        }*/
        for (int i = 0; i < nodeList.length; i++) {
            g2.setPaint(board.getNodeColor(i));
            Ellipse2D.Double circle = new Ellipse2D.Double(board.getNodeYCoords(i) * interval + xOffset - board.getNodeXCoords(i) * interval/2.0, board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + yOffset, diameter, diameter);
            g2.fill(circle);
            if (!board.getNodeColor(i).equals(Color.WHITE)) {
                g2.setPaint(Color.WHITE);
                g2.draw(new Ellipse2D.Double(board.getNodeYCoords(i) * interval + xOffset - board.getNodeXCoords(i) * interval/2.0, board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + yOffset, diameter, diameter));
            }
//            g2.setPaint(Color.ORANGE);
//            g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * interval + 715 - board.getNodeXCoords(i) * interval/2, (int) (board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 120));
        }



        if(player1 && !firstMove){ AIChoosesNode();}

    }

    private Path2D triangle(Node node,int a) {
        double startX = node.getY() * interval + xOffset - node.getX() * interval/2.0 + diameter/2.0*(1-1/Math.tan(30/180.0*Math.PI));
        double startY = node.getX()  * Math.sqrt(3)/2.0 * interval + yOffset + diameter;
        double deltaX = 3*interval/2.0 + diameter/2.0*1/Math.tan(30/180.0*Math.PI);
        double deltaY = 4.5*diameter;
        double[] xUp = {startX,startX + deltaX, startX + 2*deltaX};
        double[] yUp = {startY,startY-deltaY,startY};
        double[] xDown = {startX, startX + 2*deltaX,startX + deltaX};
        double[] yDown = {startY-diameter, startY-diameter,startY-diameter+deltaY};

        Path2D path = new Path2D.Double();

        if (a == 0) { //a == 0 for the upward pointing triangles
            path.moveTo(xUp[0], yUp[0]);
            for(int i = 1; i < xUp.length; ++i) {
                path.lineTo(xUp[i], yUp[i]);
            }
            path.closePath();
        }
        else if (a == 1) {
            path.moveTo(xDown[0], yDown[0]);
            for(int i = 1; i < xDown.length; ++i) {
                path.lineTo(xDown[i], yDown[i]);
            }
            path.closePath();
        }
        return path;
    }

    private boolean isWinningCondition() {


        if (player1) {       //checking for blue
            for (int i = 120; i > 110; i--) {
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

        if(!player1) {
            /*for (int i = 0; i < 120; i++) {

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


            }*/

            for(int i=0; i<121; i++){
                if (var1 >= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2
                        && var1 <= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2 + diameter
                        && var2 >= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100
                        && var2 <= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100 + diameter) {
                    setMove(i);
                }
            }

        }
        System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    private void AIChoosesNode(){

        for(int i=0; i<board.getBoardSize(); i++){  //tries to make a move
            if(player1 && board.getNodeColor(i).equals(Color.BLUE)){

                int depthSimulation;
                if(this.turnsCount<=20){depthSimulation = 20;}
                else if(this.turnsCount>20 && this.turnsCount<=50){depthSimulation = 16;}
                else{depthSimulation = 10;}

                simulator = new LobbySimulator(this.getBluePositions(), this.getRedPositions(), depthSimulation, this.bestPreviousScore);
                simulator.launch();

                boolean done = false;
                System.out.println("Launching Simulations...");
                while(!simulator.isDone()){}
                System.out.println("Finished!");

                int iNode = simulator.getBestCandidate();
                int destinationNode = simulator.getBestDestination();
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
                        //setMove(max.getIndex());
                        setMove(destinationNode);
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
        int[] bluePos = new int[10]; //amount of blue pawns
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
        int[] redPos = new int[10]; //amount of blue pawns
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
