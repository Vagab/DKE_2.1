import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class MCMC {

    //Jel
    int interval = 45;
    int diameter = 40;


    private Graph board;
    private ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();
    private Node[] nodeList;
    private int selectedNode, previousSelectedNode = -1;
    private boolean firstMove = false;
    private long start = System.nanoTime();
    private int currentPlayer = 1;
    private int turnsCount = 1;
    int nrsP;

    private Node lastAIPlayed;
    private Node previousAIPlayed;

    private boolean player1 = true;
    private Random rand;
    private LobbySimulator simulator;
    private int bestPreviousScore = -1;

    public MCMC(Graph board) {

        this.board = board;
        this.nodeList = board.getNodes();

       // MousePressListener var1 = new MousePressListener();
       // this.addMouseListener(var1);

        lastAIPlayed = new Node("null", -1,-1,-1);
        previousAIPlayed = new Node("null",-1,-1,-1);


        rand = new Random(); //for random element in AIChoosesNode
        //AIChoosesNode();

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



    public int[] AIChoosesNode(){

        System.out.println("______PLAY_______");

        for(int i=0; i<board.getBoardSize(); i++){  //tries to make a move
            if(player1 && board.getNodeColor(i).equals(Color.BLUE)){

                int depthSimulation;
                if(this.turnsCount<=20){depthSimulation = 10;}
                else{depthSimulation = 4;}

                simulator = new LobbySimulator(this.getBluePositions(), this.getRedPositions(), depthSimulation, bestPreviousScore);
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


                ArrayList<Node> popChoices = board.popularChoice(this.board.getSecNode(iNode)); //gets popular choices of i



                Node[] possibleChoice = new Node[popChoices.size()];

                int index = 0;
                for(int j=0; j<popChoices.size(); j++){ //convert ArrayList<Node> to Node[]
                    if(popChoices.get(j).getLabel().equals("null")
                            //forbids to make moves up
                            || popChoices.get(j).getdRight().getLabel().equals(this.board.getSecNode(iNode).getLabel())
                            || popChoices.get(j).getdLeft().getLabel().equals(this.board.getSecNode(iNode).getLabel())) {
                    }
                    else {
                        possibleChoice[index] = popChoices.get(j);
                        index++;
                    }
                }

                Node[] finalPossibleChoice = new Node[index];//copies array with correct size
                for(int k=0; k<index; k++){finalPossibleChoice[k]=possibleChoice[k];}


                if(index>0){//display elements/
                   // for(int k=0;k<finalPossibleChoice.length;k++){System.out.println(finalPossibleChoice[k].getLabel());}


                    int n;
                    Node max = new Node("max", -1, -1,-1);

                    for(int ind=0; ind<finalPossibleChoice.length;ind++){
                        if(finalPossibleChoice[ind].getScore()>max.getScore()){
                            max = finalPossibleChoice[ind];
                        }
                    }



                    // setMove(finalPossibleChoice[n].getIndex());//sets the next move
                    if(this.board.getSecNode(iNode).getScore()!=max.getScore()) {
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
            else if(!this.player1){break;}   //if not its turn anymore, break
        }

        int[] output = new int[]{this.previousSelectedNode, this.selectedNode};
        return output;
    }

    public void setMove(int chosenNode) {
        higlight(this.board.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        //System.out.println(previousSelectedNode + " " + selectedNode);

        ArrayList<Node> stopBugs = this.board.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;    //gets popular choices ?
        for(int i=0; i<stopBugs.size(); i++){
            if(stopBugs.get(i)==this.nodeList[this.selectedNode]){
                allgood = true;
            }
        }
        if(allgood==false){ //removes a bug?
            removeOneHighlight(this.nodeList[previousSelectedNode]);
        }

        higlight(this.board.getSecNode(chosenNode));

        if (this.previousSelectedNode == this.selectedNode) { //removes highlight from chosen node
            removehighlight(this.nodeList[this.previousSelectedNode], this.nodeList[this.selectedNode]);
        }

        //makes the move for either player
        if (((this.nodeList[this.previousSelectedNode].getColor().equals(Color.BLUE) && this.player1)
                || (this.nodeList[this.previousSelectedNode].getColor().equals(Color.RED) && !this.player1)

        )) {
            //System.out.println("Ok");
            this.firstMove = false;


            //condition for moving a pawn only to yellow nodes
            if (this.selectedNode != this.previousSelectedNode && !this.nodeList[this.previousSelectedNode].getColor().equals(Color.YELLOW)
                    && this.nodeList[this.selectedNode].getColor().equals(Color.YELLOW)
            ) {
                //changes colors
               // System.out.println("Ok2");
                this.nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                this.nodeList[previousSelectedNode].setColor(Color.WHITE);
                if (isWinningCondition()) { // /!\
                    if (this.player1) {
                       // JOptionPane.showMessageDialog(this, currentPlayer + " has won!" "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else {
                      //  JOptionPane.showMessageDialog(this, currentPlayer + " has won!", "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
                    }
                }

                if(this.player1){
                    this.player1=false;
                    System.out.println("AI PLAYER 2");
                }
                else{
                    this.player1 = true;
                    System.out.println("AI PLAYER 1");
                }

                this.turnsCount++;
            }
            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        //repaint();
       // System.out.println("MCMC PLAYER 1 IS " + this.player1);
        //System.out.println("MCMC FIRST MOVE IS " + this.firstMove);

        return;
    }

    public void higlight(Node n) {
        if (this.player1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = this.board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        } else if (!this.player1 && n.getColor() == Color.RED) {
            ArrayList<Node> tr = this.board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }


    }

    public void removehighlight(Node n, Node m) {
        ArrayList<Node> tr = this.board.popularChoice(n);
        for (int i = 0; i < tr.size(); i++) {
            if (tr.get(i) == m) {

            } else {
                tr.get(i).setColor(Color.WHITE);
            }
        }

        ArrayList<Node> te = this.board.popularChoice(m);
        for (int i = 0; i < tr.size(); i++) {

            tr.get(i).setColor(Color.WHITE);

        }
    }

    public void removeOneHighlight(Node n){//method that removes the highlight when choosing another node
        ArrayList<Node> tr = this.board.popularChoice(n);
        for(int i=0; i<tr.size(); i++){
            tr.get(i).setColor(Color.WHITE);
        }
    }

    public int[] getBluePositions(){
        int[] bluePos = new int[6]; //amount of blue pawns
        int iter = 0;
        for(int i=0; i<this.nodeList.length; i++){
            if(this.board.getNodeColor(i).equals(Color.BLUE)){
                bluePos[iter] = this.board.getSecNode(i).getIndex();
                System.out.print(this.board.getNodeLabel(bluePos[iter]) + " ");
                iter++;
            }
        }
        return bluePos;
    }
    public int[] getRedPositions(){
        int[] redPos = new int[6]; //amount of blue pawns
        int iter = 0;
        for(int i=0; i<this.nodeList.length; i++){
            if(this.board.getNodeColor(i).equals(Color.RED)){
                redPos[iter] = this.board.getSecNode(i).getIndex();
                System.out.print(this.board.getNodeLabel(redPos[iter]) + " ");
                iter++;
            }
        }
        return redPos;
    }

    public void setGraph(Graph board){
        System.out.println("Setting graph into MCMC");
        this.board = board;
    }

    public void setTurn(boolean player1){
        this.player1 = player1;
    }
    public boolean getTurn(){return this.player1;}

    public void setFirstMove(boolean firstMove){this.firstMove = firstMove;}
    public boolean getFirstMove(){return this.firstMove;}

    public Node[] getNodeList(){return this.nodeList;}


}
