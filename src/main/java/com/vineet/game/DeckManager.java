package com.vineet.game;

import com.vineet.model.Card;
import com.vineet.model.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeckManager {
    private Deck deck;
    private List<Card> usedCards;


    public DeckManager() {
        this(new Deck());
    }

    public DeckManager(Deck deck) {
        this.deck = deck;
        usedCards = new ArrayList<>();
    }

    public void addUsedCard(List<Card> cards){
        usedCards.addAll(cards);
    }

    public void shuffleDeck(){
        deck.shuffle();
    }
    public Card dealACard() {
        if (deck.isEmpty()) {
            deck = new Deck(usedCards);
            deck.shuffle();
            usedCards.clear();
        }
        return deck.popACard();
    }
}
