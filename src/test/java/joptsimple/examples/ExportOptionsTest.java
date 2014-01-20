package joptsimple.examples;

import com.google.common.base.Joiner;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ExportOptionsTest {
    private static Properties asProperties( final OptionSet options, final String prefix ) {
        final Properties properties = new Properties();
        for ( final Entry<OptionSpec<?>, List<?>> entry : options.asMap().entrySet() ) {
            final OptionSpec<?> spec = entry.getKey();
            properties.setProperty( asPropertyKey( prefix, spec ),
                asPropertValue( entry.getValue(), options.has( spec ) ) );
        }
        return properties;
    }

    private static String asPropertyKey( final String prefix, final OptionSpec<?> spec ) {
        final Collection<String> flags = spec.options();
        for ( final String flag : flags )
            if ( 1 < flag.length() )
                return null == prefix ? flag : ( prefix + '.' + flag );
        throw new IllegalArgumentException( "No usable non-short flag: " + flags );
    }

    private static String asPropertValue( final List<?> values, final boolean present ) {
        // Simple flags have no values; treat presence/absence as true/false
        return values.isEmpty() ? String.valueOf( present ) : Joiner.on( "," ).join( values );
    }

    @Test
    public void allowsExportOfOptions() {
        final Properties expected = new Properties();
        expected.setProperty( "rice.count", "3" );
        // Cannot check path as string directly - Windows flips the leading slash
        expected.setProperty( "rice.output-dir", new File( "/tmp" ).toString() );
        expected.setProperty( "rice.fun", "false" );
        expected.setProperty( "rice.verbose", "true" );

        final OptionParser parser = new OptionParser();
        final OptionSpec<Integer> count = parser.accepts( "count" ).withRequiredArg().ofType( Integer.class );
        final OptionSpec<File> outputDir = parser.accepts( "output-dir" ).withOptionalArg().ofType( File.class );
        final OptionSpec<Void> verbose = parser.accepts( "verbose" );
        final OptionSpec<Void> fun = parser.accepts( "fun" );
        final OptionSpec<File> files = parser.nonOptions().ofType( File.class );

        final OptionSet options = parser.parse( "--count", "3", "--output-dir", "/tmp", "--verbose", "a.txt", "b.txt" );

        assertEquals( expected, asProperties( options, "rice" ) );
    }
}
