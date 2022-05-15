// classe utilitaire
public class CUtils {

    // methode pour "sleep"
    public static void wait(int ms) {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    // methode qui retourne un nombre aleatoire
    public static int getRandom(int pMin, int pMax) {
        return pMin + (int)(Math.random() * ((pMax) + 1));
    }

}
