import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServiceThread extends Thread {
    private final int intervalSeconds;

    public ServiceThread(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
        setDaemon(true);
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (true) {
            String currentTime = LocalTime.now().format(formatter);
            System.out.println("Служебное время: " + currentTime);
            try {
                Thread.sleep(intervalSeconds * 1000L);
            } catch (InterruptedException e) {
                System.out.println("Служебный поток прерван.");
                return;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ServiceThread serviceThread = new ServiceThread(3);
        serviceThread.start();

        Thread.sleep(15000);
        System.out.println("Главный поток завершён.");
    }
}