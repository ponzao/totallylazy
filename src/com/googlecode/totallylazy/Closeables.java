package com.googlecode.totallylazy;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.googlecode.totallylazy.Callers.call;

public class Closeables {
    public static <T extends Closeable, R> Function1<T, R> closeAfter(final Callable1<? super T, R> callable) {
        return new Function1<T, R>() {
            @Override
            public R call(T t) throws Exception {
                return using(t, callable);
            }
        };
    }

    public static <T> T safeClose(final T t) {
        try {
            close(t);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T extends Closeable> T safeClose(final T t) {
        try {
            close(t);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T close(final T t) {
        if (t == null) {
            return t;
        }

        final Option<Method> close = Methods.method(t, "close");
        if (close.isEmpty()) {
            return t;
        }

        Methods.invoke(close.get(), t);
        return t;
    }


    public static <T extends Closeable> T close(final T t) {
        if (t == null) {
            return t;
        }
        try {
            t.close();
        } catch (IOException e) {
            throw LazyException.lazyException(e);
        }
        return t;
    }

    public static <T extends Closeable, R> R using(T t, Callable1<? super T, ? extends R> callable) {
        try {
            return call(callable, t);
        } finally {
            call(close(), t);
        }
    }

    public static <A extends Closeable, B extends Closeable, R> R using(A a, B b, Callable2<? super A, ? super B, ? extends R> callable) {
        try {
            return call(callable, a, b);
        } finally {
            try {
                call(close(), a);
            } finally {
                call(close(), b);
            }
        }
    }

    public static <T, R> R using(T instanceWithCloseMethod, Callable1<? super T, ? extends R> callable) {
        try {
            return call(callable, instanceWithCloseMethod);
        } finally {
            call(reflectiveClose(), instanceWithCloseMethod);
        }
    }

    public static <T> Block<T> reflectiveClose() {
        return new Block<T>() {
            @Override
            protected void execute(T instanceWithCloseMethod) throws Exception {
                close(instanceWithCloseMethod);
            }
        };
    }

    public static Block<Closeable> close() {
        return new Block<Closeable>() {
            @Override
            protected void execute(Closeable closeable) throws Exception {
                close(closeable);
            }
        };
    }

    public static Block<Closeable> safeClose() {
        return new Block<Closeable>() {
            @Override
            protected void execute(Closeable closeable) throws Exception {
                safeClose(closeable);
            }
        };
    }
}