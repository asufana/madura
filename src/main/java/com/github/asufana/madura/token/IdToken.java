package com.github.asufana.madura.token;

import lombok.Getter;

@Getter
public class IdToken extends Token {
    private String value;
    
    public IdToken(int lineNumber, String value) {
        super(lineNumber);
        this.value = value;
    }
    
    public boolean isIdentifier() {
        return true;
    }
    
    public String getText() {
        return value;
    }
    
    public String toString() {
        return String.format("IdToken(%s, \"%s\")", getLineNumber(), getValue());
    }
}
