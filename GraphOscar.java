import org.w3c.dom.CDATASection;
import java.awt.*;
import java.util.ArrayList;

public class GraphOscar {

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
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(QA);
        nodes.add(PA);
        nodes.add(PB);
//        System.out.println(straightLineDistance(AA,J1));
        for (Node node : nodeList) {
            System.out.println(straightLineDistance(AA,node));
        }



//        System.out.println(centroidNodeDistance(nodes,MC));
//        System.out.println(centroidNodeDistance(nodes,ME));
    }

    public GraphOscar(int n){
        this.numPly = n;
        makeNodes();
        makeNodeList();
        connectEdges();
        initializeStartingPositions(n);
    }

    public GraphOscar(int n, ArrayList<Integer> army1, ArrayList<Integer> army2,
                 ArrayList<Integer> army3, ArrayList<Integer> army4,
                 ArrayList<Integer> army5, ArrayList<Integer> army6,
                 Color army1Color, Color army2Color,
                 Color army3Color, Color army4Color,
                 Color army5Color, Color army6Color) {
        this.numPly = n;
        this.makeNodes();
        this.makeNodeList();
        this.connectEdges();
        this.customGraphInitializer(army1, army2, army3, army4, army5, army6,
                army1Color, army2Color, army3Color, army4Color, army5Color, army6Color);
    }

    public void customGraphInitializer(ArrayList<Integer> army1, ArrayList<Integer> army2,
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

    private static void makeNodes(){

        AA = new Node("0",0,0); //AA

        BA = new Node("1",1,0); //AB
        BB = new Node("2",1,1); //BB

        CA = new Node("3",2,0); //CA
        CB = new Node("4",2,1); //CB
        CC = new Node("5",2,2); //CC

        DA = new Node("6",3,0); //DA
        DB = new Node("7",3,1); //DB
        DC = new Node("8",3,2); //DC
        DD = new Node("9",3,3); //DD

        E4 = new Node("10",4, -4); //E4
        E3 = new Node("11",4, -3); //E3
        E2 = new Node("12",4, -2); //E2
        E1 = new Node("13",4, -1); //E1
        EA = new Node("14",4,0); //EA
        EB = new Node("15",4,1); //EB
        EC = new Node("16",4,2); //EC
        ED = new Node("17",4,3); //ED
        EE = new Node("18",4,4); //EE
        EF = new Node("19",4, 5); //EF
        EG = new Node("20",4, 6); //EG
        EH = new Node("21",4, 7); //EH
        EI = new Node("22",4, 8); //EI

        F3 = new Node("23",5, -3); //F3
        F2 = new Node("24",5, -2); //F2
        F1 = new Node("25",5, -1); //F1
        FA = new Node("26",5,0); //FA
        FB = new Node("27",5,1); //FB
        FC = new Node("28",5,2); //FC
        FD = new Node("29",5,3); //FD
        FE = new Node("30",5,4); //FE
        FF = new Node("31",5,5); //FF
        FG = new Node("32",5, 6); //FG
        FH = new Node("33",5, 7); //FH
        FI = new Node("34",5, 8); //FI

        G2 = new Node("35",6,-2); //G2
        G1 = new Node("36",6,-1); //G1
        GA = new Node("37",6,0); //GA
        GB = new Node("38",6,1); //GB
        GC = new Node("39",6,2); //GC
        GD = new Node("40",6,3); //GD
        GE = new Node("41",6,4); //GE
        GF = new Node("42",6,5); //GF
        GG = new Node("43",6,6); //GG
        GH = new Node("44",6,7); //GH
        GI = new Node("45",6,8); //GI

        H1 = new Node("46",7, -1); //H1
        HA = new Node("47",7,0); //HA
        HB = new Node("48",7,1); //HB
        HC = new Node("49",7,2); //HC
        HD = new Node("50",7,3); //HD
        HE = new Node("51",7,4); //HE
        HF = new Node("52",7,5); //HF
        HG = new Node("53",7,6); //HG
        HH = new Node("54",7,7); //HH
        HI = new Node("55",7,8); //HI

        IA = new Node("56",8,0); //IA
        IB = new Node("57",8,1); //IB
        IC = new Node("58",8,2); //IC
        ID = new Node("59",8,3); //ID
        IE = new Node("60",8,4); //IE
        IF = new Node("61",8,5); //IF
        IG = new Node("62",8,6); //IG
        IH = new Node("63",8,7); //IH
        II = new Node("64",8,8); //II

        J1 = new Node("65",9, 0); //J1
        JA = new Node("66",9,1); //JA
        JB = new Node("67",9,2); //JB
        JC = new Node("68",9,3); //JC
        JD = new Node("69",9,4); //JD
        JE = new Node("70",9,5); //JE
        JF = new Node("71",9,6); //JF
        JG = new Node("72",9,7); //JG
        JH = new Node("73",9,8); //JH
        JI = new Node("74",9,9); //JI

        K2 = new Node("75",10,0);
        K1 = new Node("76",10,1);
        KA = new Node("77",10,2);
        KB = new Node("78",10,3);
        KC = new Node("79",10,4);
        KD = new Node("80",10,5);
        KE = new Node("81",10,6);
        KF = new Node("82",10,7);
        KG = new Node("83",10,8);
        KH = new Node("84",10,9);
        KI = new Node("85",10,10);

        L3 = new Node("86",11,0);
        L2 = new Node("87",11,1);
        L1 = new Node("88",11,2);
        LA = new Node("89",11,3);
        LB = new Node("90",11,4);
        LC = new Node("91",11,5);
        LD = new Node("92",11,6);
        LE = new Node("93",11,7);
        LF = new Node("94",11,8);
        LG = new Node("95",11,9);
        LH = new Node("96",11,10);
        LI = new Node("97",11,11);

        M4 = new Node("98",12,0);
        M3 = new Node("99",12,1);
        M2 = new Node("100",12,2);
        M1 = new Node("101",12,3);
        MA = new Node("102",12,4);
        MB = new Node("103",12,5);
        MC = new Node("104",12,6);
        MD = new Node("105",12,7);
        ME = new Node("106",12,8);
        MF = new Node("107",12,9);
        MG = new Node("108",12,10);
        MH = new Node("109",12,11);
        MI = new Node("110",12,12);


        NA = new Node("111",13,5);
        NB = new Node("112",13,6);
        NC = new Node("113",13,7);
        ND = new Node("114",13,8);

        OA = new Node("115",14,6);
        OB = new Node("116",14,7);
        OC = new Node("117",14,8);

        PA = new Node("118",15,7);
        PB = new Node("119",15,8);

        QA = new Node("120",16,8);
    }

    private static void makeNodeList(){

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
            for(int i = 0; i < 10; i++){
                nodeList[i].setColor(Color.BLUE);
                nodeList[120-i].setColor(Color.RED);
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
    public int getNodeIndex(Node node){
        for(int i = 0; i < nodeList.length; i++){
            if(nodeList[i].equals(node)){
                return i;
            }
        }
        return -1;
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

    public static int stepDistance(Node node1, Node node2) {
        int dx = node2.getX() - node1.getX();
        int dy = node2.getY() - node1.getY();
        if (Math.signum(dx) != Math.signum(dy)) {
            return Math.abs(dx - dy);
        } else {
            return Math.max(Math.abs(dx), Math.abs(dy));
        }
    }

    public static double[] centroid(ArrayList<Node> nodes) {
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

    public static double distanceToMiddleLine(Node node, Color colorAI) {
        Node referenceNode = AA;
        int dx = node.getX() - referenceNode.getX();
        int dy = node.getY() - referenceNode.getY();
        if (Math.signum(dx) != Math.signum(dy)) {
            return 0.5 * Math.abs(dx);
        } else {
            return Math.abs(0.5 * (Math.abs(dx - dy) - Math.abs(dy)));
        }
    }

    public static double centroidNodeDistance(ArrayList<Node> nodes, Node destinationNode) {
        double[] centroidCoordinates = centroid(nodes);
        double dx = destinationNode.getX() - centroidCoordinates[0];
        double dy = destinationNode.getY() - centroidCoordinates[1];
        double x = 0;
        double y = 0;
        if (Math.signum(dx) != Math.signum(dy)) {
            x = 0.5 * Math.abs(dx) + Math.abs(dy);
            y = Math.sqrt(3) / 2.0 * Math.abs(dx);
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
        else {
            x =  Math.abs(0.5*dx - dy);
            y = Math.sqrt(3) / 2.0 * Math.abs(dx);
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
    }

    public static double radius(ArrayList<Node> nodes) {
        double armyRadius = 0;
        for (Node node : nodes) {
            if (centroidNodeDistance(nodes, node) > armyRadius) {
                armyRadius = centroidNodeDistance(nodes, node);
            }
        }
        return armyRadius;
    }

    public static double straightLineDistance(Node node1, Node node2) {
        int dx = node2.getX() - node1.getX(); // Steps in the downward direction
        int dy = node2.getY() - node1.getY(); // Steps in the sideway direction
        double x = 0;
        double y = 0;
        if (Math.signum(dx) != Math.signum(dy)) {
            x = 0.5 * Math.abs(dx) + Math.abs(dy);
            y = Math.sqrt(3) / 2.0 * Math.abs(dx);
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
        else {
            x =  Math.abs(0.5*dx - dy);
            y = Math.sqrt(3) / 2.0 * Math.abs(dx);
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
    }

    public Node getRedAIDestinationNode() {
        return AA;
    }

    public Node getBlueAIDestinationNode() {
        return QA;
    }

    public Node getBlackAIDestinationNode() {
        return M4;
    }

    public Node getGreyAIDestinationNode() {
        return EI;
    }

    public Node getGreenAIDestinationNode() {
        return E4;
    }

    public Node getOrangeAIDestinationNode() {
        return MI;
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
