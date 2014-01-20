package joptsimple.examples;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortOptionsTest {
    @Test
    public void supportsShortOptions() {
        final OptionParser parser = new OptionParser( "aB?*." );

        final OptionSet options = parser.parse( "-a", "-B", "-?" );

        assertTrue( options.has( "a" ) );
        assertTrue( options.has( "B" ) );
        assertTrue( options.has( "?" ) );
        assertFalse( options.has( "." ) );
    }
}
