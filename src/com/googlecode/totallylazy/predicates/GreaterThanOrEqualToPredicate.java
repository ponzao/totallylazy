package com.googlecode.totallylazy.predicates;

public class GreaterThanOrEqualToPredicate<T extends Comparable<? super T>> extends LogicalPredicate<T> implements GreaterThanOrEqualTo<T> {
    private final T comparable;

    public GreaterThanOrEqualToPredicate(T comparable) {
        this.comparable = comparable;
    }

    public boolean matches(T other) {
        return other.compareTo(comparable) >= 0;
    }

    public T value() {
        return comparable;
    }
}
