public class CMain  {
    public static void main(String[] args) {
        CMap map = CMap.shared();

        while (map.getTempsRestant() > 0) {
            map.displayMap();
            CUtils.wait(1000);
        }

        map.displayMap();

    }
}
