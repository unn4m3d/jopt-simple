package joptsimple.examples;

import joptsimple.OptionParser;

public final class RequiredUnlessExample {
    public static void main( final String[] args ) {
        final OptionParser parser = new OptionParser();
        parser.accepts( "anonymous" );
        parser.accepts( "username" ).requiredUnless( "anonymous" ).withRequiredArg();
        parser.accepts( "password" ).requiredUnless( "anonymous" ).withRequiredArg();

        parser.parse( "--anonymous" );
    }
}
