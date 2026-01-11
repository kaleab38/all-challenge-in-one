import java.util.Random;
import java.util.Scanner;

public class DongeonWarrior{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        int health = 100;
        int rooms = 5;

        System.out.println("🏰 Welcome to the Dungeon Game! Your adventure begins...\n");

        // Loop through 5 rooms
        for (int i = 1; i <= rooms; i++) {
            System.out.println("Entering room " + i + "...");

            int event = rand.nextInt(3) + 1; // random 1 to 3

            switch (event) {
                case 1: // Trap
                    System.out.println("A trap sprung! You lose 20 health!");
                    health -= 20;
                    System.out.println("Health is now " + health + ".");
                    break;

                case 2: // Healing potion
                    System.out.println("You found a healing potion! You gain 15 health!");
                    health += 15;
                    if (health > 100) {
                        System.out.println("Health exceeded 100, capping to 100.");
                        health = 100;
                    }
                    System.out.println("Health is now " + health + ".");
                    break;

                case 3: // Monster encounter
                    System.out.println("A monster appears! Guess a number (1-5) to defeat it!");
                    int monsterNumber = rand.nextInt(5) + 1;
                    int guess;

                    do {
                        System.out.print("Enter your guess: ");
                        guess = input.nextInt();

                        if (guess != monsterNumber) {
                            System.out.println("Wrong! Try again!");
                        }
                    } while (guess != monsterNumber);

                    System.out.println("You defeated the monster!");
                    break;
            }

            // Check player defeat
            if (health <= 0) {
                System.out.println("💀 You have been defeated in room " + i + ".");
                break;
            }

            System.out.println(); // space between rooms
        }

        // Final results
        if (health > 0) {
            System.out.println("🎉 You cleared the dungeon! Victorious with " + health + " health!");
        }

        input.close();
    }
}
