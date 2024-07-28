package com.tonikrug.turtle;

import java.util.ArrayList;
import java.util.List;
import static com.tonikrug.turtle.TokenType.*;

class Scanner {
    private int start = 0; // Start position of the current lexeme
    private int current = 0; // Current position in the source code
    private int line = 1; // Current line number

    private final String source; // Source code to scan
    private final List<Token> tokens = new ArrayList<>(); // List of scanned tokens

    Scanner(String source) {
        this.source = source;
    }

    // Scan the source code and return a list of tokens
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line)); // Add end-of-file token
        return tokens;
    }

    // Scan the next token and add it to the list
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                } break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            case '"': string(); break;
            default:
                Turtle.error(line, "Unexpected character."); // Report unexpected character
                break;
        }
    }
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance(); }
        if (isAtEnd()) {
            Turtle.error(line, "Unterminated string.");
            return;
        }
        // The closing ".
        advance();
        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    // Check if the end of the source code is reached
    private boolean isAtEnd() {
        return current >= source.length();
    }

    // Advance to the next character and return the current one
    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    // Add a token without a literal value
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    // Add a token with a literal value
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current); // Get the lexeme text
        tokens.add(new Token(type, text, literal, line)); // Add the token to the list
    }
}
