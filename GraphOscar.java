import org.w3c.dom.CDATASection;
import java.awt.*;
import java.util.ArrayList;

public class GraphOscar {

    /*
     *   Creates a chinese checkers board as the form of a graph containing
     *
     */
    private Node[] nodeList;

    private Node
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


    public GraphOscar(int numberOfPlayers){
        makeNodes();
        makeNodeList();
        connectEdges();
        initializeStartingPositions(numberOfPlayers);
    }

    //6 players
    public GraphOscar(int numberOfPlayers, ArrayList<Integer> army1, ArrayList<Integer> army2,
                 ArrayList<Integer> army3, ArrayList<Integer> army4,
                 ArrayList<Integer> army5, ArrayList<Integer> army6,
                 Color army1Color, Color army2Color,
                 Color army3Color, Color army4Color,
                 Color army5Color, Color army6Color) {
        this.makeNodes();
        this.makeNodeList();
        this.connectEdges();
        this.customGraphInitializer6Players(army1, army2, army3, army4, army5, army6,
                army1Color, army2Color, army3Color, army4Color, army5Color, army6Color);
    }

    //4 players
    public GraphOscar(int numberOfPlayers, ArrayList<Integer> army1, ArrayList<Integer> army2,
                      ArrayList<Integer> army3, ArrayList<Integer> army4,
                      Color army1Color, Color army2Color,
                      Color army3Color, Color army4Color) {
        this.makeNodes();
        this.makeNodeList();
        this.connectEdges();
        this.customGraphInitializer4Players(army1, army2, army3, army4,
                army1Color, army2Color, army3Color, army4Color);
    }

    public GraphOscar(int numberOfPlayers, ArrayList<Integer> army1, ArrayList<Integer> army2,
                      ArrayList<Integer> army3,
                      Color army1Color, Color army2Color,
                      Color army3Color) {
        this.makeNodes();
        this.makeNodeList();
        this.connectEdges();
        this.customGraphInitializer3Players(army1, army2, army3,
                army1Color, army2Color, army3Color);
    }

    public void customGraphInitializer6Players(ArrayList<Integer> army1, ArrayList<Integer> army2,
                                       ArrayList<Integer> army3, ArrayList<Integer> army4,
                                       ArrayList<Integer> army5, ArrayList<Integer> army6,
                                       Color army1Color, Color army2Color,
                                       Color army3Color, Color army4Color,
                                       Color army5Color, Color army6Color) {
        for (Integer node : army1) {
            getSecNode(node).setColor(army1Color);
        }
        for (Integer node : army2) {
            getSecNode(node).setColor(army2Color);
        }
        for (Integer node : army3) {
            getSecNode(node).setColor(army3Color);
        }
        for (Integer node : army4) {
            getSecNode(node).setColor(army4Color);
        }
        for (Integer node : army5) {
            getSecNode(node).setColor(army5Color);
        }
        for (Integer node : army6) {
            getSecNode(node).setColor(army6Color);
        }
    }

    public void customGraphInitializer4Players(ArrayList<Integer> army1, ArrayList<Integer> army2,
                                       ArrayList<Integer> army3, ArrayList<Integer> army4,
                                       Color army1Color, Color army2Color,
                                       Color army3Color, Color army4Color) {
        for (Integer node : army1) {
            getSecNode(node).setColor(army1Color);
        }
        for (Integer node : army2) {
            getSecNode(node).setColor(army2Color);
        }
        for (Integer node : army3) {
            getSecNode(node).setColor(army3Color);
        }
        for (Integer node : army4) {
            getSecNode(node).setColor(army4Color);
        }
    }

    public void customGraphInitializer3Players(ArrayList<Integer> army1, ArrayList<Integer> army2,
                                       ArrayList<Integer> army3,
                                       Color army1Color, Color army2Color,
                                       Color army3Color) {
        for (Integer node : army1) {
            getSecNode(node).setColor(army1Color);
        }
        for (Integer node : army2) {
            getSecNode(node).setColor(army2Color);
        }
        for (Integer node : army3) {
            getSecNode(node).setColor(army3Color);
        }
    }

