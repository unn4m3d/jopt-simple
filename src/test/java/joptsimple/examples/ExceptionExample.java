package joptsimple.examples;

import joptsimple.OptionParser;

public final class ExceptionExample {
    public static void main( final String[] args ) {
        final OptionParser parser = new OptionParser();

        parser.parse( "-x" );
    }
}
