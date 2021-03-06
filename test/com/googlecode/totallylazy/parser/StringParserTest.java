package com.googlecode.totallylazy.parser;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.googlecode.totallylazy.Segment.constructors.characters;
import static com.googlecode.totallylazy.Segment.constructors.emptySegment;
import static com.googlecode.totallylazy.Strings.UTF8;
import static com.googlecode.totallylazy.Strings.bytes;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static com.googlecode.totallylazy.parser.StringParser.string;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringParserTest {
    @Test
    public void canParseAString() throws Exception {
        Result<String> result = string("ABC").parse("ABC");
        assertThat(result.value(), is("ABC"));
        assertThat(result.remainder(), is(emptySegment(Character.class)));
    }

    @Test
    public void printsNiceMessage() throws Exception {
        Result<String> result = string("ABC").parse("DEF");
        assertThat(result.failure(), is(true));
        assertThat(result.message(), is("ABC expected, D encountered."));
    }

    @Test
    public void supportsRemainder() throws Exception {
        Result<String> result = string("ABC").parse("ABCDEF");
        assertThat(result.value(), is("ABC"));
        assertThat(result.remainder(), is(characters("DEF")));
    }

    @Test
    public void doesNotReadMoreThanItNeeds() throws Exception {
        InputStream stream = new ByteArrayInputStream(bytes("ABCDEF"));
        Reader reader = new InputStreamReader(stream, UTF8);
        Result<String> result = string("ABC").parse(reader);
        assertThat(result.value(), is("ABC"));
        char next = (char) reader.read();
        assertThat(next, is('D'));
    }
}