package com.example.boltalp1.util;

public class Event<T> {
    private boolean mHasBeenHandled;
    private T mContent;

    public Event(T content) {
        mContent = content;
        mHasBeenHandled = false;
    }

    public T getContentIfNotHandled() {
        if (mHasBeenHandled) {
            return null;
        } else {
            mHasBeenHandled = true;
            return mContent;
        }
    }

    public T peekContent() {
        return mContent;
    }
}
