package org.farm.game;

import java.util.Scanner;
import org.farm.farm.Farm;
import org.farm.market.Market;

public class Game {
    private final Farm farm;
    private final Market market;
    private int turn;
    Scanner scanner = new Scanner(System.in);

    public Game() {
        this.farm = new Farm();
        this.market = new Market();
        this.turn = 0;
    }

    public void start() {
        while (true) {  // Replace with a proper game-ending condition
            while (true) {
                System.out.println("\n===== FARM GAME =====");

                System.out.println("1. Harvest Crops");
                System.out.println("2. Check Market");
                System.out.println("3. Next Turn");
                System.out.println("4. Check Farm");
              //  System.out.println("5. Check Farm");
                System.out.println("6. Exit");
                System.out.print("Enter command: ");
                //farm.triggerCatastrophe();
                String command = scanner.nextLine().toLowerCase();

                switch (command) {
                    case "1":
                        farm.harvest();
                        break;
                    case "2":
                        System.out.println(market.getProducts());
                        break;
                    case "3":
                        System.out.println("Buy product {name,quantity} for now index");
                        String buy_product = scanner.nextLine().toLowerCase();
                        market.buyProduct(market.getProducts().get(Integer.parseInt(buy_product)),farm);
                        break;
                    case "4":
                        System.out.println("Sell product {name,quantity} for now index");
                        String sell_product = scanner.nextLine().toLowerCase();

                        market.sellProduct(farm.getStorage().getbyIndex(Integer.parseInt(sell_product)),farm);
                        break;

                    case "5":
                        nextTurn();
                        break;
                    case "6":
                        farm.printFarmStatus();
                        break;
                    case "7":
                        farm.getStorage().printStorage();
                        break;
                    case "8":
                        System.out.println("Exiting the game...");
                        return;  // Exit the game
                    default:
                        System.out.println("Invalid command! Please try again.");
                }
            }
        }
    }

    private void nextTurn() {
        turn++;
        farm.incrementAnimalAge();
        farm.triggerCatastrophe();
        farm.harvest();
        System.out.println("Turn " + turn + " complete.");
    }
}

