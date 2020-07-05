package com.vineet.model;

import com.vineet.CardConstants;

import java.util.*;

public class Deck {
    private static final List<Card> singleDeckOfCards = getSingleDeckOfCards();

    private List<Card> deck;
    private int currentIndex;

    public Deck() {
        this(Deck.singleDeckOfCards);
    }

    public Deck(Collection<Card> cards) {
        deck = new ArrayList<>(cards);
        currentIndex = 0;
    }

    private static List<Card> getSingleDeckOfCards() {
        List<Card> singleDeck = new ArrayList<>(Suite.values().length * (CardConstants.CARD_MAX_RANK.getValue() - CardConstants.CARD_MIN_RANK.getValue()));
        for (Suite suite : Suite.values()) {
            for (int i = CardConstants.CARD_MIN_RANK.getValue(); i <= CardConstants.CARD_MAX_RANK.getValue(); i++) {
                singleDeck.add(new Card(suite, i));
            }
        }
        return singleDeck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card popACard() {
        return deck.get(currentIndex++);
    }

    public boolean isEmpty() {
        return currentIndex >= deck.size();
    }
}
