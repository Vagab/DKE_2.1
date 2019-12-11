import java.lang.*;
import java.util.Random;
import java.util.*;

class NormalDistribution {



    public static void main(String[] args) {
        Random rand = new Random();
        int nNodes = 50;
        double[] d = normD(nNodes);
        int range = 3000;
        Map<Integer, Integer> res = new HashMap<Integer, Integer>(nNodes);
//        for (int i = 1; i < d.length; i++) {
//            for (int j = i; j > 0; j--) {
//                if (d[j] > d [j - 1]) {
//                    double temp = d[j];
//                    d[j] = d[j - 1];
//                    d[j - 1] = temp;
//                }
//            }
//        }
        transform(d);
//        for (int i = 0; i < d.length; i++) {
//            System.out.println(d[i]);
//        }
        for(int i = 0; i < range; i++) {
            int mc = monteCarlo(d, rand);
            if (res.get(mc) != null) {
                res.put(mc, res.get(mc) + 1);
            } else {
                res.put(mc, 0);
            }
        }

        for (int i = 1; i <= nNodes; i++) {
            if (res.get(i) == null) res.put(i, 0);
            if (res.get(i) > 0) {
                for(int j = 0; j < res.get(i); j++) {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }

    public static void sort(double[] arr) {
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

    public static void normalize(double[] things) {
        double res = 0;
        for(int i = 0; i < things.length; i++) {
            res += things[i];
        }
        for(int i = 0; i < things.length; i++) {
            things[i] *= 1/res;
        }
        return;
    }

    public static boolean inBetween(double n, double a, double b) {
        return a <= n && n <= b;
    }

    public static int monteCarlo(double[] arr, Random rand) {
        double r = rand.nextDouble();
        if (inBetween(r, 0, arr[0])) { return 0; }
        for(int i = 1; i < arr.length; i++) {
            if (inBetween(r, arr[i-1], arr[i])) { return i; }
        }
        return 0;
    }

    public static double[] normD(int n) {
        double eps = 1E-4;
        double[] domain = {-n/2, n/2};
        double[] probs = new double[n];
        double mean = 0;
        double st = 1;
        double norm = 0;
        double power = 0;
        double step = 0.000001;
        for(double i = domain[0] + step; i < domain[1] - step; i += step) {
            power = (-1.0 / 2.0) * Math.pow(((i - mean) / st), 2);
            norm = (1 / (st * Math.sqrt(2 * Math.PI))) * Math.exp(power);
            probs[(int)(i + n/2)] += norm * step;
        }

        double res = sumAr(probs);

        if (Math.abs(1.0 - res) > eps){normalize(probs);}
        for(int i = 0; i < probs.length; i++) {

        }
        return probs;
    }

    public static double sumAr(double[] arr) {
        double res = 0;
        for(double el : arr) {
            res += el;
        }
        return res;
    }

    public static void transform(double[] arr) {
        for(int i = 1; i < arr.length; i++) {
            arr[i] += arr[i - 1];
        }
    }
}