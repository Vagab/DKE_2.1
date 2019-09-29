import java.lang.Math;

public class Node {

    private String label;
    private int X, Y;       //coordinates of the node in the board
    private Node upRight, upLeft, right, left, dRight, dLeft; // connections with other nodes

    boolean isUpperTriangle; //if the Node is located in the upper triangle of the board



    public Node(String label, int X, int Y){
        this.label = label;
        this.X = X;
        this.Y = Y;

        if(this.X<=8){this.isUpperTriangle = true;}    //
        else{ this.isUpperTriangle = false;}

    }

    public String getLabel(){return this.label;}
    public int getX(){return this.X;}
    public int getY(){return this.Y;}

    public Node getUpRight(){
        if(this.upRight == null){return new Node("null", -1,-1);}
       else
            return this.upRight; }

    public Node getUpLeft(){
        if(this.upLeft == null){return new Node("null", -1,-1);}
        else
            return this.upLeft; }

    public Node getRight(){
        if(this.right == null){return new Node("null", -1,-1);}
        else
            return this.right; }

    public Node getLeft(){
        if(this.left == null){return new Node("null", -1,-1);}
        else
            return this.left; }

    public Node getdRight(){
        if(this.dRight == null){return new Node("null", -1,-1);}
        else
            return this.dRight;}

    public Node getdLeft(){
        if(this.dLeft == null){return new Node("null", -1,-1);}
        else
            return this.dLeft;}



    public void addEdge(Node toConnect){

        /*
        *   Add adjacent nodes edges to a node
        *
        *   On the chinese checkers board, a dot can only move to its adjacent nodes.
        *   This way, when creating the graph we will create and add these neighbour nodes to each.
        *
        *   There exist a maximum of 6 neighbour node: upRight, upLeft, right, left, dRight and dLeft.
        *
        *   This method add them by checking certain coordinates conditions such as the x's locations,
        *   if this.Node is located in the upper or lower triangle of the board, and finally the relation
        *   between the y's coordinates.
        *
        */

       if(Math.abs(this.X - toConnect.getX()) > 1 || Math.abs(this.Y - toConnect.getY()) >1) {
          // System.out.println("No adjacent connection");
           return;
       }

    if(this.X < toConnect.getX()){  //dRight and dLeft
        if(this.isUpperTriangle){
            if(this.Y < toConnect.getY()){
                this.dRight = toConnect;
                System.out.println(this.dRight.getLabel() + " is dRight of " + this.getLabel());
            }
            else if(this.Y == toConnect.getY()){
                this.dLeft = toConnect;
                System.out.println(this.dLeft.getLabel() + " is dLeft of " + this.getLabel());
            }
           // else{System.out.println("No adjacent connection");}
        }
        else{ //if not UpperTriangle
            if(this.Y == toConnect.getY()){
                this.dRight = toConnect;
                System.out.println(this.dRight.getLabel() + " is dRight of " + this.getLabel());
            }
            else if(this.Y > toConnect.getY()){
                this.dLeft = toConnect;
                System.out.println(this.dLeft.getLabel() + " is dLeft of " + this.getLabel());
            }
            //else{System.out.println("No adjacent connection");}
        }
    }

    else if(this.X > toConnect.getX()){  //upRigt and upLeft
        if(this.isUpperTriangle){
            if(this.Y > toConnect.getY()){
                this.upLeft = toConnect;
                System.out.println(this.upLeft.getLabel() + " is upLeft of " + this.getLabel());
            }
            else if(this.Y == toConnect.getY()){
                this.upRight = toConnect;
                System.out.println(this.upRight.getLabel() + " is upRight of " + this.getLabel());
            }
           // else{System.out.println("No adjacent connection");}
        }
        else{
            if(this.Y == toConnect.getY()){
                this.upLeft = toConnect;
                System.out.println(this.upLeft.getLabel() + " is upLeft of " + this.getLabel());
            }
            else if(this.Y < toConnect.getY()){
                this.upRight = toConnect;
                System.out.println(this.upRight.getLabel() + " is upRight of " + this.getLabel());
            }
           // else{System.out.println("No adjacent connection");}
        }
    }

    else if(this.X == toConnect.getX()){    //right and left, no need to check which triangle it is
            if(this.Y > toConnect.getY()){
                this.left = toConnect;
                System.out.println(this.left.getLabel() + " is left of " + this.getLabel());
            }
            else if(this.Y < toConnect.getY()){
                this.right = toConnect;
                System.out.println(this.right.getLabel() + " is right of " + this.getLabel());
            }
           // else{System.out.println("No adjacent connection");}
    }
    else{
        System.out.println("No adjacent connection");
    }

    }






}
