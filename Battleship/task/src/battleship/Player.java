package battleship;

import java.util.Arrays;

public class Player extends Main {

    public Player(String name, String[][] field) {
        this.name = name;
        this.field = field;
    }

    public static void main(String[] args) {

        Player player1 = new Player("Player 1", new String[10][10]);
        Player player2 = new Player("Player 2", new String[10][10]);

        Player[] game = new Player[]{player1, player2};

        for (Player player : game) {
            player.makeField();
            System.out.println(player.getName() + ", place your ships on the game field");
            player.printField();
            for (int j = 0; j < Ship.values().length; j++) {
                player.shipOnField(Ship.values()[j]);
            }
            promptEnterKey();
        }
        while (true){
            player2.printFogOfWarField();
            System.out.println("---------------------");
            player1.printField();
            System.out.println(player1.getName()+", it's your turn:");
            player1.shotCoordinate();
            player1.checkShot(player2.field);
            promptEnterKey();
            player1.printFogOfWarField();
            System.out.println("---------------------");
            player2.printField();
            System.out.println(player2.getName()+", it's your turn:");
            player2.shotCoordinate();
            player2.checkShot(player1.field);
            promptEnterKey();
        }
    }
}
