package com.vineet.game;

import com.vineet.CardConstants;
import com.vineet.model.*;
import com.vineet.reward.RewardProcessor;
import com.vineet.reward.WinType;

import java.util.*;
import java.util.stream.Collectors;

public class RoundSimulator {

    private Deck deck;
    private Set<Card> usedCards;
    private RewardProcessor rewardProcessor;
    private WinningRule winningRule;
    private int roundNum;

    public RoundSimulator(int roundNum) {
        this.deck = new Deck();
        deck.shuffle();
        usedCards = new HashSet<>();
        rewardProcessor = new RewardProcessor();
        winningRule = new WinningRule();
        this.roundNum = roundNum;

    }

    public void playRound(List<Player> players, Player dealer) {
        System.out.println("\n*************** Starting Round " + roundNum + " with Dealer " + dealer + ",\n players " + Arrays.toString(players.toArray()));
        Map<Player, Hand> playerHand = new HashMap<>(players.size());
        for (Player player : players) {
            player.getWallet().reserveToBet(CardConstants.PLAYER_BET_AMOUNT.getValue());
            playerHand.put(player, new Hand());
        }
        playerHand.put(dealer, new Hand());

        dealFirst2Cards(players, dealer, playerHand);
        players = checkProcessBlackJack(players, dealer, playerHand);

        for (Player player : players) {
            Hand hand = playerHand.get(player);
            while (player.getActionStrategy().suggest(hand.getScore()).equals(Action.HIT)) {
                hand.addCard(dealACard());
            }
        }
        Hand dealerHand = playerHand.get(dealer);

        while (dealer.getActionStrategy().suggest(dealerHand.getScore()).equals(Action.HIT)) {
            dealerHand.addCard(dealACard());
        }

        players.forEach(player -> {
            Outcome outcome = winningRule.apply(playerHand.get(player), playerHand.get(dealer));
            handlePlayerStand(player, dealer, playerHand, outcome, WinType.HIGH_SCORE);
        });

        System.out.println("\nDealer state " + dealer + " , " + playerHand.get(dealer).getCards());
        usedCards.addAll(playerHand.get(dealer).getCards());
        playerHand.remove(dealer);

        System.out.println("======================= Round " + roundNum + " Finished.=====================================");
    }

    private void dealFirst2Cards(List<Player> players, Player dealer, Map<Player, Hand> playerHand) {
        for (int i = 0; i < CardConstants.ROUND_INITIAL_DEAL_COUNT.getValue(); i++) {
            for (Player player : players) {
                playerHand.get(player).addCard(dealACard());
            }
            playerHand.get(dealer).addCard(dealACard());
        }
    }

    private Card dealACard() {
        if (deck.isEmpty()) {
            deck = new Deck(usedCards);
            deck.shuffle();
            usedCards.clear();
        }
        return deck.popACard();
    }

    private List<Player> checkProcessBlackJack(List<Player> players, Player dealer, Map<Player, Hand> playerHand) {
        boolean dealerBJ = playerHand.get(dealer).isBlackJack();
        return players.stream().filter(player -> {
            if (playerHand.get(player).isBlackJack() || dealerBJ) {
                Outcome outcome = winningRule.apply(playerHand.get(player), playerHand.get(dealer));
                handlePlayerStand(player, dealer, playerHand, outcome, WinType.BLACK_JACK);
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    private void handlePlayerStand(Player player, Player dealer, Map<Player, Hand> playerHand, Outcome outcome, WinType winType) {
        if (outcome.equals(Outcome.WIN)) {
            rewardProcessor.processReward(player, dealer, winType);
        } else if (outcome.equals(Outcome.LOOSE)) {
            rewardProcessor.processReward(dealer, player, winType);
        }else{
            rewardProcessor.processForTie(player);
        }
        System.out.println("\nRound " + roundNum + " : " + outcome + " : "+(winType.equals(WinType.BLACK_JACK)?" *BlackJack*":"") +"\n" + player + " "+playerHand.get(player) + " \n"
                + dealer + playerHand.get(dealer));
        usedCards.addAll(playerHand.get(player).getCards());
        playerHand.remove(player);
    }
}
