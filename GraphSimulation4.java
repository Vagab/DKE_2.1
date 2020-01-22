import org.w3c.dom.CDATASection;
import java.awt.*;
import java.security.spec.ECFieldF2m;
import java.util.ArrayList;

public class GraphSimulation4 implements GraphSim{

    /*
     *   Creates a chinese checkers board as the form of a graph containing
     *
     */
    private Node[] nodeList;
    public Color lightBlue = new Color(0, 0, 182, 155);
    public int numPly; // Number of players

    private Node
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

    public void main(String args[]) {

        makeNodes();
        makeNodeList();
        connectEdges();
        //testNode(nodeList[50]); //test adjacent nodes
        //testNodeColor(IE);      //test the color of a node by printing it
        //testNode(CA);
    }

    public GraphSimulation4() {
        this.numPly = 2;
        makeNodes();
        makeNodeList();
        connectEdges();
        initializeStartingPositions(2);
    }

    public void testNode(Node node) {
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

    public void testNodeColor(Node node) {

        if (node.getColor().equals(Color.BLUE)) {
            System.out.println("The color of the node " + node.getLabel() + " is BLUE");
        } else if (node.getColor().equals(Color.RED)) {
            System.out.println("The color of the node " + node.getLabel() + " is RED");
        } else {
            System.out.println("The color of the node " + node.getLabel() + " is WHITE");
        }


    }

    private void makeNodes() {

        QA = new Node("AA", 0, 0, 0);

        PA = new Node("BA", 1, 0, 20);
        PB = new Node("BB", 1, 1, 20);

        OA = new Node("CA", 2, 0, 25);
        OB = new Node("CB", 2, 1, 40);
        OC = new Node("CC", 2, 2, 25);

        NA = new Node("DA", 3, 0, 35);
        NB = new Node("DB", 3, 1, 50);
        NC = new Node("DC", 3, 2, 50);
        ND = new Node("DD", 3, 3, 35);

        M4 = new Node("E4",4, -4, 0); //E4
        M3 = new Node("E3",4, -3,0); //E3
        M2 = new Node("E2",4, -2,0); //E2
        M1 = new Node("E1",4, -1,0); //E1
        MA = new Node("EA", 4, 0, 39);
        MB = new Node("EB", 4, 1, 44);
        MC = new Node("EC", 4, 2, 59);
        MD = new Node("ED", 4, 3, 44);
        ME = new Node("EE", 4, 4, 39);
        MF = new Node("EF",4, 5,0); //EF
        MG = new Node("EG",4, 6,0); //EG
        MH = new Node("EH",4, 7,0); //EH
        MI = new Node("EI",4, 8,0); //EI

        L3 = new Node("F3",5, -3,0); //F3
        L2 = new Node("F2",5, -2,0); //F2
        L1 = new Node("F1",5, -1,0); //F1
        LA = new Node("FA", 5, 0, 40);
        LB = new Node("FB", 5, 1, 60);
        LC = new Node("FC", 5, 2, 65);
        LD = new Node("FD", 5, 3, 65);
        LE = new Node("FE", 5, 4, 60);
        LF = new Node("FF", 5, 5, 40);
        LG = new Node("FG",5, 6,0); //FG
        LH = new Node("FH",5, 7,0); //FH
        LI = new Node("FI",5, 8,0); //FI

        K2 = new Node("G2",6,-2,0); //G2
        K1 = new Node("G1",6,-1,0); //G1
        KA = new Node("GA", 6, 0, 3);
        KB = new Node("GB", 6, 1, 64);
        KC = new Node("GC", 6, 2, 67);
        KD = new Node("GD", 6, 3, 69);
        KE = new Node("GE", 6, 4, 67);
        KF = new Node("GF", 6, 5, 64);
        KG = new Node("GG", 6, 6, 3);
        KH = new Node("GH",6,7,0); //GH
        KI = new Node("GI",6,8,0); //GI

        JI = new Node("H1",7, -1,0); //H1
        JH = new Node("HA", 7, 0, 2);
        JG   = new Node("HB", 7, 1, 68);
        JF = new Node("HC", 7, 2, 70);
        JE = new Node("HD", 7, 3, 74);
        JD = new Node("HE", 7, 4, 74);
        JC = new Node("HF", 7, 5, 70);
        JB = new Node("HG", 7, 6, 68);
        JA = new Node("HH", 7, 7, 2);
        J1 = new Node("H1",7, 8,0); //H1

        IA = new Node("IA", 8, 0, 1);
        IB = new Node("IB", 8, 1, 65);
        IC = new Node("IC", 8, 2, 69);
        ID = new Node("ID", 8, 3, 75);
        IE = new Node("IE", 8, 4, 79);
        IF = new Node("IF", 8, 5, 75);
        IG = new Node("IG", 8, 6, 69);
        IH = new Node("IH", 8, 7, 65);
        II = new Node("II", 8, 8, 1);

        HI = new Node("J1",9, 0,0); //J1
        HH = new Node("JA", 9, 0, 6);
        HG = new Node("JB", 9, 1, 78);
        HF = new Node("JC", 9, 2, 80);
            HE = new Node("JD", 9, 3, 82);
        HD = new Node("JE", 9, 4, 82);
        HC = new Node("JF", 9, 5, 80);
            HB = new Node("JG", 9, 6, 78);
        HA = new Node("JH", 9, 7, 6);
        H1 = new Node("JI",9,9,0); //JI

        GI = new Node("K2",10,0,0);
        GH = new Node("K1",10,1,0);
        GG = new Node("KA", 10, 0, 7);
        GF = new Node("KB", 10, 1, 79);
        GE  = new Node("KC", 10, 2, 83);
        GD = new Node("KD", 10, 3, 84);
        GC = new Node("KE", 10, 4, 83);
        GB = new Node("KF", 10, 5, 79);
        GA = new Node("KG", 10, 6, 7);
        G1  = new Node("KH",10,9,0);
        G2 = new Node("KI",10,10,0);

        FI = new Node("L3",11,0,0);
        FH = new Node("L2",11,1,0);
        FG   = new Node("L1",11,2,0);
        FF = new Node("LA", 11, 0, 80);
        FE = new Node("LB", 11, 1, 84);
        FD = new Node("LC", 11, 2, 85);
        FC = new Node("LD", 11, 3, 85);
        FB = new Node("LE", 11, 4, 84);
        FA = new Node("LF", 11, 5, 80);
        F1 = new Node("LG",11,9,0);
        F2 = new Node("LH",11,10,0);
        F3 = new Node("LI",11,11,0);

        EI = new Node("M4",12,0,0);
        EH = new Node("M3",12,1,0);
        EG = new Node("M2",12,2,0);
        EF = new Node("M1",12,3,0);
        EE = new Node("MA", 12, 0, 83);
        ED = new Node("MB", 12, 1, 87);
        EC = new Node("MC", 12, 2, 88);
        EB = new Node("MD", 12, 3, 87);
        EA = new Node("ME", 12, 4, 83);
        E1 = new Node("M4",12,9,0);
        E2 = new Node("M3",12,10,0);
        E3 = new Node("M2",12,11,0);
        E4 = new Node("M1",12,12,0);


        DD = new Node("NA", 13, 0, 88);
        DC = new Node("NB", 13, 1, 90);
        DB = new Node("NB", 13, 2, 90);
        DA = new Node("NB", 13, 3, 88);

        CC = new Node("OA", 14, 0, 91);
        CB = new Node("OB", 14, 1, 95);
        CA = new Node("OC", 14, 2, 91);

        BB = new Node("PA", 15, 0, 97);
        BA = new Node("PB", 15, 1, 97);

        AA = new Node("QA", 16, 0, 100);
    }

    private void makeNodeList() {

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

    private void connectEdges() {
        /*
         *   Method to connect the graph
         */
        for (int i = 0; i < nodeList.length; i++) {
            for (int j = 0; j < nodeList.length; j++) {
                nodeList[i].addEdge(nodeList[j]);
            }
        }
    }

    private void initializeStartingPositions(int n) {
        if (n == 2) {
            for (int i = 0; i < 10; i++) {
                nodeList[i].setColor(Color.BLUE);
                nodeList[120 - i].setColor(Color.RED);
            }
        }

    }

    public void initializePositions(int[] blue, int[] red) {
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
