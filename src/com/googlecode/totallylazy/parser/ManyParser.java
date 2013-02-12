package com.googlecode.totallylazy.parser;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Segment;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.Sequences;

import static com.googlecode.totallylazy.Functions.returns;
import static com.googlecode.totallylazy.Unchecked.cast;

public class ManyParser<A> extends Parser<Sequence<A>> {
    private final Parser<A> parser;

    private ManyParser(Parse<? extends A> parse) {
        this.parser = Parsers.parser(parse);
    }

    public static <A> ManyParser<A> many(Parse<? extends A> parser) {
        return new ManyParser<A>(parser);
    }

    @Override
    public String toString() {
        return String.format("many %s", parser);
    }

    public Result<Sequence<A>> parse(Segment<Character> sequence) throws Exception {
        return parser.then(returns(this)).
                map(ManyParser.<A, Sequence<A>>cons()).
                or(ReturnsParser.returns(Sequences.<A>empty())).
                parse(sequence);
    }

    public static <A, S extends Segment<A>> Callable1<Pair<A, S>, S> cons() {
        return new Callable1<Pair<A, S>, S>() {
            @Override
            public S call(Pair<A, S> pair) throws Exception {
                return cast(pair.second().cons(pair.first()));
            }
        };
    }
}