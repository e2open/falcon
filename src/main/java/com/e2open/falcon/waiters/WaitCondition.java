package com.e2open.falcon.waiters;

import java.util.NoSuchElementException;

public interface WaitCondition {
    public boolean isSatisfied() throws NoSuchElementException;
}
