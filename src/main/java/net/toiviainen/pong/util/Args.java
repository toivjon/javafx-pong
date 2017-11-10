package net.toiviainen.pong.util;

/**
 * <p>
 * A utility for validating and managing function arguments.
 * </p>
 * <p>
 * This utility can be used to validate and manage function arguments by using
 * different kinds of validation functions that provide an in-line way to
 * produce {@link IllegalArgumentException} on certain conditions.
 * </p>
 */
public final class Args {
	private Args() {
		throw new AssertionError("No net.toiviainen.pong.util.Args instances for you!");
	}

	/**
	 * Throw a {@link IllegalArgumentException} if the provided object is null.
	 * @param object Object to check.
	 * @param message A message to pass to exception if the object is null.
	 * @return A reference to the provided object.
	 * @throws IllegalArgumentException When the provided object is null.
	 */
	public static <T> T notNull(T object, String message) throws IllegalArgumentException {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
		return object;
	}

	/**
	 * Check that the provided value is equal or lower than the provided limit.
	 * @param value The value to check.
	 * @param limit The limit to check against.
	 * @param message A message to pass to exception if the validation fails.
	 * @return A reference to the provided object.
	 * @throws IllegalArgumentException When the validation fails.
	 */
	public static int isLte(int value, int limit, String message) throws IllegalArgumentException {
		if (value > limit) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}

	/**
	 * Check that the provided is equal or higher than the provided limit.
	 * @param value The value to check.
	 * @param limit The limit to check against.
	 * @param message A message to pass to exception if the validation fails.
	 * @return A reference to the provided object.
	 * @throws IllegalArgumentException When the validation fails.
	 */
	public static int isGte(int value, int limit, String message) throws IllegalArgumentException {
		if (value < limit) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}

	/**
	 * Check that the provided is equal or higher than the provided limit.
	 * @param value The value to check.
	 * @param limit The limit to check against.
	 * @param message A message to pass to exception if the validation fails.
	 * @return A reference to the provided object.
	 * @throws IllegalArgumentException When the validation fails.
	 */
	public static double isGte(double value, double limit, String message) throws IllegalArgumentException {
		if (value < limit) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}

	/**
	 * Check that the provided is between (inclusively) the provided limits.
	 * @param value The value to check.
	 * @param min The minimum allowed value.
	 * @param max The maximum allowed value.
	 * @param message A message to pass to exception if the validation fails.
	 * @return A reference to the provided object.
	 * @throws IllegalArgumentException When the validation fails.
	 */
	public static int isBetween(int value, int min, int max, String message) throws IllegalArgumentException {
		if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}
}
