import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTile {

    private ArrayList<CResource> resources;
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
        this.resources = new ArrayList<CResource>();
        this.ants = new ArrayList<CAnt>();
    }

    public void addAnt(CAnt pAnt) {
        this.ants.add(pAnt);
    }

    public void removeAnt(CAnt pAnt) {
        this.ants.remove(pAnt);
    }

    public void dropResource(CResource pResource, CAnt pAnt) {
        this.resources.add(pResource);
    }

    public CResource takeResource() {
        return null;
    }

    public int getTileResource() {
        return 0;
    }

    public EResourceType getResourceType() {
        return  null;
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

    }

    public void afficherTile() {
        if(this.ants.size() > 0) {
            // boucle pour afficher chaque ant
            for(int nbAnt=0; nbAnt < this.ants.size(); nbAnt++) {
                //System.out.print("x");
                if(this.ants.get(nbAnt) instanceof CWorkerAnt) {
                    System.out.print("x");
                } else System.out.print("X");
            }
        }
        if(this.resources.size() > 0) {
            System.out.print("(" + this.resources.size() + ")");
        }
        if(this.anthill != null) {
            System.out.print("[AH]");
        }
        if(!(this.ants.size() > 0 || this.resources.size() > 0 || this.anthill != null)) {
            System.out.print("-");
        }
    }

    public boolean findAnt(CAnt pAnt) {
        return this.ants.contains(pAnt);
    }

    public boolean findAnthill(CAnthill pAnthill) {
        return this.anthill.equals(pAnthill);
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }
}
