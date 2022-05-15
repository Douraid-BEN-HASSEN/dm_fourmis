import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// classe qui represente les tiles
public class CTile {
    private LinkedList<CResource> resources;
    private ArrayList<CAnt> ants;
    private Map<EAnthillColor, Integer> pheromones = new HashMap<EAnthillColor, Integer>();
    private CAnthill anthill;
    private int xPos;
    private int yPos;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // constructor
    public CTile(int pxPos, int pyPos) {
        this.xPos = pxPos;
        this.yPos = pyPos;

        this.anthill = null;
        this.resources = new LinkedList<CResource>();
        this.ants = new ArrayList<CAnt>();
    }

    // methode pour ajouter une fourmis sur la tile
    public void addAnt(CAnt pAnt) {
        synchronized (this.ants) {
            this.ants.add(pAnt);
        }
    }

    // methode pour retirer une fourmis
    public void removeAnt(CAnt pAnt) {
        synchronized(this.ants) {
            this.ants.remove(pAnt);
        }
    }

    // methode pour retirer une ressource
    public void dropResource(CResource pResource, CAnt pAnt) {
        synchronized (this.resources) {
            if(pAnt != null) {
                CResource resource = pAnt.dropRessource();
                this.resources.add(resource);
            } else {
                this.resources.add(pResource);
            }
        }
    }

    // methode pour prendre une ressource
    public CResource takeResource() {
        synchronized (this.resources) {
            if(this.resources.size() > 0) {
                return this.resources.pollLast();
            } else {
                return null;
            }
        }
    }

    // methode pour ajouter une pheromone
    public void addPheromone(EAnthillColor pAnthillColor) {

    }

    // methode pour enlever une pheromone
    public void removePheromone(EAnthillColor pAnthillColor) {

    }

    // methode pour afficher la tile
    public void afficherTile() {
        synchronized (this.ants) {
            if(this.ants.size() > 0) {
                // boucle pour afficher chaque ant
                for(int nbAnt=0; nbAnt < this.ants.size(); nbAnt++) {
                    if(this.ants.get(nbAnt) instanceof CWorkerAnt) {
                        System.out.print("x");
                    } else if(this.ants.get(nbAnt) instanceof CCommanderAnt){
                        System.out.print("X");
                    }
                }
            }
        }

        synchronized (this.resources) {
            if(this.resources.size() > 0) {
                System.out.print("(" + this.resources.size() + ")");
            }
        }

        if(this.anthill != null) {
            System.out.print("[AH]");
        }

        if(!(this.ants.size() > 0 || this.resources.size() > 0 || this.anthill != null)) {
            System.out.print("-");
        }
    }

    // +--------+
    // | GETTER |
    // +--------+

    // methode qui retourne la position x de la tile
    public int getxPos() {
        return this.xPos;
    }

    // methode qui retourne la position y de la tile
    public int getyPos() {
        return this.yPos;
    }

    // methode qui retourne le nombre de ressource sur la tile
    public int getTileResource() {
        return this.resources.size();
    }

    // methode qui retourne le type de ressource de la tile
    public EResourceType getResourceType() {
        synchronized (this.resources) {
            if(this.getTileResource() > 0) {
                return this.resources.getLast().getType();
            }
            return  null;
        }
    }

    // methode qui retourne les fourmis de la case
    public ArrayList<CAnt> getAnts() {
        return this.ants;
    }

    // methode qui retourne le nombre de pheromone sur la tile
    public int getPheromoneQuantity(EAnthillColor pAnthillColor) {
        return 0;
    }

    // +--------+
    // | SETTER |
    // +--------+

    // methode qui enregistre une anthill
    public void setAnthill(CAnthill pAnthill) {
        this.anthill = pAnthill;
        // get worker ant
        for(int nbWorkerAnt=0; nbWorkerAnt < this.anthill.getWorkers().size(); nbWorkerAnt++) {
            this.addAnt(this.anthill.getWorkers().get(nbWorkerAnt));
        }
        // get commander ant
        for(int nbCommanderAnt=0; nbCommanderAnt < this.anthill.getCommander().size(); nbCommanderAnt++) {
            this.addAnt(this.anthill.getCommander().get(nbCommanderAnt));
        }

        int a = 0;

    }


}
