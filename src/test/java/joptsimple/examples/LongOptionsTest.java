package joptsimple.examples;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongOptionsTest {
    @Test
    public void acceptsLongOptions() {
        final OptionParser parser = new OptionParser();
        parser.accepts( "flag" );
        parser.accepts( "verbose" );

        final OptionSet options = parser.parse( "--flag" );

        assertTrue( options.has( "flag" ) );
        assertFalse( options.has( "verbose" ) );
    }
}
