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

import java.util.NoSuchElementException;

import static java.util.Collections.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.infinitest.toolkit.CollectionMatchers.*;
import static org.junit.Assert.*;
import static org.junit.rules.ExpectedException.*;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class OptionSpecTokenizerTest {
    @Rule public final ExpectedException thrown = none();

    @Test
    public void tokenizeEmpty() {
        assertNoMoreTokens( new OptionSpecTokenizer( "" ) );
    }

    @Test
    public void tokenizeOptionsWithoutArguments() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "ab" );
        assertNextTokenTakesNoArgument( lexer, 'a', false );
        assertNextTokenTakesNoArgument( lexer, 'b', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithRequiredArguments() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "c:d:" );
        assertNextTokenRequiresAnArgument( lexer, 'c', false );
        assertNextTokenRequiresAnArgument( lexer, 'd', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithOptionalArguments() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "e::f::" );
        assertNextTokenTakesAnOptionalArgument( lexer, 'e', false );
        assertNextTokenTakesAnOptionalArgument( lexer, 'f', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithMixtureOfSpecTypes() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "gh:i::j" );
        assertNextTokenTakesNoArgument( lexer, 'g', false );
        assertNextTokenRequiresAnArgument( lexer, 'h', false );
        assertNextTokenTakesAnOptionalArgument( lexer, 'i', false );
        assertNextTokenTakesNoArgument( lexer, 'j', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wByItself() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W" );
        assertNextTokenTakesNoArgument( lexer, 'W', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wRequiredArg() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W:" );
        assertNextTokenRequiresAnArgument( lexer, 'W', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wOptionalArg() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W::" );
        assertNextTokenTakesAnOptionalArgument( lexer, 'W', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void alternativeLongOptionsMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W;" );
        assertNextTokenRequiresAnArgument( lexer, 'W', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithoutArgumentsAndHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "ab*" );
        assertNextTokenTakesNoArgument( lexer, 'a', false );
        assertNextTokenTakesNoArgument( lexer, 'b', true );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithRequiredArgumentsAndHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "c*:d:" );
        assertNextTokenRequiresAnArgument( lexer, 'c', true );
        assertNextTokenRequiresAnArgument( lexer, 'd', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithOptionalArgumentsAndHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "e*::f*::" );
        assertNextTokenTakesAnOptionalArgument( lexer, 'e', true );
        assertNextTokenTakesAnOptionalArgument( lexer, 'f', true );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void tokenizeOptionsWithMixtureOfSpecTypesAndHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "g*h:i*::j" );
        assertNextTokenTakesNoArgument( lexer, 'g', true );
        assertNextTokenRequiresAnArgument( lexer, 'h', false );
        assertNextTokenTakesAnOptionalArgument( lexer, 'i', true );
        assertNextTokenTakesNoArgument( lexer, 'j', false );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wByItselfWithHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W*" );
        assertNextTokenTakesNoArgument( lexer, 'W', true );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wRequiredArgWithHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W*:" );
        assertNextTokenRequiresAnArgument( lexer, 'W', true );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void wOptionalArgWithHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W*::" );
        assertNextTokenTakesAnOptionalArgument( lexer, 'W', true );
        assertNoMoreTokens( lexer );
    }

    @Test
    public void alternativeLongOptionsMarkerWithHelpMarker() {
        final OptionSpecTokenizer lexer = new OptionSpecTokenizer( "W*;" );
        assertNextTokenTakesNoArgument( lexer, 'W', true );
    }

    private void assertNoMoreTokens( final OptionSpecTokenizer lexer ) {
        assertFalse( lexer.hasMore() );

        thrown.expect( NoSuchElementException.class );
        lexer.next();
    }

    private static void assertNextTokenTakesNoArgument( final OptionSpecTokenizer lexer, final char option, final boolean forHelp ) {
        assertNextToken( lexer, option, false, false, forHelp );
    }

    private static void assertNextTokenRequiresAnArgument( final OptionSpecTokenizer lexer, final char option, final boolean forHelp ) {
        assertNextToken( lexer, option, true, true, forHelp );
    }

    private static void assertNextTokenTakesAnOptionalArgument( final OptionSpecTokenizer lexer, final char option,
        final boolean forHelp ) {

        assertNextToken( lexer, option, true, false, forHelp );
    }

    private static void assertNextToken( final OptionSpecTokenizer lexer, final char option, final boolean acceptsArguments,
        final boolean requiresArgument, final boolean forHelp ) {

        assertTrue( "no more tokens?", lexer.hasMore() );
        final AbstractOptionSpec<?> spec = lexer.next();
        assertThat( "option?", spec.options(), hasSameContentsAs( singleton( String.valueOf( option ) ) ) );
        assertEquals( "accepts args?", acceptsArguments, spec.acceptsArguments() );
        assertEquals( "requires arg?", requiresArgument, spec.requiresArgument() );
        assertEquals( "for help?", forHelp, spec.isForHelp() );
    }
}
