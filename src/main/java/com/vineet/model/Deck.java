package com.vineet.model;

import com.vineet.CardConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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


//    private void init() {
//        int currentIndex = 0;
//        Card[] temp_deck = new Card[Suite.values().length * (CardConstants.CARD_MAX_RANK.getValue() - CardConstants.CARD_MIN_RANK.getValue())];
//        for (Suite suite : Suite.values()) {
//            for (int i = CardConstants.CARD_MIN_RANK.getValue(); i <= CardConstants.CARD_MAX_RANK.getValue(); i++) {
//                temp_deck[currentIndex] = new Card(suite, i);
//            }
//        }
//        deck = temp_deck;
//    }

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
        currentIndex++;
        return deck.get(currentIndex);
    }

    public boolean isEmpty() {
        return currentIndex >= deck.size();
    }
}
