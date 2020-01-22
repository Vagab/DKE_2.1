import org.w3c.dom.CDATASection;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class GraphSimulation2 implements GraphSim{

    /*
     *   Creates a chinese checkers board as the form of a graph containing
     *
     */
    private static Node[] nodeList;
    public Color lightBlue = new Color(0, 0, 182, 155);
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

    public static void main(String args[]) {

        makeNodes();
        makeNodeList();
        connectEdges();
        //testNode(nodeList[50]); //test adjacent nodes
        //testNodeColor(IE);      //test the color of a node by printing it
        //testNode(CA);
    }

    public GraphSimulation2() {
        this.numPly = 2;
        makeNodes();
        makeNodeList();
        connectEdges();
        initializeStartingPositions(2);
    }

    public static void testNode(Node node) {
        /*
         *   Provides the adjacent nodes
         *   Useful to check connections
         */
        if (node.getLabel() == null) {
            System.out.println("This is not a valid node.");
        } else {

            if (!node.getUpRight().getLabel().equals("null")) {
                System.out.println("The upRight node of " + node.getLabel() + " is " + node.getUpRight().getLabel());
            } else {
                System.out.println("There is no upRight node for " + node.getLabel());
            }

            if (!node.getRight().getLabel().equals("null")) {
                System.out.println("The Right node of " + node.getLabel() + " is " + node.getRight().getLabel());
            } else {
                System.out.println("There is no Right node for " + node.getLabel());
            }

            if (!node.getdRight().getLabel().equals("null")) {
                System.out.println("The dRight node of " + node.getLabel() + " is " + node.getdRight().getLabel());
            } else {
                System.out.println("There is no dRight node for " + node.getLabel());
            }

            if (!node.getdLeft().getLabel().equals("null")) {
                System.out.println("The dLeft node of " + node.getLabel() + " is " + node.getdLeft().getLabel());
            } else {
                System.out.println("There is no dLeft node for " + node.getLabel());
            }

            if (!node.getLeft().getLabel().equals("null")) {
                System.out.println("The Left node of " + node.getLabel() + " is " + node.getLeft().getLabel());
            } else {
                System.out.println("There is no Left node for " + node.getLabel());
            }

            if (!node.getUpLeft().getLabel().equals("null")) {
                System.out.println("The UpLeft node of " + node.getLabel() + " is " + node.getUpLeft().getLabel());
            } else {
                System.out.println("There is no UpLeft node for " + node.getLabel());
            }

        }

    }

    public static void testNodeColor(Node node) {

        if (node.getColor().equals(Color.BLUE)) {
            System.out.println("The color of the node " + node.getLabel() + " is BLUE");
        } else if (node.getColor().equals(Color.RED)) {
            System.out.println("The color of the node " + node.getLabel() + " is RED");
        } else {
            System.out.println("The color of the node " + node.getLabel() + " is WHITE");
        }


    }

    private static void makeNodes() {

        EI = new Node("EI", 0, 0, 0);

        EH = new Node("EH", 1, 0, 20);
        FI = new Node("FI", 1, 1, 20);

        EG = new Node("EG", 2, 0, 25);
        FH = new Node("FH", 2, 1, 40);
        GI = new Node("GI", 2, 2, 25);

        EF = new Node("DA", 3, 0, 35);
        FG = new Node("DB", 3, 1, 50);
        GH = new Node("DC", 3, 2, 50);
        HI = new Node("DD", 3, 3, 35);

        AA = new Node("E4",4, -4, 0); //E4
        BB = new Node("E3",4, -3,0); //E3
        CC = new Node("E2",4, -2,0); //E2
        DD = new Node("E1",4, -1,0); //E1
        EE = new Node("EA", 4, 0, 39);
        FF = new Node("EB", 4, 1, 44);
        GG = new Node("EC", 4, 2, 59);
        HH = new Node("ED", 4, 3, 44);
        II = new Node("EE", 4, 4, 39);
        JI = new Node("EF",4, 5,0); //EF
        KI = new Node("EG",4, 6,0); //EG
        LI = new Node("EH",4, 7,0); //EH
        MI = new Node("EI",4, 8,0); //EI

        BA = new Node("F3",5, -3,0); //F3
        CB = new Node("F2",5, -2,0); //F2
        DC = new Node("F1",5, -1,0); //F1
        ED = new Node("FA", 5, 0, 40);
        FE = new Node("FB", 5, 1, 60);
        GF = new Node("FC", 5, 2, 65);
        HG = new Node("FD", 5, 3, 65);
        IG = new Node("FE", 5, 4, 60);
        JH = new Node("FF", 5, 5, 40);
        KH = new Node("FG",5, 6,0); //FG
        LH = new Node("FH",5, 7,0); //FH
        MH = new Node("FI",5, 8,0); //FI

        CA = new Node("G2",6,-2,0); //G2
        DB = new Node("G1",6,-1,0); //G1
        EC = new Node("GA", 6, 0, 3);
        FD = new Node("GB", 6, 1, 64);
        GE = new Node("GC", 6, 2, 67);
        HF = new Node("GD", 6, 3, 69);
        IH = new Node("GE", 6, 4, 67);
        JG = new Node("GF", 6, 5, 64);
        KG = new Node("GG", 6, 6, 3);
        LG = new Node("GH",6,7,0); //GH
        MG = new Node("GI",6,8,0); //GI

        DA = new Node("H1",7, -1,0); //H1
        EB = new Node("HA", 7, 0, 2);
        FC = new Node("HB", 7, 1, 68);
        GD = new Node("HC", 7, 2, 70);
        HE = new Node("HD", 7, 3, 74);
        IF = new Node("HE", 7, 4, 74);
        JF = new Node("HF", 7, 5, 70);
        KF = new Node("HG", 7, 6, 68);
        LF = new Node("HH", 7, 7, 2);
        MF = new Node("H1",7, 8,0); //H1

        EA = new Node("IA", 8, 0, 1);
        FB = new Node("IB", 8, 1, 65);
        GC = new Node("IC", 8, 2, 69);
        HD = new Node("ID", 8, 3, 75);
        IE = new Node("IE", 8, 4, 79);
        JE = new Node("IF", 8, 5, 75);
        KE = new Node("IG", 8, 6, 69);
        LE = new Node("IH", 8, 7, 65);
        ME = new Node("II", 8, 8, 1);

        E4 = new Node("J1",9, 0,0); //J1
        FA = new Node("JA", 9, 0, 6);
        GB = new Node("JB", 9, 1, 78);
        HC = new Node("JC", 9, 2, 80);
        ID = new Node("JD", 9, 3, 82);
        JD = new Node("JE", 9, 4, 82);
        KD = new Node("JF", 9, 5, 80);
        LD = new Node("JG", 9, 6, 78);
        MD = new Node("JH", 9, 7, 6);
        ND = new Node("JI",9,9,0); //JI

        E3  = new Node("K2",10,0,0);
        F3 = new Node("K1",10,1,0);
        GA = new Node("KA", 10, 0, 7);
        HB = new Node("KB", 10, 1, 79);
        IC = new Node("KC", 10, 2, 83);
        JC = new Node("KD", 10, 3, 84);
        KC = new Node("KE", 10, 4, 83);
        LC = new Node("KF", 10, 5, 79);
        MC = new Node("KG", 10, 6, 7);
        NC = new Node("KH",10,9,0);
        OC = new Node("KI",10,10,0);

        E2 = new Node("L3",11,0,0);
        F2  = new Node("L2",11,1,0);
        G1 = new Node("L1",11,2,0);
        HA = new Node("LA", 11, 0, 80);
        IB = new Node("LB", 11, 1, 84);
        JB = new Node("LC", 11, 2, 85);
        KB = new Node("LD", 11, 3, 85);
        LB = new Node("LE", 11, 4, 84);
        MB = new Node("LF", 11, 5, 80);
        NB = new Node("LG",11,9,0);
        OB = new Node("LH",11,10,0);
        PB = new Node("LI",11,11,0);

        E1 = new Node("M4",12,0,0);
        F1 = new Node("M3",12,1,0);
        G2 = new Node("M2",12,2,0);
        H1 = new Node("M1",12,3,0);
        IA = new Node("MA", 12, 0, 83);
        JA = new Node("MB", 12, 1, 87);
        KA = new Node("MC", 12, 2, 88);
        LA = new Node("MD", 12, 3, 87);
        MA = new Node("ME", 12, 4, 83);
        NA = new Node("M4",12,9,0);
        OA = new Node("M3",12,10,0);
        PA = new Node("M2",12,11,0);
        QA = new Node("M1",12,12,0);


        J1 = new Node("NA", 13, 0, 88);
        K1 = new Node("NB", 13, 1, 90);
        L1 = new Node("NB", 13, 2, 90);
        M1 = new Node("NB", 13, 3, 88);

        K2 = new Node("OA", 14, 0, 91);
        L2 = new Node("OB", 14, 1, 95);
        M2 = new Node("OC", 14, 2, 91);

        L3  = new Node("PA", 15, 0, 97);
        M3 = new Node("PB", 15, 1, 97);

        M4 = new Node("QA", 16, 0, 100);
    }

    private static void makeNodeList() {

       /* nodeList = new Node[]{AA,
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

        for (int i = 0; i < nodeList.length; i++) {
            nodeList[i].setIndex(i);
        }

    }

    private static void connectEdges() {
        /*
         *   Method to connect the graph
         */
        for (int i = 0; i < nodeList.length; i++) {
            for (int j = 0; j < nodeList.length; j++) {
                nodeList[i].addEdge(nodeList[j]);
            }
        }
    }

    private static void initializeStartingPositions(int n) {
        if (n == 2) {
            for (int i = 0; i < 6; i++) {
                nodeList[i].setColor(Color.BLUE);
                nodeList[120 - i].setColor(Color.RED);
            }
        }

    }

    public static void initializePositions(int[] blue, int[] red) {
        for (int i = 0; i < nodeList.length; i++) {    //reset the board, don't use please it's dangerous
            nodeList[i].setColor(Color.WHITE);
        }

        for (int i = 0; i < blue.length; i++) { //sets the positions
            nodeList[blue[i]].setColor(Color.BLUE);
            nodeList[red[i]].setColor(Color.RED);
        }
    }


    public int getBoardSize() {
        return nodeList.length;
    }

    public int getNodeIndex(int i) {
        return nodeList[i].getIndex();
    }

    public String getNodeLabel(int node) {
        return nodeList[node].getLabel();
    }

    public Node getSecNode(int node) {
        return nodeList[node];
    }

    public Color getNodeColor(int node) {
        return nodeList[node].getColor();
    }

    public int getNodeXCoords(int node) {
        return nodeList[node].getX();
    }

    public int getNodeYCoords(int node) {
        return nodeList[node].getY();
    }

    public Node[] getNodes() {
        return this.nodeList;
    }

    //Oscar

    public ArrayList<Node> oG = new ArrayList<Node>();

    public ArrayList<Node> popularChoice(Node n) {
        oG.clear();
        Node[] availableNodes = n.adjN(); //Gets adjacent nodes from node n
        for (int i = 0; i < availableNodes.length; i++) { // Goes through all neighbour nodes of n
            choose(availableNodes[i], i);
        }
        return oG;
    }

    public void choose(Node n, int i) {
        Node[] av = n.adjN();
        if (!n.isOccupied()) { //if node n is not occupied, it is added to the list of available spots
            oG.add(n);
        } else if (!av[i].isOccupied()) {
            oG.add(av[i]); //if node n is occupied, we check whether the node which lies in the i-direction is available
            isItGucci(av[i], i);
        }

    }

    public void isItGucci(Node n, int m) {
        Node[] y = n.adjN(); //gets neighbours of node n
        for (int i = 0; i < y.length; i++) {
            if (y[i].getLabel() == null) {
                //Do nothing
            }
            if (y[i].isOccupied() && (i + m) != 5) { //The (i+m)!=5 statement is to make sure that the piece doesn't jump back to its original position
                if (y[i].getAdj(i).getLabel() == null) {
                    //Do nothing
                }
                if (!y[i].getAdj(i).isOccupied()) { //
                    if (!oG.contains(y[i].getAdj(i))) {
                        oG.add(y[i].getAdj(i));
                        isItGucci(y[i].getAdj(i), i);
                    }
                }
            }
        }
    }

    public void display() {
        for (int i = 0; i < oG.size(); i++) {
            //System.out.println("possible move " + oG.get(i).getLabel());
        }
    }

}
