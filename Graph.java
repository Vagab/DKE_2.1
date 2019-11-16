import org.w3c.dom.CDATASection;
import java.awt.*;
import java.util.ArrayList;

public class Graph {

    /*
     *   Creates a chinese checkers board as the form of a graph containing
     *
     */
    private static Node[] nodeList;
    public Color lightBlue = new Color(0,0,182,155);
    public int numPly; // Number of players

    private static Node
                            AA,
                          BA, BB,
                        CA, CB, CC,
                      DA, DB, DC, DD,
                    EA, EB, EC, ED, EE,
                  FA, FB, FC, FD, FE, FF,
                GA, GB, GC, GD, GE, GF, GG,
              HA, HB, HC, HD, HE, HF, HG, HH,
            IA, IB, IC, ID, IE, IF, IG, IH, II,
              JA, JB, JC, JD, JE, JF, JG, JH,
                KA, KB, KC, KD, KE, KF, KG,
                  LA, LB, LC, LD, LE, LF,
                    MA, MB, MC, MD, ME,
                      NA, NB, NC, ND,
                        OA, OB, OC,
                          PA, PB,
                            QA;

    public static void main(String args[]){

        makeNodes();
        makeNodeList();
        connectEdges();
        testNode(nodeList[50]); //test adjacent nodes
        testNodeColor(IE);      //test the color of a node by printing it
        testNode(CA);
    }

    public Graph(){
        this.numPly = 2;
        makeNodes();
        makeNodeList();
        connectEdges();
        initializeStartingPositions(2);
    }

    public static void testNode(Node node){
        /*
         *   Provides the adjacent nodes
         *   Useful to check connections
         */
        if(node.getLabel() == null){System.out.println("This is not a valid node.");}
        else{

            if(!node.getUpRight().getLabel().equals("null")){
                System.out.println("The upRight node of " + node.getLabel() + " is " + node.getUpRight().getLabel());
            }
            else{System.out.println("There is no upRight node for " + node.getLabel());}

            if(!node.getRight().getLabel().equals("null")){
                System.out.println("The Right node of " + node.getLabel() + " is " + node.getRight().getLabel());
            }
            else{System.out.println("There is no Right node for " + node.getLabel());}

            if(!node.getdRight().getLabel().equals("null")){
                System.out.println("The dRight node of " + node.getLabel() + " is " + node.getdRight().getLabel());
            }
            else{System.out.println("There is no dRight node for " + node.getLabel());}

            if(!node.getdLeft().getLabel().equals("null")){
                System.out.println("The dLeft node of " + node.getLabel() + " is " + node.getdLeft().getLabel());
            }
            else{System.out.println("There is no dLeft node for " + node.getLabel());}

            if(!node.getLeft().getLabel().equals("null")){
                System.out.println("The Left node of " + node.getLabel() + " is " + node.getLeft().getLabel());
            }
            else{System.out.println("There is no Left node for " + node.getLabel());}

            if(!node.getUpLeft().getLabel().equals("null")){
                System.out.println("The UpLeft node of " + node.getLabel() + " is " + node.getUpLeft().getLabel());
            }
            else{System.out.println("There is no UpLeft node for " + node.getLabel());}

        }

    }

    public static void testNodeColor(Node node){

        if(node.getColor().equals(Color.BLUE)){System.out.println("The color of the node " + node.getLabel() + " is BLUE");}
        else if(node.getColor().equals(Color.RED)){System.out.println("The color of the node " + node.getLabel() + " is RED");}
        else{System.out.println("The color of the node " + node.getLabel() + " is WHITE");}


    }

