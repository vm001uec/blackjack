package com.vineet.game;

import com.vineet.CardConstants;
import com.vineet.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class RoundSimulatorTest {

    @Mock
    private DeckManager deckManager;

    @InjectMocks
    private RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);

    // Both Players score < 21 but more than dealer
    @Test
    public void testPlayRound2PlayersWin() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 9));
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 7));
        dealerHand.add(new Card(Suite.DIAMOND, 9));
        dealerHand.add(new Card(Suite.DIAMOND, 2));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1+bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer-bet*players.size(),dealer.getWallet().getBalance());

    }

    // Both Players score < 21 but more than dealer. Dealer has Ace but ace is counted as 1.
    @Test
    public void testPlayRound2PlayersWinDealerAce() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 9));
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 7));
        dealerHand.add(new Card(Suite.DIAMOND, 9));
        dealerHand.add(new Card(Suite.DIAMOND, 1));  //ace value =1 here
        dealerHand.add(new Card(Suite.DIAMOND, 1));  //ace value =1 here

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));
        customDeck.add(dealerHand.get(3));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1+bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer-bet*players.size(),dealer.getWallet().getBalance());

    }

    // Both Players score < 21 but more than dealer. Player has Ace but is counted as 1.
    @Test
    public void testPlayRound2PlayersWinPlayerAceAs1() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(18));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(18));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 5));
        player1Hand.add(new Card(Suite.CLUB, 10));
        player1Hand.add(new Card(Suite.CLUB, 1)); //Ace =1 here
        player1Hand.add(new Card(Suite.CLUB, 4));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 4));
        player2Hand.add(new Card(Suite.SPADE, 11));
        player2Hand.add(new Card(Suite.SPADE, 5));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 7));
        dealerHand.add(new Card(Suite.DIAMOND, 9));
        dealerHand.add(new Card(Suite.DIAMOND, 2));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(player1Hand.get(2));
        customDeck.add(player1Hand.get(3));
        customDeck.add(player2Hand.get(2));
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1+bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer-bet*players.size(),dealer.getWallet().getBalance());

    }

    // Both Players score < 21 but score is same as dealer leading to Tie.
    @Test
    public void testPlayRound2PlayersTie() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 9));
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 7));
        dealerHand.add(new Card(Suite.DIAMOND, 9));
        dealerHand.add(new Card(Suite.DIAMOND, 3));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        Assert.assertEquals(initialMoneyPlayer1,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer,dealer.getWallet().getBalance());

    }

    // Both Players hit a black jack and dealer does not.
    @Test
    public void testPlayRound2PlayersBlackJackWin() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 1)); //ace value =11 here
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 1)); //ace value =11 here
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 7));
        dealerHand.add(new Card(Suite.DIAMOND, 9));
        dealerHand.add(new Card(Suite.DIAMOND, 3));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int gain = CardConstants.PLAYER_BET_AMOUNT.getValue()*CardConstants.BLACK_JACK_REWARD_PERCENTAGE.getValue()/100;
        Assert.assertEquals(initialMoneyPlayer1+gain,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+gain,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer-gain*players.size(),dealer.getWallet().getBalance());
    }

    //Both Players and Dealer hit blackjack leading to Tie.
    @Test
    public void testPlayRound2PlayersBlackJackTie() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 1));
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 1));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 1));
        dealerHand.add(new Card(Suite.DIAMOND, 10));
        dealerHand.add(new Card(Suite.DIAMOND, 3));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int gain = CardConstants.PLAYER_BET_AMOUNT.getValue()*CardConstants.BLACK_JACK_REWARD_PERCENTAGE.getValue()/100;
        Assert.assertEquals(initialMoneyPlayer1,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer,dealer.getWallet().getBalance());
    }

    // One player wins and other loses. score < 21 and score < dealer.
    @Test
    public void testPlayRound2PlayersWinLoose() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(10));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 4));
        player1Hand.add(new Card(Suite.CLUB, 10));

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 5));
        dealerHand.add(new Card(Suite.DIAMOND, 10));
        dealerHand.add(new Card(Suite.DIAMOND, 3));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1-bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer,dealer.getWallet().getBalance());
    }

    //player score crosses 21
    @Test
    public void testPlayRound2PlayersWinLooseScoreOver21() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(18));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 4));
        player1Hand.add(new Card(Suite.CLUB, 10));
        player1Hand.add(new Card(Suite.CLUB, 10));  // score above 21

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 5));
        dealerHand.add(new Card(Suite.DIAMOND, 10));
        dealerHand.add(new Card(Suite.DIAMOND, 3));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(player1Hand.get(2));
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1-bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer,dealer.getWallet().getBalance());
    }

    //One player and One dealer score over 21
    @Test
    public void testPlayRound2PlayersWinLooseAsPlayerDealerScoreOver21() {
        int initialMoneyPlayer1 = 20;
        int initialMoneyPlayer2 = 30;
        int initialMoneydealer = 100;
        Player dealer = new Player("anydealer", PlayerType.DEALER, initialMoneydealer, new ActionStrategy(17));
        Player player1 = new Player("anyplayer", PlayerType.PLAYER, initialMoneyPlayer1, new ActionStrategy(18));
        Player player2 = new Player("anyplayer2", PlayerType.PLAYER, initialMoneyPlayer2, new ActionStrategy(10));

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Card> player1Hand = new ArrayList<>();
        player1Hand.add(new Card(Suite.CLUB, 4));
        player1Hand.add(new Card(Suite.CLUB, 10));
        player1Hand.add(new Card(Suite.CLUB, 10));  // score above 21

        List<Card> player2Hand = new ArrayList<>();
        player2Hand.add(new Card(Suite.SPADE, 9));
        player2Hand.add(new Card(Suite.SPADE, 10));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suite.DIAMOND, 5));
        dealerHand.add(new Card(Suite.DIAMOND, 10));
        dealerHand.add(new Card(Suite.DIAMOND, 12));

        List<Card> customDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            customDeck.add(player1Hand.get(i));
            customDeck.add(player2Hand.get(i));
            customDeck.add(dealerHand.get(i));
        }
        customDeck.add(player1Hand.get(2));
        customDeck.add(dealerHand.get(2));

        DeckManager deckManager = new DeckManager(new Deck(customDeck));
        RoundSimulator roundSimulator = new RoundSimulator(deckManager, 1);
        roundSimulator.playRound(players, dealer);

        int bet = CardConstants.PLAYER_BET_AMOUNT.getValue();
        Assert.assertEquals(initialMoneyPlayer1-bet,player1.getWallet().getBalance());
        Assert.assertEquals(initialMoneyPlayer2+bet,player2.getWallet().getBalance());
        Assert.assertEquals(initialMoneydealer,dealer.getWallet().getBalance());
    }
}
