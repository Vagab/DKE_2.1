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
        //testNode(nodeList[50]); //test adjacent nodes
        //testNodeColor(IE);      //test the color of a node by printing it
        //testNode(CA);
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

        AA = new Node("AA",0,0, 0);

        BA = new Node("BA",1,0, 1);
        BB = new Node("BB",1,1, 1);

        CA = new Node("CA",2,0,2);
        CB = new Node("CB",2,1,3);
        CC = new Node("CC",2,2,2);

        DA = new Node("DA",3,0,3);
        DB = new Node("DB",3,1,4);
        DC = new Node("DC",3,2,4);
        DD = new Node("DD",3,3,3);

        EA = new Node("EA",4,0,3);
        EB = new Node("EB",4,1,4);
        EC = new Node("EC",4,2,5);
        ED = new Node("ED",4,3,4);
        EE = new Node("EE",4,4,3);

        FA = new Node("FA",5,0,3);
        FB = new Node("FB",5,1,4);
        FC = new Node("FC",5,2,6);
        FD = new Node("FD",5,3,7);
        FE = new Node("FE",5,4,4);
        FF = new Node("FF",5,5,3);

        GA = new Node("GA",6,0,3);
        GB = new Node("GB",6,1,6);
        GC = new Node("GC",6,2,7);
        GD = new Node("GD",6,3,8);
        GE = new Node("GE",6,4,7);
        GF = new Node("GF",6,5,6);
        GG = new Node("GG",6,6,3);

        HA = new Node("HA",7,0,2);
        HB = new Node("HB",7,1,4);
        HC = new Node("HC",7,2,8);
        HD = new Node("HD",7,3,9);
        HE = new Node("HE",7,4,9);
        HF = new Node("HF",7,5,8);
        HG = new Node("HG",7,6,4);
        HH = new Node("HH",7,7,2);

        IA = new Node("IA",8,0,1);
        IB = new Node("IB",8,1,5);
        IC = new Node("IC",8,2,9);
        ID = new Node("ID",8,3,10);
        IE = new Node("IE",8,4,11);
        IF = new Node("IF",8,5,10);
        IG = new Node("IG",8,6,9);
        IH = new Node("IH",8,7,5);
        II = new Node("II",8,8,1);

        JA = new Node("JA",9,0,6);
        JB = new Node("JB",9,1,8);
        JC = new Node("JC",9,2,10);
        JD = new Node("JD",9,3,12);
        JE = new Node("JE",9,4,12);
        JF = new Node("JF",9,5,10);
        JG = new Node("JG",9,6,8);
        JH = new Node("JH",9,7,6);

        KA = new Node("KA",10,0,7);
        KB = new Node("KB",10,1,9);
        KC = new Node("KC",10,2,13);
        KD = new Node("KD",10,3,14);
        KE = new Node("KE",10,4,13);
        KF = new Node("KF",10,5,9);
        KG = new Node("KG",10,6,7);

        LA = new Node("LA",11,0,8);
        LB = new Node("LB",11,1,14);
        LC = new Node("LC",11,2,15);
        LD = new Node("LD",11,3,15);
        LE = new Node("LE",11,4,14);
        LF = new Node("LF",11,5,8);

        MA = new Node("MA",12,0,15);
        MB = new Node("MB",12,1,17);
        MC = new Node("MC",12,2,18);
        MD = new Node("MD",12,3,17);
        ME = new Node("ME",12,4,15);

        NA = new Node("NA",13,0,18);
        NB = new Node("NB",13,1,20);
        NC = new Node("NB",13,2,20);
        ND = new Node("NB",13,3,18);

        OA = new Node("OA",14,0,21);
        OB = new Node("OB",14,1,25);
        OC = new Node("OC",14,2,21);

        PA = new Node("PA",15,0,25);
        PB = new Node("PB",15,1,25);

        QA = new Node("QA",16,0,22);
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

        for(int i=0; i<nodeList.length;i++){
            nodeList[i].setIndex(i);
        }

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

    }

    public static void initializePositions(int[] blue, int[] red){
        for(int i=0; i<nodeList.length;i++){    //reset the board, don't use please it's dangerous
            nodeList[i].setColor(Color.WHITE);
        }

        for(int i=0;i<blue.length;i++){ //sets the positions
            nodeList[blue[i]].setColor(Color.BLUE);
            nodeList[red[i]].setColor(Color.RED);
        }
    }


    public int getBoardSize(){return nodeList.length;}

    public int getNodeIndex(int i){return nodeList[i].getIndex();}

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