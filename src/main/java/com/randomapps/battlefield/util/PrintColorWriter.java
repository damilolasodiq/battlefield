package com.randomapps.battlefield.util;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PrintColorWriter extends PrintWriter {

    private static final String ANSI_RESET = "\u001B[0m";
    public static PrintColorWriter out;

    private PrintColorWriter() {
        super(System.out);
    }

    public PrintColorWriter(PrintStream out) throws UnsupportedEncodingException {
        super(new OutputStreamWriter(out, "UTF-8"), true);
    }

    public static PrintColorWriter getInstance() {
        if (out == null) {
            out = new PrintColorWriter();
        }
        return out;
    }

    public void println(PrintColor color, String string) {
        if (!GameHelper.isWindowsOS()) {
            print(color.getAnsiColor());
            print(string);
            println(ANSI_RESET);
            flush();
        } else {
            println(string);
        }
    }

    public void printf(PrintColor color, String string) {
        if (!GameHelper.isWindowsOS()) {
            print(color.getAnsiColor());
            print(string);
            print(ANSI_RESET);
            flush();
        } else {
            print(string);
        }
    }

    public void green(String string) {
        println(PrintColor.GREEN, string);
    }

    public void red(String string) {
        println(PrintColor.RED, string);
    }
}
