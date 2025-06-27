public class Main {
    public static void main(String[] args) {
        for (int x = 0; x <= 255; x++) {
            int hostBits = Integer.bitCount(~x & 0xFF); // сколько нулей в маске
            int totalHosts = (int) Math.pow(2, hostBits);
            int count = 0;

            for (int i = 0; i < totalHosts; i++) {
                int zeros = Integer.bitCount(~i & ((1 << hostBits) - 1));
                if (zeros % 7 == 0) {
                    count++;
                }
            }

            if (count == 35) {
                System.out.println("Найден X: " + x);
                break;
            }
        }
    }
}