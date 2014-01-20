package joptsimple.examples;

import java.util.List;

import static java.util.Arrays.*;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionSynonymTest {
    @Test
    public void supportsOptionSynonyms() {
        final OptionParser parser = new OptionParser();
        final List<String> synonyms = asList( "message", "blurb", "greeting" );
        parser.acceptsAll( synonyms ).withRequiredArg();
        final String expectedMessage = "Hello";

        final OptionSet options = parser.parse( "--message", expectedMessage );

        for ( final String each : synonyms ) {
            assertTrue( each, options.has( each ) );
            assertTrue( each, options.hasArgument( each ) );
            assertEquals( each, expectedMessage, options.valueOf( each ) );
            assertEquals( each, asList( expectedMessage ), options.valuesOf( each ) );
        }
    }
}
