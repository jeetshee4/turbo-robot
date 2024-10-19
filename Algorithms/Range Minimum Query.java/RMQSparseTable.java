public class RMQSparseTable {
    int[][] sparseTable;  // Sparse table to store precomputed minimums for ranges
    int[] log;            // Logarithmic values for quick computation

    // Constructor to build the sparse table
    public RMQSparseTable(int[] arr) {
        int n = arr.length;
        int maxLog = (int)(Math.log(n) / Math.log(2)) + 1;
        
        sparseTable = new int[n][maxLog];  // Initialize sparse table
        log = new int[n + 1];              // Initialize logarithmic array

        // Precompute logarithmic values
        log[1] = 0;
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;  // log(i) = log(i/2) + 1
        }

        // Initialize the first column of the sparse table (ranges of length 1)
        for (int i = 0; i < n; i++) {
            sparseTable[i][0] = arr[i];
        }

        // Fill the rest of the sparse table
        // sparseTable[i][j] stores the minimum of the range [i, i + 2^j - 1]
        for (int j = 1; j <= log[n]; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                sparseTable[i][j] = Math.min(sparseTable[i][j - 1], 
                                             sparseTable[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // Query to get the minimum value in the range [l, r]
    public int query(int l, int r) {
        // Calculate the largest power of 2 that fits in the range
        int j = log[r - l + 1];
        
        // Return the minimum value from two overlapping intervals of length 2^j
        return Math.min(sparseTable[l][j], sparseTable[r - (1 << j) + 1][j]);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 7, 9, 11, 3, 5, 6};
        RMQSparseTable rmq = new RMQSparseTable(arr);
        
        // Example queries
        System.out.println(rmq.query(1, 4)); // Output: 2
        System.out.println(rmq.query(0, 8)); // Output: 1
    }
}
