package com.gyf.thread;

/**
 * @author yunfan.gyf
 **/
public class IsolatedThreadGroup extends ThreadGroup {
    protected final Object monitor = new Object();

    protected Throwable    exception;

    public IsolatedThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!(ex instanceof ThreadDeath)) {
            synchronized (this.monitor) {
                this.exception = (this.exception == null ? ex : this.exception);
            }
        }
    }

    public synchronized void rethrowUncaughtException() {
        synchronized (this.monitor) {
            if (this.exception != null) {
                throw new RuntimeException("An exception occurred while running. "
                        + this.exception.getMessage(), this.exception);
            }
        }
    }
}
