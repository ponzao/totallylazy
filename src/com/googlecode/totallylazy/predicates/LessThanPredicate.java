package com.googlecode.totallylazy.predicates;

public class LessThanPredicate<T extends Comparable<? super T>> extends LogicalPredicate<T> implements LessThan<T> {
    private final T comparable;

    private LessThanPredicate(T comparable) {
        this.comparable = comparable;
    }

    public static <T extends Comparable<? super T>> LessThanPredicate<T> lessThan(T comparable) {
        return new LessThanPredicate<T>(comparable);
    }

    public boolean matches(T other) {
        return other.compareTo(comparable) < 0;
    }

    public T value() {
        return comparable;
    }
}