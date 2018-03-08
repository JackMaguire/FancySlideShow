package test;

import java.util.ArrayList;

public class Test{

	public static ArrayList< SingleTest > tests = new ArrayList< SingleTest >();
	
	public static void main(String [] args) {
		addTests();
		
		int num_tests_passed = 0;
		for( SingleTest test : tests ) {
			if( test.run() ) {
				++num_tests_passed;
			}
		}
		System.out.println(  num_tests_passed + " of " + tests.size() + " tests passed." );
	}	
	
	private static void addTests() {
		//tests.add( new util.ArrayUtil.extendArrayTest() );
	}
	
}