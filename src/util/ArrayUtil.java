/*
 * package util;
 * 
 * import java.util.Arrays;
 * 
 * public class ArrayUtil {
 * 
 * static <T> T[] extendArray( T[] in, T new_element ) { Object[] new_array =
 * new Object[in.length + 1]; for( int i = 0; i < in.length; ++i ) { new_array[
 * i ] = in[ i ]; } new_array[ in.length ] = new_element; T[] cast_array = (T[])
 * new_array; return cast_array; }
 * 
 * public static class extendArrayTest implements test.SingleTest {
 * 
 * public int dummy_value;
 * 
 * public extendArrayTest() { dummy_value = 0; }
 * 
 * public extendArrayTest( int dummy_val ) { dummy_value = dummy_val; }
 * 
 * @Override public boolean run() {
 * 
 * extendArrayTest[] test_array = new extendArrayTest[ 3 ]; test_array[ 0 ] =
 * new extendArrayTest( 0 ); test_array[ 1 ] = new extendArrayTest( 1 );
 * test_array[ 2 ] = new extendArrayTest( 2 ); test_array = (extendArrayTest[])
 * ArrayUtil.< extendArrayTest >extendArray( test_array, new extendArrayTest( 3
 * ) );
 * 
 * if( test_array.length != 4 ) { p( "test_array.length != 4" ); return false; }
 * 
 * for( int i=0; i<4; ++i ) { if( test_array[i].dummy_value != i ) { p(
 * "test_array[i].dummy_value != i for i=" + i ); return false; } }
 * 
 * return true; }
 * 
 * private void p( String s ) { System.err.println( "util.extendArrayTest: " + s
 * ); }
 * 
 * }
 * 
 * }
 */