package com.vineet.reward;

import com.vineet.CardConstants;
import com.vineet.game.ActionStrategy;
import com.vineet.model.Player;
import com.vineet.model.PlayerType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RewardProcessorTest {
    private RewardProcessor rewardProcessor= new RewardProcessor();

    @Test
    public void testprocessRewardBlackJack(){
        int initialMoney = 100;
        int betAmount =10;
        Player player = new Player("anyplayer", PlayerType.PLAYER, initialMoney, new ActionStrategy(10));
        Player dealer = new Player("anydealer", PlayerType.DEALER, 0, new ActionStrategy(17));

        player.getWallet().reserveToBet(betAmount);

        rewardProcessor.processReward(player,dealer,WinType.BLACK_JACK);
        int newBal = betAmount * CardConstants.BLACK_JACK_REWARD_PERCENTAGE.getValue()/100 + initialMoney;
        Assert.assertEquals(newBal,player.getWallet().getBalance());
    }

    @Test
    public void testprocessRewardWIN(){
        int initialMoney = 100;
        int betAmount =10;
        Player player = new Player("anyplayer", PlayerType.PLAYER, initialMoney, new ActionStrategy(10));
        Player dealer = new Player("anydealer", PlayerType.DEALER, 0, new ActionStrategy(17));

        player.getWallet().reserveToBet(betAmount);

        rewardProcessor.processReward(player,dealer,WinType.HIGH_SCORE);
        int newBal = betAmount + initialMoney;
        Assert.assertEquals(newBal,player.getWallet().getBalance());
    }

    @Test
    public void testprocessRewardLOSS(){
        int initialMoney = 100;
        int betAmount =10;
        Player player = new Player("anyplayer", PlayerType.PLAYER, initialMoney, new ActionStrategy(10));
        Player dealer = new Player("anydealer", PlayerType.DEALER, 0, new ActionStrategy(17));

        player.getWallet().reserveToBet(betAmount);

        rewardProcessor.processReward(dealer,player,WinType.HIGH_SCORE);
        int newBal = initialMoney-betAmount;
        Assert.assertEquals(newBal,player.getWallet().getBalance());
    }
}
