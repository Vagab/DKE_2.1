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
                           /* AA,
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
                            QA;*/

            AA,
            BA, BB,
            CA, CB, CC,
            DA, DB, DC, DD,
            E4, E3, E2, E1, EA, EB, EC, ED, EE, EF, EG, EH, EI,
            F3, F2, F1, FA, FB, FC, FD, FE, FF, FG, FH, FI,
            G2, G1, GA, GB, GC, GD, GE, GF, GG, GH, GI,
            H1, HA, HB, HC, HD, HE, HF, HG, HH, HI,
            IA, IB, IC, ID, IE, IF, IG, IH, II,
            J1, JA, JB, JC, JD, JE, JF, JG, JH, JI,
            K2, K1, KA, KB, KC, KD, KE, KF, KG, KH, KI,
            L3, L2, L1, LA, LB, LC, LD, LE, LF, LG, LH, LI,
            M4, M3, M2, M1, MA, MB, MC, MD, ME, MF, MG, MH, MI,
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

        E4 = new Node("E4",4, -4, 0); //E4
        E3 = new Node("E3",4, -3,0); //E3
        E2 = new Node("E2",4, -2,0); //E2
        E1 = new Node("E1",4, -1,0); //E1
        EA = new Node("EA",4,0,4);
        EB = new Node("EB",4,1,5);
        EC = new Node("EC",4,2,6);
        ED = new Node("ED",4,3,5);
        EE = new Node("EE",4,4,4);
        EF = new Node("EF",4, 5,0); //EF
        EG = new Node("EG",4, 6,0); //EG
        EH = new Node("EH",4, 7,0); //EH
        EI = new Node("EI",4, 8,0); //EI

        F3 = new Node("F3",5, -3,0); //F3
        F2 = new Node("F2",5, -2,0); //F2
        F1 = new Node("F1",5, -1,0); //F1
        FA = new Node("FA",5,0,4);
        FB = new Node("FB",5,1,5);
        FC = new Node("FC",5,2,6);
        FD = new Node("FD",5,3,7);
        FE = new Node("FE",5,4,5);
        FF = new Node("FF",5,5,4);
        FG = new Node("FG",5, 6,0); //FG
        FH = new Node("FH",5, 7,0); //FH
        FI = new Node("FI",5, 8,0); //FI

        G2 = new Node("G2",6,-2,0); //G2
        G1 = new Node("G1",6,-1,0); //G1
        GA = new Node("GA",6,0,3);
        GB = new Node("GB",6,1,6);
        GC = new Node("GC",6,2,7);
        GD = new Node("GD",6,3,8);
        GE = new Node("GE",6,4,7);
        GF = new Node("GF",6,5,6);
        GG = new Node("GG",6,6,3);
        GH = new Node("GH",6,7,0); //GH
        GI = new Node("GI",6,8,0); //GI

        H1 = new Node("H1",7, -1,0); //H1
        HA = new Node("HA",7,0,2);
        HB = new Node("HB",7,1,4);
        HC = new Node("HC",7,2,8);
        HD = new Node("HD",7,3,9);
        HE = new Node("HE",7,4,9);
        HF = new Node("HF",7,5,8);
        HG = new Node("HG",7,6,4);
        HH = new Node("HH",7,7,2);
        HI = new Node("H1",7, 8,0); //H1

        IA = new Node("IA",8,0,5);
        IB = new Node("IB",8,1,8);
        IC = new Node("IC",8,2,9);
        ID = new Node("ID",8,3,10);
        IE = new Node("IE",8,4,11);
        IF = new Node("IF",8,5,10);
        IG = new Node("IG",8,6,9);
        IH = new Node("IH",8,7,8);
        II = new Node("II",8,8,5);

        J1 = new Node("J1",9, 0,0); //J1
        JA = new Node("JA",9,1,6);
        JB = new Node("JB",9,2,9);
        JC = new Node("JC",9,3,10);
        JD = new Node("JD",9,4,12);
        JE = new Node("JE",9,5,12);
        JF = new Node("JF",9,6,10);
        JG = new Node("JG",9,7,9);
        JH = new Node("JH",9,8,6);
        JI = new Node("JI",9,9,0); //JI

        K2 = new Node("K2",10,0,0);
        K1 = new Node("K1",10,1,0);
        KA = new Node("KA",10,2,7);
        KB = new Node("KB",10,3,11);
        KC = new Node("KC",10,4,13);
        KD = new Node("KD",10,5,14);
        KE = new Node("KE",10,6,13);
        KF = new Node("KF",10,7,11);
        KG = new Node("KG",10,8,7);
        KH = new Node("KH",10,9,0);
        KI = new Node("KI",10,10,0);

        L3 = new Node("L3",11,0,0);
        L2 = new Node("L2",11,1,0);
        L1 = new Node("L1",11,2,8);
        LA = new Node("LA",11,3,13);
        LB = new Node("LB",11,4,14);
        LC = new Node("LC",11,5,15);
        LD = new Node("LD",11,6,15);
        LE = new Node("LE",11,7,14);
        LF = new Node("LF",11,8,13);
        LG = new Node("LG",11,9,8);
        LH = new Node("LH",11,10,0);
        LI = new Node("LI",11,11,0);

        M4 = new Node("M4",12,0,0);
        M3 = new Node("M3",12,1,0);
        M2 = new Node("M2",12,2,0);
        M1 = new Node("M1",12,3,0);
        MA = new Node("MA",12,4,18);
        MB = new Node("MB",12,5,18);
        MC = new Node("MC",12,6,18);
        MD = new Node("MD",12,7,18);
        ME = new Node("ME",12,8,18);
        MF = new Node("M4",12,9,0);
        MG = new Node("M3",12,10,0);
        MH = new Node("M2",12,11,0);
        MI = new Node("M1",12,12,0);

        NA = new Node("NA",13,5,30);
        NB = new Node("NB",13,6,30);
        NC = new Node("NB",13,7,30);
        ND = new Node("NB",13,8,30);

        OA = new Node("OA",14,6,100);
        OB = new Node("OB",14,7,100);
        OC = new Node("OC",14,8,100);

        PA = new Node("PA",15,7,1000);
        PB = new Node("PB",15,8,1000);

        QA = new Node("QA",16,8,500);
    }

    private static void makeNodeList(){

        /*nodeList = new Node[]{AA,
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
                QA};*/

        nodeList = new Node[]{
                                        AA,
                                      BA, BB,
                                    CA, CB, CC,
                                  DA, DB, DC, DD,
                E4, E3, E2, E1, EA, EB, EC, ED, EE, EF, EG, EH, EI,
                  F3, F2, F1, FA, FB, FC, FD, FE, FF, FG, FH, FI,
                    G2, G1, GA, GB, GC, GD, GE, GF, GG, GH, GI,
                      H1, HA, HB, HC, HD, HE, HF, HG, HH, HI,
                        IA, IB, IC, ID, IE, IF, IG, IH, II,
                      J1, JA, JB, JC, JD, JE, JF, JG, JH, JI,
                    K2, K1, KA, KB, KC, KD, KE, KF, KG, KH, KI,
                  L3, L2, L1, LA, LB, LC, LD, LE, LF, LG, LH, LI,
                M4, M3, M2, M1, MA, MB, MC, MD, ME, MF, MG, MH, MI,
                                  NA, NB, NC, ND,
                                    OA, OB, OC,
                                      PA, PB,
                                        QA};

        System.out.println("NodeList Length = " + nodeList.length);

        for(int i=0; i<nodeList.length;i++){
            System.out.println(nodeList[i].getLabel());
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
            for(int i=0; i<10; i++){
                nodeList[i].setColor(Color.BLUE);
                nodeList[120-i].setColor(Color.RED);
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