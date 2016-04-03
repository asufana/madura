package com.github.asufana.madura.token;

import com.github.asufana.madura.MaduraException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@AllArgsConstructor
public abstract class Token {
    
    public static final Token EOF = new Token(-1) {};
    public static final String EOL = "\\n";
    
    private int lineNumber;
    
    public boolean isIdentifier() {
        return false;
    }
    
    public boolean isNumber() {
        return false;
    }
    
    public boolean isString() {
        return false;
    }
    
    public int getNumber() {
        throw new MaduraException("Not number token.");
    }
    
    public String getText() {
        return "";
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, other);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
