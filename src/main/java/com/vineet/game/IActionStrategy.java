package com.vineet.game;

import com.vineet.model.Action;

public interface IActionStrategy {
    Action suggest(int score);
}
