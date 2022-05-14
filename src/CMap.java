import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class CMap {
    private static volatile CMap instance;

    private CTile tiles[][];
    private CAnthill anthills[];
    // private PheromoneManager;

    private int tempsRestant;

    private Thread timer_th;

    private boolean partieFinie;

    // constructor
    private CMap(int pnbAnthill) {
        this.tempsRestant = 120;
        this.partieFinie = false;

        this.tiles = new CTile[CConstants.MAP_SIZE_X][CConstants.MAP_SIZE_Y];
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                this.tiles[i][j] = new CTile(i, j);
            }
        }

        this.anthills = new CAnthill[pnbAnthill];

        this.addRessources();
        this.addAnthills();

        // start Timer pour tempsRestant
        this.timer_th = new Thread() {
            public void run() {
                CMap.shared().timerTH();
            }
        };
        this.timer_th.start();

    }

    public CTile getTile(CAnt pAnt) {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                if(this.tiles[i][j].findAnt(pAnt)) {
                    return this.tiles[i][j];
                }
            }
        }
        return null;
    }

    public CTile getTile(CAnthill pAnthill) {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                if(this.tiles[i][j].findAnthill(pAnthill)) {
                    return this.tiles[i][j];
                }
            }
        }
        return null;
    }

    public CTile getTile(int pX, int pY) {
        try{
            if(pX < 0 || pY < 0) return null;
            if(pX >= this.tiles.length || pY >= this.tiles[0].length) return null;
            return this.tiles[pX][pY];
        }catch (Exception e){
            return null;
        }
    }

    public void moveTo(CAnt pAnt, int pXTo, int pYTo) {
        this.tiles[pAnt.getxPos()][pAnt.getyPos()].removeAnt(pAnt);
        pAnt.setxPos(pXTo);
        pAnt.setyPos(pYTo);
        this.tiles[pXTo][pYTo].addAnt(pAnt);
    }

    public CTile getTopTile(CAnt pAnt) {
        if(!(pAnt.getxPos() - 1 < 0)) {
            return this.tiles[pAnt.getxPos() - 1][pAnt.getyPos()];
        }
        return null;
    }

    public CTile getLeftTile(CAnt pAnt) {
        if(!(pAnt.getyPos() - 1 < 0)) {
            return this.tiles[pAnt.getxPos()][pAnt.getyPos() - 1];
        }
        return null;
    }

    public CTile getRightTile(CAnt pAnt) {
        if(!(pAnt.getyPos() + 1 >= CConstants.MAP_SIZE_Y)) {
            return this.tiles[pAnt.getxPos()][pAnt.getyPos() + 1];
        }
        return null;
    }

    public CTile getBottomTile(CAnt pAnt) {
        if(!(pAnt.getxPos() + 1 >= CConstants.MAP_SIZE_X)) {
            return this.tiles[pAnt.getxPos() + 1][pAnt.getyPos()];
        }
        return null;
    }

    public void displayMap() {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                this.tiles[i][j].afficherTile();
            }
            System.out.println("");
        }

        if(this.getTempsRestant() > 0) {
            System.out.println("temps restant => " + this.getTempsRestant());
            System.out.println("");
            System.out.println("");
        }
        else {
            System.out.println("Fin de partie en cours...");

            boolean allAnthill = false;
            while(!allAnthill) {
                allAnthill = true;
                for(int nbAnthill=0; nbAnthill < this.anthills.length; nbAnthill++) {
                    for(int nbCommander=0; nbCommander < this.anthills[nbAnthill].getCommander().size(); nbCommander++) {
                        if(!(this.anthills[nbAnthill].getCommander().get(nbCommander).pileDeplacement.size() == 0)) {
                            allAnthill = false;
                            break;
                        }
                    }

                    for(int nbWorker=0; nbWorker < this.anthills[nbAnthill].getWorkers().size(); nbWorker++) {
                        if(!(this.anthills[nbAnthill].getWorkers().get(nbWorker).pileDeplacement.size() == 0)) {
                            allAnthill = false;
                            break;
                        }
                    }

                    if(!allAnthill) break;
                }
            }

            for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
                for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                    this.tiles[i][j].afficherTile();
                }
                System.out.println("");
            }

            for(int nbAnthill=0; nbAnthill < this.anthills.length; nbAnthill++) {
                System.out.println("Anthill " + this.anthills[nbAnthill].getColor() +
                        " : score = " + this.anthills[nbAnthill].getnPoint() +
                        " | resource = " + this.anthills[nbAnthill].getnResource() +
                        " | commandants = " + this.anthills[nbAnthill].getCommander().size() +
                        " | worker = " + this.anthills[nbAnthill].getWorkers().size());
            }

        }
    }

    // methode pour generer des ressources aleatoirement à la création de la map
    private void addRessources() {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                int nombreAleatoire = CUtils.getRandom(0,100);
                if(nombreAleatoire < 5) {
                    nombreAleatoire = CUtils.getRandom(0,50);
                    for(int nbRessource = 0; nbRessource < nombreAleatoire; nbRessource++) {
                        int ressourceRandom = CUtils.getRandom(0, 10);
                        if(ressourceRandom > 3) this.tiles[i][j].dropResource(new CResource(EResourceType.FOOD), null);
                        else this.tiles[i][j].dropResource(new CResource(EResourceType.POINT), null);
                    }
                }
            }
        }
    }

    // methode pour placer des anthills aleatoirement à la création de la map
    private void addAnthills() {
        /**
         * [1] generer random pos pour n anthills
         * [2] placer les anthills
         */
        int positions[][] = new int[this.anthills.length][2];
        int index = 0;
        while(index < this.anthills.length) {
            int x = 0 + (int)(Math.random() * ((CConstants.MAP_SIZE_X-1 - 0) + 1));
            int y = 0 + (int)(Math.random() * ((CConstants.MAP_SIZE_Y-1 - 0) + 1));

            // check pos si pas deja prise
            boolean pasPrise = false;
            while (!pasPrise) {
                pasPrise = true;
                for(int it=0; it < index-1; it++) {
                    if(positions[it][0] == x && positions[it][1] == y) {
                        pasPrise = false;
                    }
                }
                if(!pasPrise) {
                    x = 0 + (int)(Math.random() * ((CConstants.MAP_SIZE_X-1 - 0) + 1));
                    y = 0 + (int)(Math.random() * ((CConstants.MAP_SIZE_Y-1 - 0) + 1));
                }
            }

            positions[index][0] = x;
            positions[index][1] = y;
            index++;
        }

        // placer les anthills
        index = 0; // index pour la répartition des couleurs
        for(int it=0; it < this.anthills.length; it++) {
            if(index == 0 ) {
                this.anthills[it] = new CAnthill(EAnthillColor.RED, new Point(positions[it][0], positions[it][1]));
                index++;
            } else if(index == 1) {
                this.anthills[it] = new CAnthill(EAnthillColor.BLUE, new Point(positions[it][0], positions[it][1]));
                index++;
            } else if(index == 2) {
                this.anthills[it] = new CAnthill(EAnthillColor.YELLOW, new Point(positions[it][0], positions[it][1]));
                index++;
            } else if(index == 3) {
                this.anthills[it] = new CAnthill(EAnthillColor.GREEN, new Point(positions[it][0], positions[it][1]));
                index = 0;
            }
            this.tiles[positions[it][0]][positions[it][1]].setAnthill(this.anthills[it]);
            this.anthills[it].start();
        }
    }

    private void timerTH() {
        while (this.tempsRestant > 0) {
            this.tempsRestant--;
            CUtils.wait(100);
        }
        this.partieFinie = true;
    }

    public int getTempsRestant() {
        return this.tempsRestant;
    }

    public boolean getPartieFinie() {
        return this.partieFinie;
    }

    public static CMap shared() {
        int nbAnthill = 4;
        CMap result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CMap.class) {
            if (instance == null) {
                instance = new CMap(nbAnthill);
            }
            return instance;
        }
    }


}
