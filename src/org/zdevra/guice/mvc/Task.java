package org.zdevra.guice.mvc;

import java.util.concurrent.CountDownLatch;

public class Task<M> implements Runnable {

    private final M message;
    private final CountDownLatch cdl;
    private final ITask<M> tsk;

    public static interface ITask<M> {
        public abstract void execute(M message);
    }

    public Task(CountDownLatch cdl, M message, ITask<M> task) {
        this.message = message;
        this.cdl = cdl;
        this.tsk = task;
    }

    @Override
    public final void run() {
        try {
            tsk.execute(message);
        } finally {
            cdl.countDown();
        }
    }
}
