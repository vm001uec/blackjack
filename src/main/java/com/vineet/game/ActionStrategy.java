package com.vineet.game;

import com.vineet.model.Action;

public class ActionStrategy implements IActionStrategy{
    private int hitActionThreshold;

    public ActionStrategy(int hitActionThreshold) {
        this.hitActionThreshold = hitActionThreshold;
    }

    @Override
    public Action suggest(int score) {
        return (score <= hitActionThreshold) ? Action.HIT : Action.STAND;
    }
}