    private static void makeNodes(){

        AA = new Node("AA",0,0);

        BA = new Node("BA",1,0);
        BB = new Node("BB",1,1);

        CA = new Node("CA",2,0);
        CB = new Node("CB",2,1);
        CC = new Node("CC",2,2);

        DA = new Node("DA",3,0);
        DB = new Node("DB",3,1);
        DC = new Node("DC",3,2);
        DD = new Node("DD",3,3);

        EA = new Node("EA",4,0);
        EB = new Node("EB",4,1);
        EC = new Node("EC",4,2);
        ED = new Node("ED",4,3);
        EE = new Node("EE",4,4);

        FA = new Node("FA",5,0);
        FB = new Node("FB",5,1);
        FC = new Node("FC",5,2);
        FD = new Node("FD",5,3);
        FE = new Node("FE",5,4);
        FF = new Node("FF",5,5);

        GA = new Node("GA",6,0);
        GB = new Node("GB",6,1);
        GC = new Node("GC",6,2);
        GD = new Node("GD",6,3);
        GE = new Node("GE",6,4);
        GF = new Node("GF",6,5);
        GG = new Node("GG",6,6);

        HA = new Node("HA",7,0);
        HB = new Node("HB",7,1);
        HC = new Node("HC",7,2);
        HD = new Node("HD",7,3);
        HE = new Node("HE",7,4);
        HF = new Node("HF",7,5);
        HG = new Node("HG",7,6);
        HH = new Node("HH",7,7);

        IA = new Node("IA",8,0);
        IB = new Node("IB",8,1);
        IC = new Node("IC",8,2);
        ID = new Node("ID",8,3);
        IE = new Node("IE",8,4);
        IF = new Node("IF",8,5);
        IG = new Node("IG",8,6);
        IH = new Node("IH",8,7);
        II = new Node("II",8,8);

        JA = new Node("JA",9,0);
        JB = new Node("JB",9,1);
        JC = new Node("JC",9,2);
        JD = new Node("JD",9,3);
        JE = new Node("JE",9,4);
        JF = new Node("JF",9,5);
        JG = new Node("JG",9,6);
        JH = new Node("JH",9,7);

        KA = new Node("KA",10,0);
        KB = new Node("KB",10,1);
        KC = new Node("KC",10,2);
        KD = new Node("KD",10,3);
        KE = new Node("KE",10,4);
        KF = new Node("KF",10,5);
        KG = new Node("KG",10,6);

        LA = new Node("LA",11,0);
        LB = new Node("LB",11,1);
        LC = new Node("LC",11,2);
        LD = new Node("LD",11,3);
        LE = new Node("LE",11,4);
        LF = new Node("LF",11,5);

        MA = new Node("MA",12,0);
        MB = new Node("MB",12,1);
        MC = new Node("MC",12,2);
        MD = new Node("MD",12,3);
        ME = new Node("ME",12,4);

        NA = new Node("NA",13,0);
        NB = new Node("NB",13,1);
        NC = new Node("NB",13,2);
        ND = new Node("NB",13,3);

        OA = new Node("OA",14,0);
        OB = new Node("OB",14,1);
        OC = new Node("OC",14,2);

        PA = new Node("PA",15,0);
        PB = new Node("PB",15,1);

        QA = new Node("QA",16,0);
    }

    private static void makeNodeList(){

        nodeList = new Node[]{AA,
                BA, BB,
                CA, CB, CC,
                DA, DB, DC, DD,
                EA, EB, EC, ED, EE,
                FA, FB, FC, FD, FE, FF,
                GA, GB, GC, GD, GE, GF, GG,
                HA, HB, HC, HD, HE, HF, HG, HH,
                IA, IB, IC, ID, IE, IF, IG, IH, II,
                JA, JB, JC, JD, JE, JF, JG, JH,
                KA, KB, KC, KD, KE, KF, KG,
                LA, LB, LC, LD, LE, LF,
                MA, MB, MC, MD, ME,
                NA, NB, NC, ND,
                OA, OB, OC,
                PA, PB,
                QA};



    }

    private static void connectEdges(){
        /*
         *   Method to connect the graph
         */
        for(int i=0; i<nodeList.length; i++){
            for(int j=0; j<nodeList.length; j++){
                nodeList[i].addEdge(nodeList[j]);
            }
        }
    }

