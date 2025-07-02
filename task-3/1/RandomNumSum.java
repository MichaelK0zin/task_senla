import java.util.Random;

public class RandomNumSum {
    public static void main(String[] args) {
        Random random = new Random();
        int num = random.nextInt(900)+100;

        int digit1 = num / 100;
        int digit2 = (num /10) % 10;
        int digit3 = num % 10;

        int sum = digit1 + digit2 + digit3;

        System.out.println("Сгенерированное трехзначное число: " + num);
        System.out.println("Сумма его чисел: " + sum);
    }
}