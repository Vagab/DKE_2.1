import java.lang.Math;
import java.awt.*;

public class Node {

    private String label;
    private int X, Y;       //coordinates of the node in the board
    private Node upRight, upLeft, right, left, dRight, dLeft; // connections with other nodes
    private Color color;
    private boolean occupied = false;


    public Node(String label, int X, int Y){
        this.label = label;
        this.X = X;
        this.Y = Y;
        if(!label.equals("null")){
            upRight = new Node("null", -1, -1);
            upLeft = new Node("null", -1, -1);
            right = new Node("null", -1, -1);
            left = new Node("null", -1, -1);
            dRight = new Node("null", -1, -1);
            dLeft = new Node("null", -1, -1);
        }
    }

    public void setColor(Color color){
        if(color.equals(Color.BLUE)|| color.equals(Color.RED)|| color.equals(Color.GRAY)|| color.equals(Color.ORANGE)
                || color.equals(Color.BLACK)|| color.equals(Color.GREEN)){
            occupied = true;
        }
        else{
            occupied = false;
        }
        this.color = color;
    }

    public Color getColor(){
        if(this.color == null){return Color.WHITE;}
        else
            return this.color;
    }
    public String toString(){
        return label; 
    }

    public String getLabel(){return this.label;}

    public int getX(){return this.X;}

    public int getY(){return this.Y;}

    public Node getUpRight(){
        return this.upRight;
    }

    public Node getUpLeft(){
        return this.upLeft; 
    }

    public Node getRight(){
        return this.right; 
        }

    public Node getLeft(){
        return this.left; 
    }

    public Node getdRight(){
        return this.dRight;
    }

    public Node getdLeft(){
        return this.dLeft;
    }

    public void addEdge(Node toConnect){

        /*
         *   Add adjacent nodes edges to a node
         *
         *   On the chinese checkers board, a dot can only move to its adjacent nodes.
         *   This way, when creating the graph we will create and add these neighbour nodes to each.
         *
         *   There exist a maximum of 6 neighbour node: upRight, upLeft, right, left, dRight and dLeft.
         *
         *   This method adds them by checking certain coordinates conditions such as the x's locations,
         *   if this.Node is located in the upper or lower triangle of the board, and finally the relation
         *   between the y's coordinates.
         *
         */

        if(Math.abs(this.X - toConnect.getX()) > 1 || Math.abs(this.Y - toConnect.getY()) >1) {
            // System.out.println("No adjacent connection");
            return;
        }

        if(this.X < toConnect.getX()){  //dRight and dLeft
            if(this.Y < toConnect.getY()){
                this.dRight = toConnect;
                //System.out.println(this.dRight.getLabel() + " is dRight of " + this.getLabel());
            }
            else if(this.Y == toConnect.getY()){
                this.dLeft = toConnect;
                //System.out.println(this.dLeft.getLabel() + " is dLeft of " + this.getLabel());
            }
        }

        else if(this.X > toConnect.getX()){  //upRight and upLeft
            if(this.Y > toConnect.getY()){
                this.upLeft = toConnect;
                //System.out.println(this.upLeft.getLabel() + " is upLeft of " + this.getLabel());
            }
            else if(this.Y == toConnect.getY()){
                this.upRight = toConnect;
                //System.out.println(this.upRight.getLabel() + " is upRight of " + this.getLabel());
            }
            // else{System.out.println("No adjacent connection");}
        }

        else if(this.X == toConnect.getX()){    //right and left, no need to check which triangle it is
            if(this.Y > toConnect.getY()){
                this.left = toConnect;
                //System.out.println(this.left.getLabel() + " is left of " + this.getLabel());
            }
            else if(this.Y < toConnect.getY()){
                this.right = toConnect;
                //System.out.println(this.right.getLabel() + " is right of " + this.getLabel());
            }
            // else{System.out.println("No adjacent connection");}
        }
        else{
            System.out.println("No adjacent connection");
        }

    }


    public boolean isOccupied(){
        return this.occupied;
    }

    public Node[] adjN(){
        return new Node[]{upRight, upLeft, right, left, dRight, dLeft};
    }
    public Node getAdj(int i){
        Node[] neigh = new Node[]{upRight, upLeft, right, left, dRight, dLeft};
        return neigh[i];
    }

    public void setOccupy(Boolean occupy){
        this.occupied = occupy;
    }

}
