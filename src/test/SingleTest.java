package test;

public abstract class SingleTest implements SingleTestInterface {

	protected void err( String s ) {
		System.err.println( "test." + name() + ": " + s );
	}

	protected boolean equals_object( String name, Object value, Object intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean equals_string( String name, String value, String intended_value ) {
		if( !value.equals( intended_value ) ) {
			err( name + " is \"" + value + "\" instead of \"" + intended_value + "\"" );
			return false;
		}
		return true;
	}

	protected boolean equals_bool( String name, boolean value, boolean intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean equals_int( String name, int value, int intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean equals_double( String name, double value, double intended_value ) {
		if( value != intended_value ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	protected boolean equals_double_window( String name, double value, double intended_value, double window ) {
		if( Math.abs( value - intended_value ) > window ) {
			err( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

}
