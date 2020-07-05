package com.vineet.game;

import com.vineet.CardConstants;
import com.vineet.GameConstants;
import com.vineet.exception.ValidationFailureException;
import com.vineet.model.Player;
import com.vineet.model.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vineet.CardConstants.PLAYER_BET_AMOUNT;
import static com.vineet.CardConstants.PLAYER_MAX_INITIAL_AMOUNT;

public class GameSimulator {
    private DeckManager deckManager;

    public GameSimulator(DeckManager deckManager) {
        this.deckManager = deckManager;
    }

    public void startGame(List<Player> players) {
        System.out.println(" received Start game request for " + players.size() + " players");
        validatePlayerCount(players);
        validateInitialBalance(players);
        Player dealer = new Player(GameConstants.DEALER_NAME.getValue(), PlayerType.DEALER, 0, new ActionStrategy(CardConstants.DEALER_PLAYER_HIT_MAX.getValue()));
        List<Player> eligiblePlayers = getEligible(players);

        deckManager.shuffleDeck();
        int roundNum = 1;
        while (!eligiblePlayers.isEmpty()) {
            System.out.println(" Initiating round " + roundNum + ", eligible player count " + eligiblePlayers.size());
            RoundSimulator round = new RoundSimulator(deckManager, roundNum);
            round.playRound(eligiblePlayers, dealer);
            roundNum++;
            eligiblePlayers = getEligible(eligiblePlayers);
        }
        System.out.println("=========================================Game Finished=====================================");
    }

    private List<Player> getEligible(List<Player> players) {
        return players.stream().filter(player -> {
                    boolean eligible = true;
                    int balance = player.getWallet().getBalance();
                    if (balance < PLAYER_BET_AMOUNT.getValue()) {
                        System.out.println("\n\n\t\t\t\t Player out, as low balance : " + player + "\n\n");
                        eligible = false;
                    }
                    if ((balance - player.getWallet().getInitialMoney()) >= CardConstants.PLAYER_GAIN_LIMIT.getValue()) {
                        System.out.println("\n\n\t\t\t\t Player out, as Gain Amount limit reached : "
                                + CardConstants.PLAYER_GAIN_LIMIT.getValue() + " player " + player + "\n\n");
                        eligible = false;
                    }
                    return eligible;
                }
        ).collect(Collectors.toList());
    }

    private void validatePlayerCount(List<Player> players) {
        if (players.size() > CardConstants.MAX_PLAYER_COUNT.getValue()) {
            throw new ValidationFailureException(" Expected Max Players " + CardConstants.MAX_PLAYER_COUNT.getValue() + " Found " + players.size());
        }
    }

    private void validateInitialBalance(List<Player> players) {
        for (Player player : players) {
            if (player.getWallet().getInitialMoney() > PLAYER_MAX_INITIAL_AMOUNT.getValue() ||
                    player.getWallet().getInitialMoney() < PLAYER_BET_AMOUNT.getValue()) {
                throw new ValidationFailureException("Initial Amount out of valid range [" + PLAYER_BET_AMOUNT.getValue() + ", " + PLAYER_MAX_INITIAL_AMOUNT.getValue()
                        + "] for  " + player);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("hi user !!!");
        if (args == null || args.length == 0 || args.length % 3 != 0) {
            String msg = " expected 3 arguments per player but found " + Arrays.toString(args) + "\n" +
                    "sample command : \n java -jar blackjack.jar player1 20 c player2 30 a";

            System.out.println(msg);
            return;
        }

        System.out.println(" Received args " + Arrays.toString(args));
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < args.length; ) {
            String name = args[i];
            Integer initialMoney = Integer.valueOf(args[i + 1]);
            IActionStrategy actionStrategy;
            String strategyStr = args[i + 2];
            if (strategyStr.equalsIgnoreCase("c")) {
                actionStrategy = new ActionStrategy(CardConstants.CONSERVATIVE_PLAYER_HIT_MAX.getValue());
            } else {
                actionStrategy = new ActionStrategy(CardConstants.AGGRESSIVE_PLAYER_HIT_MAX.getValue());
            }
            Player p = new Player(name, PlayerType.PLAYER, initialMoney, actionStrategy);
            players.add(p);
            i += 3;
        }
        GameSimulator gs = new GameSimulator(new DeckManager());
        gs.startGame(players);
    }
}
