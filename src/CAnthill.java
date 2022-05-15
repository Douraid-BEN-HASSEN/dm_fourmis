import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

// classe fourmilière
public class CAnthill extends Thread implements Flow.Subscriber<CMessage> {
    private int nResource;
    private int nPoint;
    private Point position;
    private ArrayList<CWorkerAnt> workers;
    private ArrayList<CCommanderAnt> commanders;
    private ArrayList<CResource> resources;
    private EAnthillColor color;

    private Flow.Subscription subscription;
    private SubmissionPublisher<CMessage> publisher;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // constructor
    public CAnthill(EAnthillColor pAnthillColor, Point pPosition) {
        this.color = pAnthillColor;
        this.position = pPosition;

        this.workers = new ArrayList<>();
        this.commanders = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.nResource = 0;
        this.nPoint = 0;

        this.publisher = new CPublisher();

        // TODO: RENDRE MODULAIRE (SCALE+++)
        // ajout des fourmis
        for(int nbCommanderAnt=0; nbCommanderAnt < CConstants.N_COMMANDER; nbCommanderAnt++) {
            CCommanderAnt ant = new CCommanderAnt(this, nbCommanderAnt);
            ant.setxPos(this.position.x);
            ant.setyPos(this.position.y);
            this.commanders.add(ant);
            this.publisher.subscribe(ant);
            ant.start();
        }
        for(int nbWorkerAnt=0; nbWorkerAnt < CConstants.N_WORKER; nbWorkerAnt++) {
            CWorkerAnt ant = new CWorkerAnt(this, nbWorkerAnt);
            ant.setxPos(this.position.x);
            ant.setyPos(this.position.y);
            this.workers.add(ant);

            if(nbWorkerAnt < 10) {
                this.commanders.get(0).linkWorker(ant);
            } else if(nbWorkerAnt < 20) {
                this.commanders.get(1).linkWorker(ant);
            } else if(nbWorkerAnt < 30) {
                this.commanders.get(2).linkWorker(ant);
            } else if(nbWorkerAnt < 40) {
                this.commanders.get(3).linkWorker(ant);
            } else if(nbWorkerAnt < 50) {
                this.commanders.get(4).linkWorker(ant);
            }
            ant.start();
        }
    }

    // methode thread
    public void run() {
        CMap map = CMap.shared();

        while(!map.getPartieFinie()) {
            // SI tempsRestant > 60 secondes
            // ALORS:   => donner ordre EQueenOrder.FOCUS_FOOD aux commandants dans un rayon de 100
            // SINON
            // ALORS:   => donner ordre EQueenOrder.GO_ANTHILL aux commandants dans un rayon de 100
            // FIN SI
            if(map.getTempsRestant() > 60) {
                CMessage message = new CMessage(EQueenOrder.GO_FIND_RESOURCE, this);
                this.publisher.submit(message);
            } else {
                CMessage message = new CMessage(EQueenOrder.GO_ANTHILL, this);
                this.publisher.submit(message);
            }

            CUtils.wait(50);
        }

    }

    // methode pour ajouter une ressource
    public void addResource(LinkedList<CResource> pResource) {
        synchronized (this.resources) {
            this.resources.addAll(pResource);
            for(int nbResource=0; nbResource < pResource.size(); nbResource++) {
                if(pResource.get(nbResource).getType() == EResourceType.FOOD) {
                    this.nResource++;
                }
                else {
                    this.nPoint++;
                }
            }
        }
    }

    // methode pour tuer (supprimer) une fourmis
    public boolean killAnt(CAnt pAnt) {
        if(pAnt instanceof CCommanderAnt) {
            synchronized (this.commanders) {
                return this.commanders.remove(pAnt);
            }
        } else {
            synchronized (this.workers) {
                return this.workers.remove(pAnt);
            }
        }
    }

    // +--------+
    // | GETTER |
    // +--------+

    // methode qui retourne les workers
    public ArrayList<CWorkerAnt> getWorkers() {
        return this.workers;
    }

    // methode qui retourne les commanders
    public ArrayList<CCommanderAnt> getCommander() {
        return this.commanders;
    }

    // methode qui retourne les ressources
    public int getnResource() {
        return this.nResource;
    }

    // methode qui retourne le nombre de point
    public int getnPoint() {
        return this.nPoint;
    }

    // mehtode qui retourne la couleur de la anthill
    public EAnthillColor getColor() {
        return this.color;
    }

    // methode qui retourne la position
    public Point getPosition() {
        return this.position;
    }

    // +------+
    // | FLOW |
    // +------+
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }
    @Override
    public void onNext(CMessage item) {

    }
    @Override
    public void onError(Throwable throwable) {

    }
    @Override
    public void onComplete() {

    }
}
