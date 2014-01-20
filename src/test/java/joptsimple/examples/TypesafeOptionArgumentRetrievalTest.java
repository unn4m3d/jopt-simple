package joptsimple.examples;

import java.io.File;

import static java.util.Arrays.*;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypesafeOptionArgumentRetrievalTest {
    @Test
    public void allowsTypesafeRetrievalOfOptionArguments() {
        final OptionParser parser = new OptionParser();
        final OptionSpec<Integer> count = parser.accepts( "count" ).withRequiredArg().ofType( Integer.class );
        final OptionSpec<File> outputDir = parser.accepts( "output-dir" ).withOptionalArg().ofType( File.class );
        final OptionSpec<Void> verbose = parser.accepts( "verbose" );
        final OptionSpec<File> files = parser.nonOptions().ofType( File.class );

        final OptionSet options = parser.parse( "--count", "3", "--output-dir", "/tmp", "--verbose", "a.txt", "b.txt" );

        assertTrue( options.has( verbose ) );

        assertTrue( options.has( count ) );
        assertTrue( options.hasArgument( count ) );
        final Integer expectedCount = 3;
        assertEquals( expectedCount, options.valueOf( count ) );
        assertEquals( expectedCount, count.value( options ) );
        assertEquals( asList( expectedCount ), options.valuesOf( count ) );
        assertEquals( asList( expectedCount ), count.values( options ) );
        assertEquals( asList( new File( "a.txt" ), new File( "b.txt" ) ), options.valuesOf( files ) );

        assertTrue( options.has( outputDir ) );
        assertTrue( options.hasArgument( outputDir ) );
        final File expectedFile = new File( "/tmp" );
        assertEquals( expectedFile, options.valueOf( outputDir ) );
        assertEquals( expectedFile, outputDir.value( options ) );
        assertEquals( asList( expectedFile ), options.valuesOf( outputDir ) );
        assertEquals( asList( expectedFile ), outputDir.values( options ) );
        assertEquals( asList( new File( "a.txt" ), new File( "b.txt" ) ), files.values( options ) );
    }
}
