package com.e2open.falcon.framework.waiters;

import java.util.NoSuchElementException;

public interface WaitCondition {
    public boolean isSatisfied() throws NoSuchElementException;
}
