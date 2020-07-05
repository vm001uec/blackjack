package com.vineet.model;

import com.vineet.CardConstants;

import java.util.Objects;

public class Card {
    private Suite suit;
    private int rank;

    public Card(Suite suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suite getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        String prefix = (rank == CardConstants.ACE_RANK.getValue()) ? "Ace" :
                ( rank < CardConstants.JACK_RANK.getValue()) ? String.valueOf(rank) :
                        (rank == CardConstants.JACK_RANK.getValue()) ? "Jack" :
                                (rank == CardConstants.QUEEN_RANK.getValue()) ? "Queen" :
                                (rank == CardConstants.KING_RANK.getValue()) ? "King" :
                                        "Unknown";
        return prefix + " of " + suit.toString();
    }

}
