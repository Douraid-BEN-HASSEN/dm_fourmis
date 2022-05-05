import java.util.ArrayList;
import java.util.Observable;

public class CWorkerAnt extends CAnt {

    private ArrayList<CResource> resources;

    public CWorkerAnt(EAnthillColor pAnthillColor, int pId) {
        super(pAnthillColor, pId);
    }

    public void update() {

    }

    public void run() {
        int direction;
        boolean deplacement = true;

        while (true){
            if(deplacement) {
                CTile tile = new CTile(0,0);
                deplacement = false;
                direction = (int) (Math.random() * 4);

                switch(direction)
                {
                    case 0:
                        // RIGHT
                        tile = CMap.shared().getRightTile(this);
                        if(tile == null) deplacement = true;
                        break;
                    case 1:
                        // LEFT
                        tile = CMap.shared().getLeftTile(this);
                        if(tile == null) deplacement = true;
                        break;
                    case 2:
                        // BOTTOM
                        tile = CMap.shared().getBottomTile(this);
                        if(tile == null) deplacement = true;
                        break;
                    case 3:
                        // TOP
                        tile = CMap.shared().getTopTile(this);
                        if(tile == null) deplacement = true;
                        break;
                }

                if(!deplacement) {
                    CMap.shared().moveTo(this, tile);
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addObserver(Observable pObservalbe) {

    }
}
