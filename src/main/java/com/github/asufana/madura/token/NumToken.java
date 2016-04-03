package com.github.asufana.madura.token;

import lombok.Getter;

@Getter
public class NumToken extends Token {
    private int value;
    
    public NumToken(int lineNumber, int value) {
        super(lineNumber);
        this.value = value;
    }
    
    public boolean isNumber() {
        return true;
    }
    
    public String getText() {
        return Integer.toString(value);
    }
    
    public int getNumber() {
        return value;
    }
    
    public String toString() {
        return String.format("NumToken(%s, \"%s\")",
                             getLineNumber(),
                             getValue());
    }
}
