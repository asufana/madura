package com.github.asufana.madura.token;

import lombok.Getter;

@Getter
public class StrToken extends Token {
    private String value;
    
    public StrToken(int lineNumber, String value) {
        super(lineNumber);
        this.value = value;
    }
    
    public boolean isString() {
        return true;
    }
    
    public String getText() {
        return value;
    }
    
    public String toString() {
        return String.format("StrToken(%s, \"%s\")",
                             getLineNumber(),
                             getValue());
    }
}
