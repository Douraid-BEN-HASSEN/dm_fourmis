import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTile {

    private ArrayList<CResource> resources;
    private ArrayList<CAnt> ants;
    private Map<EAnthillColor, Integer> pheromones = new HashMap<EAnthillColor, Integer>();
    private CAnthill anthill;

    // constructor
    public CTile(CAnthill pAnthill) {
        this.anthill = pAnthill;
    }

    public void addAnt(CAnt pAnt) {

    }

    public void removeAnt(CAnt pAnt) {

    }

    public void dropResource(CResource pResource, CAnt pAnt) {

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
}
