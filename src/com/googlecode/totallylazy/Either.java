package com.googlecode.totallylazy;

import com.googlecode.totallylazy.functions.Function1;
import com.googlecode.totallylazy.functions.Function2;
import com.googlecode.totallylazy.functions.Functions;
import com.googlecode.totallylazy.predicates.LogicalPredicate;

import java.util.concurrent.Callable;

public abstract class Either<L, R> implements Iterable<R>, Value<Object>, Functor<R>, Applicative<R>, Monad<R>, Foldable<R> {
    public static <L, R> Either<L, R> right(R value) {
        return Right.right(value);
    }

    public static <L, R> Either<L, R> right(Class<L> leftType, R value) {
        return Right.right(value);
    }

    public static <L, R> Either<L, R> left(L value) {
        return Left.left(value);
    }

    public static <L, R> Either<L, R> left(L value, Class<R> rightType) {
        return Left.left(value);
    }

    public static <R> Either<Exception, R> either(Callable<? extends R> callable) {
        try {
            return Either.right(callable.call());
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    public abstract boolean isRight();

    public abstract boolean isLeft();

    public abstract R right();

    public abstract L left();

    public abstract Either<R, L> flip();

    public abstract <S> S fold(final S seed, final Function2<? super S, ? super L, ? extends S> left, final Function2<? super S, ? super R, ? extends S> right);

    public abstract <S> S map(final Function1<? super L, S> left, final Function1<? super R, ? extends S> right);

    @Override
    public abstract <S> Either<L, S> map(Function1<? super R, ? extends S> callable);

    public abstract <S> Either<S, R> mapLeft(Function1<? super L, ? extends S> callable);

    public abstract <S> Either<L, S> flatMap(Function1<? super R, ? extends Either<L, S>> callable);

    public static <L, R> Either<L, R> flatten(final Either<L, Either<L, R>> either) {
        return either.flatMap(Either.<L, R>identity());
    }

    public static <L, R> Function1<Either<L, R>, Either<L, R>> identity(Class<L> lClass, Class<R> rClass) {
        return identity();
    }

    public static <L, R> Function1<Either<L, R>, Either<L, R>> identity() { return Functions.identity(); }

    public abstract Object value();

    public <Ro> Either<L, Ro> applicate(Either<L, ? extends Function1<? super R, ? extends Ro>> applicator) {
        return applicate(applicator, this);
    }

    public static <L, Ri, Ro> Either<L, Ro> applicate(Either<L, ? extends Function1<? super Ri, ? extends Ro>> applicator, Either<L, ? extends Ri> value) {
        if (applicator.isLeft()) return left(applicator.left());
        return value.map(applicator.right());
    }

    public abstract Option<L> leftOption();

    public abstract Option<R> rightOption();


    public static class predicates {
        public static LogicalPredicate<Either<?, ?>> left = new LogicalPredicate<Either<?, ?>>() {
            @Override
            public boolean matches(Either<?, ?> other) {
                return other.isLeft();
            }
        };

        public static LogicalPredicate<Either<?, ?>> right = new LogicalPredicate<Either<?, ?>>() {
            @Override
            public boolean matches(Either<?, ?> other) {
                return other.isRight();
            }
        };
    }

    public static class functions {
        public static <L> Function1<Either<? extends L, ?>, L> left() {
            return either -> either.left();
        }

        public static <R> Function1<Either<?, ? extends R>, R> right() {
            return either -> either.right();
        }

        public static <L> Function1<Either<? extends L, ?>, Option<? extends L>> leftOption() {
            return Either::leftOption;
        }

        public static <R> Function1<Either<?, ? extends R>, Option<? extends R>> rightOption() {
            return Either::rightOption;
        }

        public static <L, R> Function1<L, Either<L, R>> asLeft() {
            return Either::left;
        }

        public static <L, R> Function1<R, Either<L, R>> asRight() {
            return Either::right;
        }

        public static <L, R> Function1<Either<L, R>, Either<R, L>> flip() {
            return Either::flip;
        }


    }
}
