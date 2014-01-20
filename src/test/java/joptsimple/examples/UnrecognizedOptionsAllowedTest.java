package joptsimple.examples;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import static java.util.Arrays.*;
import static org.junit.Assert.*;

public class UnrecognizedOptionsAllowedTest {
    @Test
    public void acceptsLongOptions() {
        final OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.accepts( "f" );

        final OptionSet options = parser.parse( "-f", "-d" );

        assertTrue( options.has( "f" ) );
        assertFalse( options.has( "d" ) );
        assertEquals( asList( "-d" ), options.nonOptionArguments() );
    }
}
