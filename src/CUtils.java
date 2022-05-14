public class CUtils {
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static int getRandom(int pMin, int pMax) {
        return pMin + (int)(Math.random() * ((pMax) + 1));
    }
}
