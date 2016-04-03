package com.github.asufana.madura.lexer;

import com.github.asufana.madura.token.IdToken;
import com.github.asufana.madura.token.NumToken;
import com.github.asufana.madura.token.StrToken;
import com.github.asufana.madura.token.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class Lexer {
    
    private static String regexPat = "\\s*"
            + "("
            + "(//.*)|"                                              //group(2):comment
            + "([0-9]+)|"                                            //group(3):number
            + "(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|"                //group(4):string
            + "[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct}" //group(1):id
            + ")?";
    private static final Pattern pattern = Pattern.compile(regexPat);

    public static List<Token> toTokens(String lines) throws ParseException {
        if(isEmpty(lines)){
            return Collections.<Token>emptyList();
        }
        ArrayList<Token> tokenList = new ArrayList<>();

        String[] lineArray = lines.split("\\n");
        for(int i = 0; i < lineArray.length; i++) {
            List<Token> tokens = toTokens(i + 1, lineArray[i]);
            tokenList.addAll(tokens);
        }
        return tokenList;
    }

    public static List<Token> toTokens(int lineNo, String line) throws ParseException {
        if(isEmpty(line)){
            return Collections.<Token>emptyList();
        }
        ArrayList<Token> tokenList = new ArrayList<>();

        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                toToken(lineNo, matcher).ifPresent(tkn -> tokenList.add(tkn));
                pos = matcher.end();
            }
            else {
                throw new ParseException("bad token at line " + lineNo);
            }
        }
        tokenList.add(new IdToken(lineNo, Token.EOL));
        return tokenList;
    }
    
    protected static Optional<Token> toToken(int lineNo, Matcher matcher) {
        String match = matcher.group(1);
        if (match == null) return Optional.empty(); //empty line
        if (matcher.group(2) != null) return Optional.empty(); //comment
        
        if (matcher.group(3) != null) {
            return of(new NumToken(lineNo, Integer.parseInt(match)));
        }
        else if (matcher.group(4) != null) {
            return of(new StrToken(lineNo, toStringLiteral(match)));
        }
        return of(new IdToken(lineNo, match));
    }
    
    protected static String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\') c = s.charAt(++i);
                else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
