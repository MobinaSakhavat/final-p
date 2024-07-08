import java.util.*;

public class Game {
        private List<Player> players;
        private List<Card> characters;
        private List<Card> locations;
        private List<Card> rooms;
        private Card thief;
        private Card location;
        private Card room;

        public Game(List<Player> players) {
                this.players = players;
                this.characters = new ArrayList<>(Arrays.asList(
                                new Card("Emma"), new Card("Liam"), new Card("Jack"),
                                new Card("Sophia"), new Card("Emily"), new Card("Ella")));
                this.locations = new ArrayList<>(Arrays.asList(
                                new Card("Under the Vase"), new Card("Hidden Drawer"), new Card("Behind the Picture"),
                                new Card("Inside the Box"), new Card("Under the Table"),
                                new Card("On Top of the Closet")));
                this.rooms = new ArrayList<>(Arrays.asList(
                                new Card("Greenhouse"), new Card("Billiard Room"), new Card("Study Room"),
                                new Card("Living Room"), new Card("Bedroom"), new Card("Piano Room"),
                                new Card("Dining Room"), new Card("Kitchen"), new Card("Library")));
                setupGame();
        }

        private void setupGame() {
                Collections.shuffle(characters);
                Collections.shuffle(locations);
                Collections.shuffle(rooms);

                thief = characters.remove(0);
                location = locations.remove(0);
                room = rooms.remove(0);

                List<Card> allCards = new ArrayList<>();
                allCards.addAll(characters);
                allCards.addAll(locations);
                allCards.addAll(rooms);
                Collections.shuffle(allCards);

                int playerIndex = 0;
                for (Card card : allCards) {
                        players.get(playerIndex).addCard(card);
                        playerIndex = (playerIndex + 1) % players.size();
                }
        }

        public void play() {
                Scanner scanner = new Scanner(System.in);
                Random random = new Random();
                boolean gameWon = false;

                while (!gameWon) {
                        List<Player> playersToRemove = new ArrayList<>();
                        Iterator<Player> iterator = players.iterator();

                        while (iterator.hasNext()) {
                                Player player = iterator.next();
                                System.out.println(player.getName() + "'s turn.");
                                int diceRoll = random.nextInt(6) + 1;
                                System.out.println("Rolled a " + diceRoll);

                                List<Card> possibleRooms = new ArrayList<>();
                                for (int i = 0; i < rooms.size(); i++) {
                                        if ((diceRoll % 2 == 0 && i % 2 == 0) || (diceRoll % 2 != 0 && i % 2 != 0)) {
                                                possibleRooms.add(rooms.get(i));
                                        }
                                }

                                Card chosenRoom = possibleRooms.get(random.nextInt(possibleRooms.size()));
                                System.out.println(player.getName() + " moves to " + chosenRoom.getName());

                                System.out.println("Make a guess (character, location): ");
                                String guessedCharacter = scanner.nextLine();
                                String guessedLocation = scanner.nextLine();

                                Card guessedCharacterCard = new Card(guessedCharacter);
                                Card guessedLocationCard = new Card(guessedLocation);

                                boolean found = false;
                                for (Player otherPlayer : players) {
                                        if (otherPlayer != player) {
                                                if (otherPlayer.hasCard(guessedCharacterCard)) {
                                                        System.out.println(otherPlayer.getName() + " has "
                                                                        + guessedCharacter);
                                                        found = true;
                                                        break;
                                                } else if (otherPlayer.hasCard(guessedLocationCard)) {
                                                        System.out.println(otherPlayer.getName() + " has "
                                                                        + guessedLocation);
                                                        found = true;
                                                        break;
                                                }
                                        }
                                }

                                if (!found) {
                                        System.out.println(
                                                        "No one has the guessed cards. Do you want to make a final guess? (yes/no): ");
                                        String response = scanner.nextLine();

                                        if (response.equalsIgnoreCase("yes")) {
                                                System.out.println("Make a final guess (character, location, room): ");
                                                String finalCharacter = scanner.nextLine();
                                                String finalLocation = scanner.nextLine();
                                                String finalRoom = scanner.nextLine();

                                                if (finalCharacter.equals(thief.getName())
                                                                && finalLocation.equals(location.getName())
                                                                && finalRoom.equals(room.getName())) {
                                                        System.out.println(
                                                                        "Congratulations! You found the thief, location, and room!");
                                                        gameWon = true;
                                                        break;
                                                } else {
                                                        System.out.println("Wrong guess. You are out of the game.");
                                                        playersToRemove.add(player);
                                                        iterator.remove();
                                                        if (players.size() == 1) {
                                                                System.out.println("No more players left. Game over.");
                                                                gameWon = true;
                                                                break;
                                                        }
                                                }
                                        }
                                }
                        }
                        players.removeAll(playersToRemove);
                }
                scanner.close();
        }
}
