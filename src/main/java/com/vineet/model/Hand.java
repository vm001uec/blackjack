package com.vineet.model;

import com.vineet.CardConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private int aceCount;
    private Score score;

    public Hand() {
        cards = new ArrayList<>();
        aceCount = 0;
        score = new Score();
    }

    public void addCard(Card card) {
        if (card.getRank() == CardConstants.ACE_RANK.getValue()) {
            aceCount++;
        }
        cards.add(card);
        score.addScore(card);
    }

    public boolean hasAnAce(){
        return aceCount>0;
    }
    public int getScore() {
        int score = this.score.getScore();
        if (aceCount > 0) {
            int higherScore = score + CardConstants.ACE_MAX_SCORE.getValue() - CardConstants.ACE_RANK.getValue();
            if (higherScore <= CardConstants.PLAYER_HIGHEST_SCORE.getValue()) {
                score = higherScore;
            }
        }
        return score;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isBlackJack() {
        return aceCount > 0 && getScore() == CardConstants.PLAYER_HIGHEST_SCORE.getValue()
                && cards.size() == CardConstants.CARD_COUNT_FOR_BLACKJACK.getValue();
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + Arrays.toString(cards.toArray()) +
                ", aceCount=" + aceCount +
                ", Base_score=" + score.getScore() +
                '}';
    }

    private class Score {
        private int score;

        public void addScore(Card card) {
            if (card.getRank() >= CardConstants.JACK_RANK.getValue()) {
                score += CardConstants.FACE_CARD_SCORE.getValue();
            } else {
                score += card.getRank();
            }
        }

        public int getScore() {
            return score;
        }
    }
}
