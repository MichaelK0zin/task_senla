public class ThreadsQueue {

    private static final Object lock = new Object();
    private static boolean isFirstTurn = true;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                synchronized (lock) {
                    while (!isFirstTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Поток 1");
                    isFirstTurn = false;
                    lock.notify();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                synchronized (lock) {
                    while (isFirstTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Поток 2");
                    isFirstTurn = true;
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
