/*
 The MIT License

 Copyright (c) 2004-2014 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package joptsimple;

import joptsimple.internal.Reflection;
import joptsimple.internal.ReflectionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static joptsimple.internal.Strings.EMPTY;

/**
 * @param <V> represents the type of the arguments this option accepts
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
abstract class AbstractOptionSpec<V> implements OptionSpec<V>, OptionDescriptor {
    private final List<String> options = new ArrayList<String>();
    private final String description;
    private boolean forHelp;

    AbstractOptionSpec(final String option) {
        this( singletonList( option ), EMPTY );
    }

    AbstractOptionSpec(final Collection<String> options, final String description) {
        arrangeOptions( options );

        this.description = description;
    }

    public final Collection<String> options() {
        return unmodifiableList( options );
    }

    public final List<V> values( final OptionSet detectedOptions ) {
        return detectedOptions.valuesOf( this );
    }

    public final V value( final OptionSet detectedOptions ) {
        return detectedOptions.valueOf( this );
    }

    public String description() {
        return description;
    }

    public final AbstractOptionSpec<V> forHelp() {
        forHelp = true;
        return this;
    }

    public final boolean isForHelp() {
        return forHelp;
    }

    public boolean representsNonOptions() {
        return false;
    }

    V convertWith(final ValueConverter<V> converter, final String argument) {
        try {
            return Reflection.convertWith( converter, argument );
        }
        catch ( final ReflectionException ex ) {
            throw new OptionArgumentConversionException( options(), argument, ex );
        }
        catch ( final ValueConversionException ex ) {
            throw new OptionArgumentConversionException( options(), argument, ex );
        }
    }

    String argumentTypeIndicatorFrom(final ValueConverter<V> converter) {
        if ( converter == null )
            return null;

        final String pattern = converter.valuePattern();
        return pattern == null ? converter.valueType().getName() : pattern;
    }

    abstract void handleOption( OptionParser parser, ArgumentList arguments, OptionSet detectedOptions,
        String detectedArgument );

    private void arrangeOptions( final Collection<String> unarranged ) {
        if ( unarranged.size() == 1 ) {
            options.addAll( unarranged );
            return;
        }

        final List<String> shortOptions = new ArrayList<String>();
        final List<String> longOptions = new ArrayList<String>();

        for ( final String each : unarranged ) {
            if ( each.length() == 1 )
                shortOptions.add( each );
            else
                longOptions.add( each );
        }

        sort( shortOptions );
        sort( longOptions );

        options.addAll( shortOptions );
        options.addAll( longOptions );
    }

    @Override
    public boolean equals( final Object that ) {
        if ( !( that instanceof AbstractOptionSpec<?> ) )
            return false;

        final AbstractOptionSpec<?> other = (AbstractOptionSpec<?>) that;
        return options.equals( other.options );
    }

    @Override
    public int hashCode() {
        return options.hashCode();
    }

    @Override
    public String toString() {
        return options.toString();
    }
}
