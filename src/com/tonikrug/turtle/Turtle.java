package com.tonikrug.turtle;
import com.tonikrug.turtle.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Turtle {
    // Indicates if an error has occurred
    static boolean hadError = false;

    // Entry point of the application
    public static void main(String[] args) throws IOException {
        // If there are more than one argument, print usage and exit
        if (args.length > 1) {
            System.out.println("Usage: turtle [script]");
            System.exit(64);
        } else if (args.length == 1) {
            // Run the script from a file
            runFile(args[0]);
        } else {
            // Run the interactive prompt
            runPrompt();
        }
    }

    // Run the script from a file
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        // Exit with error code if an error occurred
        if (hadError) System.exit(65);
    }

    // Run the interactive prompt
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        // Continuously read and execute lines of input
        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break; // Exit on end of input
            run(line);
            hadError = false; // Reset error flag for next command
        }
    }

    // Run the interpreter with the given source code
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();
        // Stop if there was a syntax error.
        if (hadError) return;
        System.out.println(new AstPrinter().print(expression));
    }

    // Report an error at a specific line
    static void error(int line, String message) {
        report(line, "", message);
    }

    // Report an error with additional context
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true; // Set the error flag
    }

    static void error(Token token, String message){
        if (token.type == TokenType.EOF){
            report(token.line, " at end", message);
        } else {
            report(token.line,"at '" + token.lexeme + "'", message);
        }
    }
}
