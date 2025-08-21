public class MultiplesSum {
    public static void main(String[] args) {
        int limit = 10; // Example limit
        System.out.println("Sum of multiples of 3 or 5 below " + limit + " is: " + calculateSumOfMultiples(limit));
    }

    public static int calculateSumOfMultiples(int limit) {
        int sum = 0;
        for (int i = 0; i < limit; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }
        return sum;
    }
}