    private void makeNodes(){

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

    private void makeNodeList(){

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


    }

    private void connectEdges(){
        /*
         *   Method to connect the graph
         */
        for(int i=0; i<nodeList.length; i++){
            for(int j=0; j<nodeList.length; j++){
                nodeList[i].addEdge(nodeList[j]);
            }
        }
    }

    private void initializeStartingPositions(int numberOfPlayers){
            if (numberOfPlayers == 6){
            //PLAYER 1 AND 4
            for(int i = 0; i < 10; i++){
                nodeList[i].setColor(Color.BLUE);
                nodeList[120-i].setColor(Color.RED);
            }

            //START PLAYER 2 and 6//
            for (int j=0;j<4;j++) {
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
        if(numberOfPlayers==4){

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
        else if (numberOfPlayers == 3){
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
    public int getNodeIndex(Node node){
        for(int i = 0; i < nodeList.length; i++){
            if(nodeList[i].equals(node)){
                return i;
            }
        }
        return -1;
    }

    //Oscar

    public ArrayList<Node> oG = new ArrayList<Node>();

    public ArrayList<Node> popularChoice(Node n){
        oG.clear();
        Node[] availableNodes = n.adjN(); // Gets adjacent nodes from node n
        for (int i = 0; i < availableNodes.length; i++) { // Goes through all neighbour nodes of n
                choose(availableNodes[i], i);
        }
        return oG;
    }

    public void choose(Node n, int i){
        Node[] av = n.adjN();

        if(!n.isOccupied()){ //if node n is not occupied, it is added to the list of available spots
            oG.add(n);
        }
        else if(!av[i].isOccupied()){
//            System.out.println("trigger on " + av);
                oG.add(av[i]); //if node n is occupied, we check whether the node which lies in the i-direction is available
                isItGucci(av[i], i);
        }

    }

    public void isItGucci(Node n, int m){
        Node[] y = n.adjN(); //gets neighbours of node n
        for (int i = 0; i < y.length; i++) {
            if (!y[i].getLabel().equals("null") &&
                    y[i].isOccupied() && (i + m) != 5 && // The (i+m)!=5 statement is to make sure that the piece doesn't
                    // jump back to its original position
                    !y[i].getAdj(i).isOccupied() &&
                    !y[i].getAdj(i).getLabel().equals("null") &&
                    !oG.contains(y[i].getAdj(i))) {

                oG.add(y[i].getAdj(i));
                isItGucci(y[i].getAdj(i), i);
            }
        }
    }

    public int stepDistance(Node node1, Node node2) {
        int dx = node2.getX() - node1.getX();
        int dy = node2.getY() - node1.getY();
        if (Math.signum(dx) != Math.signum(dy)) {
            return Math.abs(dx - dy);
        }
        else {
            return Math.max(Math.abs(dx), Math.abs(dy));
        }
    }

    public double[] centroid(ArrayList<Node> nodes) {
        double x = 0;
        double y = 0;
        double[] centroidCoordinates = new double[2];
        for (Node node : nodes) {
            x += node.getX();
            y += node.getY();
        }
        centroidCoordinates[0] = x / nodes.size();
        centroidCoordinates[1] = y / nodes.size();
        return centroidCoordinates;
    }

    public double distanceToMiddleLine(Node node) {
        Color colorAI = node.getColor();
        if (colorAI.equals(Color.RED) || colorAI.equals(Color.BLUE)) {
            Node referenceNode = AA;
            int dx = node.getX() - referenceNode.getX();
            int dy = node.getY() - referenceNode.getY();
            return Math.abs(0.5 * dx - dy);
        }
        else if (colorAI.equals(Color.GREEN) || colorAI.equals(Color.ORANGE)) {
            return Math.abs(node.getX() - (0.5 * node.getY() + 6));
        }
        else if (colorAI.equals(Color.BLACK) || colorAI.equals(Color.GRAY)) {
            return Math.abs(-6 + 0.5 * (node.getY() + node.getX()));
        }
        return 0;

    }
    public int getBoardSize(){return nodeList.length;}

    public double centroidNodeDistance(ArrayList<Node> nodes, Node destinationNode) {
        double[] centroidCoordinates = centroid(nodes);
        double dx = destinationNode.getX() - centroidCoordinates[0];
        double dy = destinationNode.getY() - centroidCoordinates[1];
        double x = Math.abs(0.5*dx - dy);
        double y = Math.sqrt(3) / 2.0 * Math.abs(dx);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double radius(ArrayList<Node> nodes) {
        double armyRadius = 0;
        for (Node node : nodes) {
            if (centroidNodeDistance(nodes, node) > armyRadius) {
                armyRadius = centroidNodeDistance(nodes, node);
            }
        }
        return armyRadius;
    }

    public double straightLineDistance(Node node1, Node node2) {
        int dx = node2.getX() - node1.getX(); // Steps in the downward direction
        int dy = node2.getY() - node1.getY(); // Steps in the sideway direction
        double x = Math.abs(0.5*dx - dy);
        double y = Math.sqrt(3) / 2.0 * Math.abs(dx);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Node getRedAIDestinationNode() {
        return AA;
    }

    public Node getBlueAIDestinationNode() {
        return QA;
    }

    public Node getBlackAIDestinationNode() {
        return EI;
    }

    public Node getGrayAIDestinationNode() {
        return M4;
    }

    public Node getGreenAIDestinationNode() {
        return MI;
    }

    public Node getOrangeAIDestinationNode() {
        return E4;
    }

    public ArrayList<Integer> getArmy(Color color) {
        ArrayList<Integer> army = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.getColor().equals(color)) {
                army.add(getNodeIndex(node));
            }
        }
        return army;
    }

    public ArrayList<Node> getNodeArmy(Color color) {
        ArrayList<Node> army = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.getColor().equals(color)) {
                army.add(node);
            }
        }
        return army;
    }

}
