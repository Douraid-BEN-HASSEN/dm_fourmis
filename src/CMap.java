public class CMap {
    private CTile tiles[][];
    private CAnthill anthills[];
    // private PheromoneManager;

    // constructor
    public CMap(int pnbAnthill) {
        this.tiles = new CTile[CConstants.MAP_SIZE_X][CConstants.MAP_SIZE_Y];
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                this.tiles[i][j] = new CTile();
            }
        }

        this.anthills = new CAnthill[pnbAnthill];

        this.addRessources();
        this.addAnthills();
    }

    public CTile getTile(CAnt pAnt) {
        return null;
    }

    public CTile getTile(CAnthill pAnthill) {
        return null;
    }

    public void moveTo(CAnt pAnt, CTile pTile) {

    }

    public CTile getTopTile(CAnt pAnt) {
        return null;
    }

    public CTile getLeftTile(CAnt pAnt) {
        return null;
    }

    public CTile getRightTile(CAnt pAnt) {
        return null;
    }

    public CTile getBottomTile(CAnt pAnt) {
        return null;
    }

    public void displayMap() {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                this.tiles[i][j].afficherTile();
            }
            System.out.println("");
        }
    }

    // methode pour generer des ressources aleatoirement à la création de la map
    private void addRessources() {
        for (int i = 0; i < CConstants.MAP_SIZE_X; i++) {
            for (int j = 0; j < CConstants.MAP_SIZE_Y; j++) {
                int nombreAleatoire = 0 + (int)(Math.random() * ((100 - 0) + 1));
                if(nombreAleatoire < 5) {
                    nombreAleatoire = 0 + (int)(Math.random() * ((50 - 1) + 1));
                    for(int nbRessource = 0; nbRessource < nombreAleatoire; nbRessource++) {
                        this.tiles[i][j].dropResource(new CResource(), null);
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
                this.anthills[it] = new CAnthill(EAnthillColor.RED);
                index++;
            } else if(index == 1) {
                this.anthills[it] = new CAnthill(EAnthillColor.BLUE);
                index++;
            } else if(index == 2) {
                this.anthills[it] = new CAnthill(EAnthillColor.YELLOW);
                index++;
            } else if(index == 3) {
                this.anthills[it] = new CAnthill(EAnthillColor.GREEN);
                index = 0;
            }
            this.tiles[positions[it][0]][positions[it][1]].setAnthill(this.anthills[it]);
        }

    }
}
