import java.util.Random;


public class seedNumbers {
    int randomGenerator(long seed) {
        Random generator = new Random(seed);
        int num = generator.nextInt();

        return num;
    }
}