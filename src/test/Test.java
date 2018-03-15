package test;

import java.util.ArrayList;

import test.xml.DemoFrameGraphTest;
import test.xml.XMLSettingsTest;

public class Test {

	public static ArrayList< SingleTestInterface > tests = new ArrayList< SingleTestInterface >();

	public static void main( String[] args ) {
		addTests();

		int num_tests_passed = 0;
		for( SingleTestInterface test : tests ) {
			if( test.run() ) {
				System.out.println( test.name() + " passed." );
				++num_tests_passed;
			} else {
				System.out.println( test.name() + " failed." );
			}
		}
		System.out.println( num_tests_passed + " of " + tests.size() + " tests passed." );
	}

	private static void addTests() {
		tests.add( new XMLSettingsTest() );
		tests.add( new DemoFrameGraphTest() );
	}

}