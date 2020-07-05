package com.vineet.model;

import com.vineet.model.Card;
import com.vineet.model.Deck;
import com.vineet.model.Suite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DeckTest {

    @Test
    public void testSuccess() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suite.CLUB,1));
        cards.add(new Card(Suite.DIAMOND,2));
        cards.add(new Card(Suite.SPADE,11));
        cards.add(new Card(Suite.HEART,9));
        Deck deck = new Deck(cards);

        System.out.println(Arrays.toString(cards.toArray()));
        Assert.assertEquals(cards.get(0),deck.popACard());
        Assert.assertEquals(cards.get(1),deck.popACard());
        Assert.assertEquals(cards.get(2),deck.popACard());
        Assert.assertEquals(cards.get(3),deck.popACard());
    }
}
