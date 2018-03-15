package test;

public abstract class SingleTest implements SingleTestInterface {

	protected void err( String s ) {
		System.err.println( "test." + name() + ": " + s );
	}

	protected boolean diff_object( String name, Object value, Object intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean diff( String name, boolean value, boolean intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean diff( String name, int value, int intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean diff( String name, double value, double intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean diff( String name, double value, double intended_value, double window ) {
		if( Math.abs( value - intended_value ) > window ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

}
