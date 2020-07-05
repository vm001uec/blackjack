package com.vineet.reward;

import com.vineet.CardConstants;
import com.vineet.model.Player;
import com.vineet.model.PlayerType;

public class RewardProcessor {

    public void processReward(Player winner, Player loser, WinType winType) {
        int rewardPercentage = CardConstants.NORMAL_REWARD_PERCENTAGE.getValue();


        int betAmount = 0;
        if (winner.getPlayerType().equals(PlayerType.PLAYER)) {
            betAmount = winner.getWallet().unreserveBetMoney();
            if (winType.equals(WinType.BLACK_JACK)) {
                rewardPercentage = CardConstants.BLACK_JACK_REWARD_PERCENTAGE.getValue();
            }
        } else {
            betAmount = loser.getWallet().unreserveBetMoney();
        }

        int rewardAmount = betAmount * rewardPercentage / 100;
        winner.getWallet().credit(rewardAmount);
        loser.getWallet().debit(rewardAmount);
    }

    public void processForTie(Player player){
        player.getWallet().unreserveBetMoney();
    }
}
