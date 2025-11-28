package be.ecam.pattern.concurrency.patterns;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadPool {

    private final Queue<Runnable> taskQueue;
    private volatile boolean isShutdown = false;

    public ThreadPool(int poolSize) {
        this.taskQueue = new LinkedList<>();
        WorkerThread[] workers = new WorkerThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread("Worker-" + i);
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shutdown. Cannot accept new tasks.");
            }
            taskQueue.offer(task);
            taskQueue.notify(); // Wake up a waiting worker
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll(); // Wake all workers to finish remaining tasks and exit
        }
    }

    private class WorkerThread extends Thread {
        WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException ignored) {}
                    }

                    if (taskQueue.isEmpty() && isShutdown) {
                        break; // Exit thread
                    }

                    task = taskQueue.poll();
                }

                try {
                    if (task != null) {
                        task.run();
                    }
                } catch (RuntimeException e) {
                    System.err.println("Task execution failed: " + e.getMessage());
                }
            }
            System.out.println(Thread.currentThread().getName() + " stopped.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " running task " + taskId);
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            });
        }

        Thread.sleep(5000);
        pool.shutdown();
    }
}
