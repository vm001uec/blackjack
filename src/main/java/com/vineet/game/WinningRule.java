package com.vineet.game;

import com.vineet.CardConstants;
import com.vineet.model.Hand;
import com.vineet.model.Outcome;

public class WinningRule {
    public boolean isBlackJack(Hand hand) {
        return hand.getScore() == CardConstants.PLAYER_HIGHEST_SCORE.getValue()
                && hand.getCards().size() == CardConstants.CARD_COUNT_FOR_BLACKJACK.getValue();
    }

    public Outcome apply(Hand playerHand, Hand dealerHand) {
        Outcome outcome;
        if (playerHand.getScore() > dealerHand.getScore()) {
            outcome = Outcome.WIN;
        } else if (dealerHand.getScore() > playerHand.getScore()) {
            outcome = Outcome.LOOSE;
        } else {
            outcome = Outcome.TIE;
        }
        return outcome;
    }

}
