package ru.temnikov.demo.util;

public interface Util {

    static void uncheck(final ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new RuntimeException(exception);
        }
    }

    interface ThrowingRunnable {
        void run() throws Exception;
    }
}
