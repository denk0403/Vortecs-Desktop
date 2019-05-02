package Testing;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents an object able to run tests
 * 
 * @author Dennis Kats
 *
 */
public abstract class Tester {

	private static int counter = 0;

	/**
	 * Tests that the first and second arguments are equivalent
	 * 
	 * @param <E> The class of the first object being compared
	 * @param <T> The class of the second object being compared
	 * @param e   The first object
	 * @param t   The second object
	 */
	public static <E, T> void test(E e, T t) {
		counter += 1;
		assert e.equals(e) && e.equals(t) && t.equals(e) && t.equals(t) : print(e, t);
	}

	/**
	 * Tests that the first and second arguments are equivalent
	 * 
	 * @param <E> The class of the first object being compared
	 * @param <T> The class of the second object being compared
	 * @param e   The first object
	 * @param t   The second object
	 * @param msg The error message to return
	 */
	public static <E, T> void test(E e, T t, String msg) {
		counter += 1;
		assert e.equals(e) && e.equals(t) && t.equals(e) && t.equals(t) : msg;
	}

	/**
	 * Tests that the given method on the given object throws the given exception
	 * 
	 * @param <T>        The class of the object being tested
	 * @param e          The object the method is called on
	 * @param ex         The exception expected to be thrown
	 * @param methodName The method to call on the given object
	 * @param params     Additional parameters to pass to the method
	 */
	public static <T> void testException(T t, Exception ex, String methodName, Object... params) {
		counter += 1;
		try {
			t.getClass().getMethod(methodName).invoke(t, params);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			assert e1.getTargetException().getMessage().equals(ex.getMessage()) : print(t);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Tests whether the given arguments are within some given margin of error
	 * 
	 * @param a      The first value
	 * @param b      The second value
	 * @param margin The maximum (inclusive) margin of error between the two given
	 *               values
	 */
	public static void testInexact(double a, double b, double margin) {
		assert Math.abs(a - b) <= margin : print(a, b);
	}

	/**
	 * Returns string of all given objects
	 * 
	 * @param objs An iterable of objects
	 * @return A string of all vector string representations
	 */
	public static String print(Object... objs) {
		String output = "\n";
		for (Object obj : objs) {
			output += obj.toString() + "\n";
		}
		return output;
	}

	/**
	 * 
	 * @return The number of tests ran
	 */
	public static int getTestCounter() {
		return counter;
	}

}
