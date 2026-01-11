public class CrypticMessageDecoder {
    public static void main(String[] args) {
        int input = 13579; // You can change this to any positive integer

        // 1. Extract the first digit
        int numDigits = (int) Math.log10(input); // Number of digits - 1
        int firstDigit = input / (int) Math.pow(10, numDigits);

        // 2. Extract the last digit
        int lastDigit = input % 10;

        // 3. Product of first and last digits
        int product = firstDigit * lastDigit;

        // 4. Extract second digit
        int secondDigit = (input / (int) Math.pow(10, numDigits - 1)) % 10;

        // 5. Extract second-last digit
        int secondLastDigit = (input / 10) % 10;

        // 6. Sum of second and second-last digits
        int sum = secondDigit + secondLastDigit;

        // 7. Concatenate product and sum to create the final code
        String decryptedCode = String.valueOf(product) + sum;

        // Output
        System.out.println("The decrypted code is: " + decryptedCode);
    }
}
