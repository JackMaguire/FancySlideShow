package test;

public abstract class SingleTest implements SingleTestInterface {

	protected void err( String s ) {
		System.err.println( "test." + name() + ": " + s );
	}

	protected boolean diff( String name, int value, int intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}
	
}
