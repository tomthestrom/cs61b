package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    /**
     * Returns a table with rows at N breakpoints from startingN to MAX_N for getLast operation of SLList
     * Constant amount of operations specified by MAX_OP_COUNT
     */
    public static void timeGetLast() {
        //starting nr of ops
        int startingN = Math.multiplyExact(1, 1000);
        //max nr of ops
        int MAX_N = Math.multiplyExact(128, 1000);

        int MAX_OP_COUNT = Math.multiplyExact(1, 10000);

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opCounts = new AList<Integer>();

        /**
         * for MAX_N = 128 runs 8 times - br points:
         * 1 000 / 2 000/ 4 000/ 8 000/ 16 000/ 32 000 /64 000 /128 000
         * Fills SLList with N items
         * Calls getLast() of the SLList MAX_OP_COUNT times
         */
        for (int n = startingN; n <= MAX_N; n *=  2) {
            SLList timedSLL = new SLList<Integer>();

            int opCount;

            /**
             * Fill SLList timedSLL with stuffing (ints from 0 to N)
             * */
            for (int stuffing = 0; stuffing < n; stuffing += 1) {
                timedSLL.addLast(stuffing);
            }

            Stopwatch sw = new Stopwatch();
            // call getLast() MAX_OP_COUNT times
            for (opCount = 0; opCount < MAX_OP_COUNT; opCount += 1) {
                timedSLL.getLast();
            }
            //stop Stopwatch
            times.addLast(sw.elapsedTime());

            Ns.addLast(n);
            opCounts.addLast(opCount);
        }

        printTimingTable(Ns, times, opCounts);
    }

}
