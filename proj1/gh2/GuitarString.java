package gh2;

import deque.Deque;
import deque.LinkedListDeque;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // Create a buffer with capacity = SR / frequency. You'll need to
        // initially filled  with zeros.
        int bufferCapacity = (int) Math.round(SR / frequency);

        buffer = new LinkedListDeque<Double>();

        for (int i = 0; i < bufferCapacity; i++) {
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // Dequeue everything in buffer, and replace with random numbers
        // between -0.5 and 0.5. You can get such a number by using:
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addLast(getRandWhiteNoiseFreq());
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     * Dequeue the front sample and enqueue a new sample that is
        the average of the two multiplied by the DECAY factor.
     */
    public void tic() {
        double curFirst = buffer.removeFirst();
        double firstTwoAvg = (curFirst + buffer.get(0)) / 2;

        double nextLast = firstTwoAvg * DECAY;

        buffer.addLast(nextLast);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }

    /**
     * Returns a nr between -0.5 and 0.5
     */
    private double getRandWhiteNoiseFreq() {
        return Math.random() - 0.5;
    }
}
