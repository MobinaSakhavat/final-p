import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClueGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of players (3 to 6): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (numPlayers < 3 || numPlayers > 6) {
            System.out.println("Invalid number of players. Please enter a number between 3 and 6.");
            return;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        Game game = new Game(players);
        game.play();
    }
}
