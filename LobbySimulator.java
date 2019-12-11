import java.util.*;

public class LobbySimulator {

    private Node bestCandidate;
    private int playLimit = 10;
    private int totalSimulations = 1000;
    int totalScore;
    private int countSimulations = 0;

    private int[] blue, red;

    private double[] probs;


    public LobbySimulator(int[] blue, int[] red){
        this.blue = blue;
        this.red = red;
        this.probs = NormalDistribution.normD(this.blue.length);
        for(double el : probs) {
            System.out.println(el);
        }
        sort(this.probs);
        System.out.println();
        for(double el : probs) {
            System.out.println(el);
        }
        System.out.println();
        NormalDistribution.transform(this.probs);
        for(double el : probs) {
            System.out.println(el);
        }

    }


    public int getBestCandidate(){
        return this.bestCandidate.getIndex();
    }

    public boolean isDone(){
        if(this.countSimulations>=totalSimulations){return true;}
        else
            return false;
    }

    public void sort(double[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }


    public void launch(){

        int maxScore = 0;

        for(int i=0; i<totalSimulations; i++){

            Simulation simulation = new Simulation(blue, red, playLimit, this.probs);
            Node candidate = simulation.startSimulation();
            int score = simulation.getScoreResult();

            if(score>maxScore){
                this.bestCandidate = candidate;
                maxScore = score;
            }
        this.countSimulations = i+1;
        }


    }

    public static void main(String[] args){
        int[] blue = new int[]{0,1,2,3,4,5};
        int[] red = new int[]{80,79,78,77,76,75};

        LobbySimulator test = new LobbySimulator(blue, red);
        int range = 400;
        Map<Integer, Integer> res = new HashMap<Integer, Integer>(blue.length);
        for(int i = 0; i < range; i++) {
            test.launch();
            System.out.println(i);
            if (res.get(test.getBestCandidate()) != null) {
                res.put(test.getBestCandidate(), res.get(test.getBestCandidate()) + 1);
            } else {
                res.put(test.getBestCandidate(), 0);
            }
//            System.out.println("Best candidate is: " + test.getBestCandidate());
        }
        for (int i = 0; i < blue.length; i++) {
            if (res.get(i) == null) res.put(i, 0);
            if (res.get(i) > 0) {
                System.out.print(i + ": " );
                for(int j = 0; j < res.get(i); j++) {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }


}
