public class LobbySimulator {

    private Node bestCandidate;
    private int playLimit = 20;
    private int totalSimulations = 10000;
    int totalScore;
    private int countSimulations = 0;

    private int[] blue, red;

    private double[] probs;


    public LobbySimulator(int[] blue, int[] red){
        this.blue = blue;
        this.red = red;
        this.probs = NormalDistribution.normD(this.blue.length);
        sort(this.probs);
        NormalDistribution.transform(this.probs);


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
                if (arr[j] > arr[j - 1]) {
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
        test.launch();
        System.out.println("Best candidate is: " + test.getBestCandidate());

    }


}
