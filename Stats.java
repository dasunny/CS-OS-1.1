public class Stats {

    // shared global variables that the worker threads will update
    static int average;
    static int minimum;
    static int maximum;

    // thread that calculates the average of the numbers
    static class AverageWorker implements Runnable {
        private int[] numbers;

        public AverageWorker(int[] numbers) {
            this.numbers = numbers;
        }

        public void run() {
            int sum = 0;
            for (int i = 0; i < numbers.length; i++) {
                sum += numbers[i];
            }
            average = sum / numbers.length;
        }
    }

    // thread that finds the minimum value
    static class MinimumWorker implements Runnable {
        private int[] numbers;

        public MinimumWorker(int[] numbers) {
            this.numbers = numbers;
        }

        public void run() {
            int min = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] < min) {
                    min = numbers[i];
                }
            }
            minimum = min;
        }
    }

    // thread that finds the maximum value
    static class MaximumWorker implements Runnable {
        private int[] numbers;

        public MaximumWorker(int[] numbers) {
            this.numbers = numbers;
        }

        public void run() {
            int max = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }
            maximum = max;
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Provide a list of integers in the command line.");
            return;
        }

        // convert the command line args into an array of ints
        int[] numbers = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        }

        // create the three worker threads
        Thread averageThread = new Thread(new AverageWorker(numbers));
        Thread minimumThread = new Thread(new MinimumWorker(numbers));
        Thread maximumThread = new Thread(new MaximumWorker(numbers));

        // start all three threads
        averageThread.start();
        minimumThread.start();
        maximumThread.start();

        // wait for all three threads to finish before printing results
        try {
            averageThread.join();
            minimumThread.join();
            maximumThread.join();
        } catch (InterruptedException ie) {
            System.out.println("A thread was interrupted.");
        }

        // print the final results
        System.out.println("The average value is " + average);
        System.out.println("The minimum value is " + minimum);
        System.out.println("The maximum value is " + maximum);
    }
}