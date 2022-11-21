package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }


    /**
     * Returns a table with rows at N breakpoints from startingN to MAX_N for addLast operation of AList
     */
    public static void timeAListConstruction() {
        //starting nr of ops
        int startingN = Math.multiplyExact(1, 1000);
        //max nr of ops
        int MAX_N = Math.multiplyExact(128, 1000);

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opCounts = new AList<Integer>();
        /**
         * for MAX_N = 128 runs 8 times - br points:
         * 1 000 / 2 000/ 4 000/ 8 000/ 16 000/ 32 000 /64 000 /128 000
         */
        for (int n = startingN; n <= MAX_N; n *=  2) {
            AList timedA = new AList<Integer>();
            Stopwatch sw = new Stopwatch();

            int opCount;
            /**
             * Fill ArrayList timedA with ints from 0 to N
             * */
            for (opCount = 0; opCount < n; opCount += 1) {
                timedA.addLast(opCount);
            }
            //stop Stopwatch
            times.addLast(sw.elapsedTime());

            Ns.addLast(n);
            opCounts.addLast(opCount);
        }

        printTimingTable(Ns, times, opCounts);
    }
}
