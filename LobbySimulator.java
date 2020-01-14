import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LobbySimulator {

    private Node bestCandidate;
    private int playLimit;
    private int totalSimulations = 10000;
    int totalScore;
    private int countSimulations = 0;

    private int[] blue, red;

    private int[] simulationsFirstNodeResults;
    private int mostCommonFirstNodeResult;

    private double[] probs;

    private int bestPreviousScore;



    public LobbySimulator(int[] blue, int[] red, int playLimit, int bestPreviousScore){
        this.playLimit = playLimit; //sets the depth of the tree according to the progression of the game
        this.blue = blue;
        this.red = red;
        this.bestPreviousScore = bestPreviousScore;

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

        int nbImprovedSimulations = 0;
        simulationsFirstNodeResults = new int[totalSimulations];

        for(int i=0; i<totalSimulations; i++){
            //System.out.print(i + " ");
            Simulation simulation = new Simulation(blue, red, playLimit, this.probs);
            //Node candidate = simulation.startSimulation();
            int candidate = simulation.startSimulation();
            int score = simulation.getScoreResult();

           /* if(score>maxScore){   //Use this to get the best first node of the best simulation
                this.bestCandidate = candidate;
                maxScore = score;
            }*/

           simulationsFirstNodeResults[i] = candidate;

           /* if(score >= this.bestPreviousScore) {
                simulationsFirstNodeResults[nbImprovedSimulations] = candidate;
                nbImprovedSimulations += 1;
            }*/



        this.countSimulations = i+1;
        }


        int[] finalNodeResults = new int[nbImprovedSimulations];

       /* //Resize the array
        for(int j=0; j<nbImprovedSimulations; j++){
            finalNodeResults[j] = simulationsFirstNodeResults[j];
        }*/


        //finalNodeResults
        mostCommonFirstNodeResult = getMostCommon(simulationsFirstNodeResults);


    }

    public int getMostCommon(int[] arr){

        int n = arr.length;

        Map<Integer, Integer> hp =
                new HashMap<Integer, Integer>();

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

        LobbySimulator test = new LobbySimulator(blue, red, 4, -1);
        test.launch();
        System.out.println("Best candidate is: " + test.getBestCandidate());

    }


}
