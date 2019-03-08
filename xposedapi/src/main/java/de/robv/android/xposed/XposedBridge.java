package de.robv.android.xposed;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

public class XposedBridge {

    public static boolean disableHooks = false;

    public static final Map<Member, CopyOnWriteSortedSet<XC_MethodHook>> sHookedMethodCallbacks = new HashMap<>();

    public synchronized static void log(String text) {
    }

    /**
     * Logs a stack trace to the Xposed error log.
     *
     * <p class="warning"><b>DON'T FLOOD THE LOG!!!</b> This is only meant for error logging.
     * If you want to write information/debug messages, use logcat.
     *
     * @param t The Throwable object for the stack trace.
     */
    public synchronized static void log(Throwable t) {
    }

    public static void unhookMethod(Member hookMethod, XC_MethodHook callback) {
    }

    public static final class CopyOnWriteSortedSet<E> {

        @SuppressWarnings("UnusedReturnValue")
        public synchronized boolean add(E e) {
            return true;
        }

        @SuppressWarnings("UnusedReturnValue")
        public synchronized boolean remove(E e) {
            return true;
        }

        private int indexOf(Object o) {
            return -1;
        }

        public Object[] getSnapshot() {
            return null;
        }

        public synchronized void clear() {
        }
    }

    public static class AdditionalHookInfo {
        public final CopyOnWriteSortedSet<XC_MethodHook> callbacks;
        public final Class<?>[] parameterTypes;
        public final Class<?> returnType;

        private AdditionalHookInfo(CopyOnWriteSortedSet<XC_MethodHook> callbacks, Class<?>[] parameterTypes, Class<?> returnType) {
            this.callbacks = callbacks;
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
        }
    }
}
