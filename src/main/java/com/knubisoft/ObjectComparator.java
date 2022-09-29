package com.knubisoft;

public interface ObjectComparator<T> {
    void compare(T expected, T actual);
}
