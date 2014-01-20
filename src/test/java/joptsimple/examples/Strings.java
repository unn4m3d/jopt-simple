package joptsimple.examples;

import java.util.Iterator;

import static java.util.Arrays.*;

public final class Strings {
    public static String join( final char delimiter, final String... pieces ) {
        final StringBuilder builder = new StringBuilder();

        for ( final Iterator<String> iter = asList( pieces ).iterator(); iter.hasNext(); ) {
            builder.append( iter.next() );
            if ( iter.hasNext() )
                builder.append( delimiter );
        }

        return builder.toString();
    }
}
