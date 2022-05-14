import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CTile {

    private LinkedList<CResource> resources;
    private ArrayList<CAnt> ants;
    private Map<EAnthillColor, Integer> pheromones = new HashMap<EAnthillColor, Integer>();
    private CAnthill anthill;

    private int xPos;
    private int yPos;

    // constructor
    public CTile(int pxPos, int pyPos) {
        this.xPos = pxPos;
        this.yPos = pyPos;

        this.anthill = null;
        this.resources = new LinkedList<CResource>();
        this.ants = new ArrayList<CAnt>();
    }

    public void addAnt(CAnt pAnt) {
        synchronized (this.ants) {
            this.ants.add(pAnt);
        }
    }

    public void removeAnt(CAnt pAnt) {
        synchronized(this.ants) {
            this.ants.remove(pAnt);
        }
    }

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

    public CResource takeResource() {
        synchronized (this.resources) {
            if(this.resources.size() > 0) {
                return this.resources.pollLast();
            } else {
                return null;
            }
        }
    }

    public int getTileResource() {
        return this.resources.size();
    }

    public EResourceType getResourceType() {
        synchronized (this.resources) {
            if(this.getTileResource() > 0) {
                return this.resources.getLast().getType();
            }
            return  null;
        }
    }

    public ArrayList<CAnt> getAnts() {
        return this.ants;
    }

    public int getPheromoneQuantity(EAnthillColor pAnthillColor) {
        return 0;
    }

    public void addPheromone(EAnthillColor pAnthillColor) {

    }

    public void removePheromone(EAnthillColor pAnthillColor) {

    }

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

    public boolean findAnt(CAnt pAnt) {
        for(int it=0; it < this.ants.size(); it++) {
            if(this.ants.get(it).getId() == pAnt.getAntId()) return true;
        }
        return false;
        //this.ants.contains(pAnt)
    }

    public boolean findAnthill(CAnthill pAnthill) {
        if(this.anthill != null) return this.anthill.getColor() == pAnthill.getColor();
        else return false;
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

}
