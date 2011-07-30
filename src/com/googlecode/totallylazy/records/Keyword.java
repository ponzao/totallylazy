package com.googlecode.totallylazy.records;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.GenericType;
import com.googlecode.totallylazy.Value;

public interface Keyword<T> extends Callable1<Record, T>, GenericType<T>, Value<String> {
    String value();

    // TODO Rename to 'as' somehow
    AliasedKeyword<T> alias(Keyword<T> keyword);
}