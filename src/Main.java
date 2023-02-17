import java.util.*;

public class Main {
    final static int LENGTH = 100;
    final static String LETTERS = "RLRFR";
    final static int THREADS = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < THREADS; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS, LENGTH);
                int frequancy = countR(route, 'R');

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frequancy)) {
                        sizeToFreq.put(frequancy, sizeToFreq.get(frequancy) + 1);
                    } else {
                        sizeToFreq.put(frequancy, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);

        System.out.println("Самое частое количество повторений " + max.getKey() + " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");
        sizeToFreq
                .forEach((key, value) -> System.out.println(" - " + key + " (" + value + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countR(String route, char a) {
        int count = 0;
        char[] chars = route.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == a) {
                count++;
            }
        }
        return count;
    }
}