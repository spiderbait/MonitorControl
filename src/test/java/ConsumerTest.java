import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<Integer> consumer = x -> {
            System.out.println(x);
            x += 2;
            System.out.println(x);
        };
        int z = 2;
        consumer.accept(z);
        System.out.println(z);
    }
}
