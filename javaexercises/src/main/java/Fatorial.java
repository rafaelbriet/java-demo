public class Fatorial {
    public static void main(String[] args) {
        int number = 5; // Example number
        System.out.println("Fatorial of " + number + " is: " + calculateFatorial(number));
    }

    public static int calculateFatorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * calculateFatorial(n - 1);
    }
}
