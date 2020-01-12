
import java.util.*;

import java.util.HashMap;
import java.util.Map;

public class LobbySimulator {

    private Node bestCandidate;
    private int playLimit;
    private int totalSimulations = 1000;
    int totalScore;
    private int countSimulations = 0;
    private int[] blue, red;

    private int[] simulationsFirstNodeResults;
    private int mostCommonFirstNodeResult;

    private double[] probs;

    Map<Simulation, Integer> simulations = new HashMap<>();

    public LobbySimulator(int[] blue, int[] red, int playLimit){
        this.playLimit = playLimit; //sets the depth of the tree according to the progression of the game
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

        return this.mostCommonFirstNodeResult;

        //return this.bestCandidate.getIndex(); //if convert Node to Index
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

        simulationsFirstNodeResults = new int[totalSimulations];

        for(int i=0; i < totalSimulations; i++){
            System.out.print(i + " ");
            Simulation simulation = new Simulation(blue, red, playLimit, this.probs);
            //Node candidate = simulation.startSimulation();
            int candidate = simulation.startSimulation();
            int score = simulation.getScoreResult();
            simulations.put(simulation, score);

            System.out.println(simulations.values());



           /* if(score>maxScore){   //Use this to get the best first node of the best simulation
                this.bestCandidate = candidate;
                maxScore = score;
            }*/

            simulationsFirstNodeResults[i] = candidate;


            this.countSimulations = i+1;
        }

        System.out.println("----------------------");

        HashMap<Simulation, Integer> hashmap1 = sortByScoreDesc(simulations);

        ///START TEST
        for(Map.Entry<Simulation, Integer> entry : hashmap1.entrySet()) {
            Simulation key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ": " + value);
        }
        ///END TEST

        mostCommonFirstNodeResult = getMostCommon(simulationsFirstNodeResults);
    }

    public static HashMap<Simulation, Integer> sortByScoreDesc(Map<Simulation, Integer> simulations) {

        List<Map.Entry<Simulation, Integer> > list = new LinkedList<>(simulations.entrySet());

        Collections.sort(list, (simulationScore1, simulationScore2) -> (simulationScore2.getValue()).compareTo(simulationScore1.getValue()));

        HashMap<Simulation, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Simulation, Integer> p : list) {
            temp.put(p.getKey(), p.getValue());
        }
        return temp;
    }


    public int getMostCommon(int[] arr){

        int n = 50;  // best 50 simulation scores

        Map<Integer, Integer> hp = new HashMap<Integer, Integer>();

        for(int i = 0; i < n; i++)
        {
            int key = arr[i];
            if(hp.containsKey(key))
            {
                int freq = hp.get(key);
                freq++;
                hp.put(key, freq);
            }
            else
            {
                hp.put(key, 1);
            }
        }

        // find max frequency.
        int max_count = 0;
        int res = -1;

        for(Map.Entry<Integer, Integer> val : hp.entrySet())
        {
            if (max_count < val.getValue())
            {
                res = val.getKey();
                max_count = val.getValue();
            }
        }

        return res;
    }

    public static void main(String[] args){
        int[] blue = new int[]{0,1,2,3,4,5};
        int[] red = new int[]{80,79,78,77,76,75};

        LobbySimulator test = new LobbySimulator(blue, red, 3);
        test.launch();
        System.out.println("Best candidate is: " + test.getBestCandidate());

    }

}