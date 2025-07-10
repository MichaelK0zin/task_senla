public class ThreadStates {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Thread thread = new Thread(() -> {
            System.out.println("Поток начал выполнение — RUNNABLE");

            try {
                System.out.println("Поток переходит в TIMED_WAITING (sleep 1s)");
                Thread.sleep(1000);

                synchronized (lock) {
                    System.out.println("Поток получил монитор и вызывает wait() — переходит в WAITING");
                    lock.wait();
                }

                System.out.println("Поток пытается захватить монитор у другого потока — BLOCKED");
                synchronized (ThreadStates.class) {
                    System.out.println("Поток получил монитор — снова RUNNABLE");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Поток завершён — TERMINATED");
        });

        System.out.println("Создан поток, но ещё не запущен — NEW");
        System.out.println("Состояние: " + thread.getState());

        thread.start();
        Thread.sleep(200);
        System.out.println("Состояние после запуска: " + thread.getState());

        Thread.sleep(1500);
        System.out.println("Состояние после sleep: " + thread.getState());

        synchronized (lock) {
            lock.notify();
        }

        Thread.sleep(200);

        Thread blocker = new Thread(() -> {
            synchronized (ThreadStates.class) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        blocker.start();
        Thread.sleep(100);


        blocker.join();
        thread.join();

        System.out.println("Состояние после завершения: " + thread.getState());
    }
}
