package com.vineet;

public enum CardConstants {
    CARD_MIN_RANK(1),
    CARD_MAX_RANK(13),
    CONSERVATIVE_PLAYER_HIT_MAX(10),
    AGGRESSIVE_PLAYER_HIT_MAX(17),
    DEALER_PLAYER_HIT_MAX(17),
    PLAYER_GAIN_LIMIT(100),
    PLAYER_BET_AMOUNT(10),
    PLAYER_MAX_INITIAL_AMOUNT(1000),
    MAX_PLAYER_COUNT(10),
    ACE_RANK(1),
    KING_RANK(13),
    QUEEN_RANK(12),
    JACK_RANK(11),
    ACE_MAX_SCORE(11),
    FACE_CARD_SCORE(10),
    PLAYER_HIGHEST_SCORE(21),
    CARD_COUNT_FOR_BLACKJACK(2),
    ROUND_INITIAL_DEAL_COUNT(2),
    BLACK_JACK_REWARD_PERCENTAGE(150),
    NORMAL_REWARD_PERCENTAGE(100),
    ;


    private int value;
    CardConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
