import java.util.ArrayList;

public class CAnthill {
    private ArrayList<CWorkerAnt> workers;
    private ArrayList<CCommanderAnt> commanders;
    private ArrayList<CResource> resources;
    private EAnthillColor color;

    // constructor
    public CAnthill(EAnthillColor pAnthillColor) {
        this.color = pAnthillColor;

        this.workers = new ArrayList<>();
        this.commanders = new ArrayList<>();
        this.resources = new ArrayList<>();

        for(int nbCommanderAnt=0; nbCommanderAnt < 5; nbCommanderAnt++) {
            this.commanders.add(new CCommanderAnt(this.color, nbCommanderAnt));
        }
        for(int nbAnt=0; nbAnt < 50; nbAnt++) {
            this.workers.add(new CWorkerAnt(this.color, nbAnt));
        }
    }

    public void run() {

    }

    public void addResource(CResource pResource[]) {

    }

    public CAnt killAnt(CAnt pAnt) {
        return null;
    }

    public ArrayList<CWorkerAnt> getWorkers() {
        return this.workers;
    }

    public ArrayList<CCommanderAnt> getCommander() {
        return this.commanders;
    }
    public int getScore() {
        return 0;
    }
}
