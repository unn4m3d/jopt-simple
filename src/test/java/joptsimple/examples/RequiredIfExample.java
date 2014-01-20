package joptsimple.examples;

import joptsimple.OptionParser;

public final class RequiredIfExample {
    public static void main( final String[] args ) {
        final OptionParser parser = new OptionParser();
        parser.accepts( "ftp" );
        parser.accepts( "username" ).requiredIf( "ftp" ).withRequiredArg();
        parser.accepts( "password" ).requiredIf( "ftp" ).withRequiredArg();

        parser.parse( "--ftp" );
    }
}
