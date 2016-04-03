package com.github.asufana.madura.lexer;

import com.github.asufana.madura.token.Token;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.github.asufana.madura.lexer.Lexer.toTokens;

public class LexerService {

    private ArrayList<Token> queue = new ArrayList<>();

    private boolean hasMore;

    private LineNumberReader reader;

    public LexerService(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }
    
    public Token read() throws ParseException {
        if (fillQueue(0)) return queue.remove(0);
        else return Token.EOF;
    }
    
    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) return queue.get(i);
        else return Token.EOF;
    }
    
    private boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size())
            if (hasMore) readLine();
            else return false;
        return true;
    }
    
    protected void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        }
        catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber();

        List<Token> tokens = toTokens(lineNo, line);
        queue.addAll(tokens);
    }
}
