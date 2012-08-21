public class PercolationStats {
    private int[] x_t;
//    public PercolationStats (int N, int T) {
//        if ( ( N <= 0 ) || ( T <= 0) )
//            throw new java.lang.IllegalArgumentException("Either N or T has illegal values");
//        size = N;
//        trials = T;
//        x_t = new int[T];
//        for (int i = 0; i <= T-1; i++)
//        {
//            Percolation perc = new Percolation(N);
//            x_t[i] = 0;
//            while (!perc.percolates())
//            {
//                perc.open(StdRandom.uniform(1, N), StdRandom.uniform(1, N));
//                x_t[i] += 1;
//            }
//            perc = null;
//        }
//    }
    public PercolationStats (int N, int T) {
        x_t = new int[T];
        for (int i=0;i<T;i++)
            x_t[i]=N;
    }
    public double mean () {
        return StdStats.mean(x_t);
    }
    public double stddev () {
        return StdStats.stddev(x_t);
    }
    public static void main (String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        StdOut.printf("N and T                 = %d, %d\n", N, T );
        PercolationStats stat = new PercolationStats(N, T);
        double c;
        double mean = stat.mean();
        double stddev = stat.stddev();
        c = 1.96 * stddev / Math.sqrt(T);
        StdOut.printf("mean                    = %f\n", mean );
        StdOut.printf("stddev                  = %f\n", stddev );
        StdOut.printf("95% confidence interval = %f, %f\n", mean - c, mean + c);
    }
}
