package transitions;

public class PrefixFactory {

	private static int next_prefix = 0;

	public static String nextPrefix() {
		return ( ++next_prefix ) + "_";
	}

}
