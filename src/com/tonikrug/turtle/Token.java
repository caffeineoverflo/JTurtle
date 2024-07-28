package com.tonikrug.turtle;

// Represents a lexical token
class Token {
    final TokenType type; // Type of the token
    final String lexeme; // The actual text of the token
    final Object literal; // Literal value (if any)
    final int line; // Line number where the token was found

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    // Convert the token to a string representation
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
