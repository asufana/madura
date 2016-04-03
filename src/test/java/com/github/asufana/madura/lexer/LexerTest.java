package com.github.asufana.madura.lexer;

import com.github.asufana.madura.token.IdToken;
import com.github.asufana.madura.token.NumToken;
import com.github.asufana.madura.token.Token;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LexerTest {
    
    private static int lineNumber = 1;
    
    @Test
    public void testNull() throws ParseException {
        List<Token> tokens = Lexer.toTokens(lineNumber, null);
        assertThat(tokens.isEmpty(), is(true));
    }
    
    @Test
    public void testEmpty() throws ParseException {
        List<Token> tokens = Lexer.toTokens(lineNumber, "");
        assertThat(tokens.isEmpty(), is(true));
    }

    @Test
    public void testTokensFromLine() throws ParseException {
        String line = "1 + 1 //comment";
        List<Token> tokens = Lexer.toTokens(lineNumber, line);

        assertThat(tokens,
                is(Arrays.asList(new NumToken(lineNumber, 1),
                        new IdToken(lineNumber, "+"),
                        new NumToken(lineNumber, 1),
                        new IdToken(lineNumber, Token.EOL))));
    }

    @Test
    public void testTokensFromLines() throws ParseException {
        String lines = "1 + 1 //comment\n" + "2 + 2";
        List<Token> tokens = Lexer.toTokens(lines);
        
        assertThat(tokens,
                is(Arrays.asList(
                        new NumToken(1, 1),
                        new IdToken(1, "+"),
                        new NumToken(1, 1),
                        new IdToken(1, Token.EOL),
                        new NumToken(2, 2),
                        new IdToken(2, "+"),
                        new NumToken(2, 2),
                        new IdToken(2, Token.EOL)
                )));
    }
    
}
