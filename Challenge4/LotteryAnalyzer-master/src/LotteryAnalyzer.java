public class LotteryAnalyzer
{
    public static void main(String[] args) {
        // Initialize array with winning numbers
        String[] winningNumbers = {"12-34-56-78-90", "33-44-11-66-22", "01-02-03-04-05"};

        String highestAvgNumber = "";
        double highestAvg = 0;

        // Loop through each winning number
        for (String number : winningNumbers) {
            System.out.println("Analyzing: " + number);

            // Remove dashes
            String digitsOnly = number.replace("-", "");

            // Convert to array of integers
            char[] charArray = digitsOnly.toCharArray();
            int[] intArray = new int[charArray.length];
            int sum = 0;

            for (int i = 0; i < charArray.length; i++) {
                intArray[i] = Character.getNumericValue(charArray[i]);
                sum += intArray[i];
            }

            // Calculate average
            double average = (double) sum / intArray.length;

            System.out.println("Digit Sum: " + sum + ", Digit Average: " + average);

            // Check if this number has the highest average
            if (average > highestAvg) {
                highestAvg = average;
                highestAvgNumber = number;
            }
        }

        // Print the winning number with the highest average
        System.out.println("The winning number with the highest average is: "
                + highestAvgNumber + " with an average of " + highestAvg);
    }
}
