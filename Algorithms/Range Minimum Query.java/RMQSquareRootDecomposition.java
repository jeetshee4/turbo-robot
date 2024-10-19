//Square root decomposition Approach
import java.util.Arrays;

public class RMQSquareRootDecomposition {
    int[] arr, block;  // Original array and block array to store minimum of blocks
    int blockSize;     // Size of each block

    public RMQSquareRootDecomposition(int[] arr) {
        this.arr = arr;
        int n = arr.length;
        
        // Calculate block size as the square root of array size
        blockSize = (int) Math.sqrt(n);
        
        // Initialize block array to hold the minimum value for each block
        block = new int[(n + blockSize - 1) / blockSize];
        
        // Fill the block array with maximum possible values initially
        Arrays.fill(block, Integer.MAX_VALUE);

        // Fill the block array with the minimum value for each block
        for (int i = 0; i < n; i++) {
            block[i / blockSize] = Math.min(block[i / blockSize], arr[i]);
        }
    }

    // Query for the minimum value in the range [l, r]
    public int query(int l, int r) {
        int min = Integer.MAX_VALUE;  // Store the result of the minimum query
        int startBlock = l / blockSize;  // Find the starting block of l
        int endBlock = r / blockSize;    // Find the ending block of r

        // Case 1: l and r are in the same block
        if (startBlock == endBlock) {
            for (int i = l; i <= r; i++) {
                min = Math.min(min, arr[i]);
            }
        } 
        // Case 2: l and r are in different blocks
        else {
            // Process elements from l to the end of its block
            for (int i = l; i < (startBlock + 1) * blockSize; i++) {
                min = Math.min(min, arr[i]);
            }
            // Process entire blocks between the start and end blocks
            for (int i = startBlock + 1; i < endBlock; i++) {
                min = Math.min(min, block[i]);
            }
            // Process elements from the beginning of r's block to r
            for (int i = endBlock * blockSize; i <= r; i++) {
                min = Math.min(min, arr[i]);
            }
        }

        return min;  // Return the minimum value in the range
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 7, 9, 11, 3, 5, 6};
        RMQSquareRootDecomposition rmq = new RMQSquareRootDecomposition(arr);
        
        // Example queries
        System.out.println(rmq.query(1, 4)); // Output: 2
        System.out.println(rmq.query(0, 8)); // Output: 1
    }
}



