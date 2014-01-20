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

import static java.util.Collections.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class ClusterWhereManyOptionsAcceptArgumentsTest extends AbstractOptionParserFixture {
    @Before
    public final void initializeParser() {
        parser.accepts( "f" );
        parser.accepts( "o" ).withOptionalArg();
        parser.accepts( "x" ).withRequiredArg();
    }

    @Test
    public void foxPermutation() {
        final OptionSet options = parser.parse( "-fox" );

        assertTrue( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "x" ), options.valuesOf( "o" ) );
    }

    @Test
    public void fxoPermutation() {
        final OptionSet options = parser.parse( "-fxo" );

        assertTrue( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "o" ), options.valuesOf( "x" ) );
    }

    @Test
    public void ofxPermutation() {
        final OptionSet options = parser.parse( "-ofx" );

        assertFalse( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "fx" ), options.valuesOf( "o" ) );
    }

    @Test
    public void oxfPermutation() {
        final OptionSet options = parser.parse( "-oxf" );

        assertFalse( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "xf" ), options.valuesOf( "o" ) );
    }

    @Test
    public void xofPermutation() {
        final OptionSet options = parser.parse( "-xof" );

        assertFalse( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "of" ), options.valuesOf( "x" ) );
    }

    @Test
    public void xfoPermutation() {
        final OptionSet options = parser.parse( "-xfo" );

        assertFalse( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "fo" ), options.valuesOf( "x" ) );
    }

    @Test
    public void foxPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-fox", "bar" );

        assertTrue( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "x" ), options.valuesOf( "o" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }

    @Test
    public void fxoPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-fxo", "bar" );

        assertTrue( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "o" ), options.valuesOf( "x" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }

    @Test
    public void ofxPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-ofx", "bar" );

        assertFalse( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "fx" ), options.valuesOf( "o" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }

    @Test
    public void oxfPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-oxf", "bar" );

        assertFalse( options.has( "f" ) );
        assertTrue( options.has( "o" ) );
        assertFalse( options.has( "x" ) );

        assertEquals( singletonList( "xf" ), options.valuesOf( "o" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }

    @Test
    public void xofPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-xof", "bar" );

        assertFalse( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "of" ), options.valuesOf( "x" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }

    @Test
    public void xfoPermutationWithFollowingArg() {
        final OptionSet options = parser.parse( "-xfo", "bar" );

        assertFalse( options.has( "f" ) );
        assertFalse( options.has( "o" ) );
        assertTrue( options.has( "x" ) );

        assertEquals( singletonList( "fo" ), options.valuesOf( "x" ) );
        assertEquals( singletonList( "bar" ), options.nonOptionArguments() );
    }
}
