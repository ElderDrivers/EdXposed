package de.robv.android.xposed.callbacks;

public interface IXUnhook<T> {
	/**
	 * Returns the callback that has been registered.
	 */
	T getCallback();

	/**
	 * Removes the callback.
	 */
	void unhook();
}