    private static void initializeStartingPositions(int n){
        if(n==2){
            for(int i=0; i<6; i++){
                nodeList[i].setColor(Color.BLUE);
                nodeList[80-i].setColor(Color.RED);
            }
        }
        else if(n==6){
            //PLAYER 1 AND 4
            for(int i = 0; i < 10; i++){
                nodeList[i].setColor(Color.BLUE);
                nodeList[120-i].setColor(Color.RED);
            }

            //START PLAYER 2 and 6//
            for(int j=0;j<4;j++) {
                nodeList[10 + j].setColor(Color.GREEN);
                nodeList[19 + j].setColor(Color.GRAY);
            }

            for(int j=0;j<3;j++) {
                nodeList[23 + j].setColor(Color.GREEN);
                nodeList[32 + j].setColor(Color.GRAY);
            }

            for(int j=0;j<2;j++) {
                nodeList[35 + j].setColor(Color.GREEN);
                nodeList[44 + j].setColor(Color.GRAY);
            }

            nodeList[46].setColor(Color.GREEN);
            nodeList[55].setColor(Color.GRAY);
            //END PLAYER 2 and 6//

            //START PLAYER 3 and 5//
            for(int k=0; k < 4; k++) {
                nodeList[98 + k].setColor(Color.BLACK);
                nodeList[107 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 3; k++) {
                nodeList[86 + k].setColor(Color.BLACK);
                nodeList[95 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 2; k++) {
                nodeList[75 + k].setColor(Color.BLACK);
                nodeList[84 + k].setColor(Color.ORANGE);
            }

            nodeList[65].setColor(Color.BLACK);
            nodeList[74].setColor(Color.ORANGE);
        }
        //END PLAYER 3 and 5//
        else if(n==4){

            //START PLAYER 3 and 5//
            for(int k=0; k < 4; k++) {
                nodeList[98 + k].setColor(Color.BLACK);
                nodeList[107 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 3; k++) {
                nodeList[86 + k].setColor(Color.BLACK);
                nodeList[95 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 2; k++) {
                nodeList[75 + k].setColor(Color.BLACK);
                nodeList[84 + k].setColor(Color.ORANGE);
            }

            nodeList[65].setColor(Color.BLACK);
            nodeList[74].setColor(Color.ORANGE);
            //END PLAYER 3 and 5//

            //START PLAYER 2 and 6//
            for(int j=0;j<4;j++) {
                nodeList[10 + j].setColor(Color.GREEN);
                nodeList[19 + j].setColor(Color.GRAY);
            }

            for(int j=0;j<3;j++) {
                nodeList[23 + j].setColor(Color.GREEN);
                nodeList[32 + j].setColor(Color.GRAY);
            }

            for(int j=0;j<2;j++) {
                nodeList[35 + j].setColor(Color.GREEN);
                nodeList[44 + j].setColor(Color.GRAY);
            }

            nodeList[46].setColor(Color.GREEN);
            nodeList[55].setColor(Color.GRAY);
            //END PLAYER 2 and 6//

        }
        else{
            //PLAYER 1
            for(int i = 0; i < 10; i++){
                nodeList[i].setColor(Color.BLUE);
            }
            for(int k=0; k < 4; k++) {
                nodeList[98 + k].setColor(Color.BLACK);
                nodeList[107 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 3; k++) {
                nodeList[86 + k].setColor(Color.BLACK);
                nodeList[95 + k].setColor(Color.ORANGE);
            }

            for(int k=0; k < 2; k++) {
                nodeList[75 + k].setColor(Color.BLACK);
                nodeList[84 + k].setColor(Color.ORANGE);
            }

            nodeList[65].setColor(Color.BLACK);
            nodeList[74].setColor(Color.ORANGE);
        }
    }


    public String getNodeLabel(int node){
        return nodeList[node].getLabel();
    }
    public Node getSecNode(int node){
        return nodeList[node];
    }
    public Color getNodeColor(int node){
        return nodeList[node].getColor();
    }
    public int getNodeXCoords(int node){
        return nodeList[node].getX();
    }
    public int getNodeYCoords(int node){
        return nodeList[node].getY();
    }
    public Node[] getNodes(){
        return this.nodeList;
    }

    //Oscar

    public static ArrayList<Node> oG = new ArrayList<Node>();

    public static ArrayList<Node> popularChoice(Node n){
        oG.clear();
        Node[] availableNodes = n.adjN(); //Gets adjacent nodes from node n
        for(int i = 0; i < availableNodes.length; i++){ // Goes through all neighbour nodes of n
            choose(availableNodes[i], i);
        }
        return oG;
    }

    public static void choose(Node n, int i){
        Node[] av = n.adjN();
        if(!n.isOccupied()){ //if node n is not occupied, it is added to the list of available spots
            oG.add(n);
        }
        else if(!av[i].isOccupied()){
            oG.add(av[i]); //if node n is occupied, we check whether the node which lies in the i-direction is available
            isItGucci(av[i], i);
        }

    }

    public static void isItGucci(Node n, int m){
        Node[] y = n.adjN(); //gets neighbours of node n
        for(int i=0; i<y.length; i++){
            if(y[i].getLabel()==null){
                //Do nothing
            }
            if(y[i].isOccupied() && (i+m)!=5){ //The (i+m)!=5 statement is to make sure that the piece doesn't jump back to its original position
                if(y[i].getAdj(i).getLabel()==null){
                    //Do nothing
                }
                if(!y[i].getAdj(i).isOccupied()){ //
                    if(!oG.contains(y[i].getAdj(i))) {
                        oG.add(y[i].getAdj(i));
                        isItGucci(y[i].getAdj(i), i);
                    }
                }
            }
        }
    }

    public static void display(){
        for (int i=0; i<oG.size();i++ ) {
            System.out.println("possible move "+oG.get(i).getLabel());
        }
    }

}