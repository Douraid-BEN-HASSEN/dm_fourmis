import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class CAnthill extends Thread implements Flow.Subscriber<CMessage> {
    private ArrayList<CWorkerAnt> workers;
    private ArrayList<CCommanderAnt> commanders;
    private ArrayList<CResource> resources;
    private EAnthillColor color;

    private int nResource;
    private int nPoint;

    private Flow.Subscription subscription;
    private SubmissionPublisher<CMessage> publisher;

    private Point position;

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
        for(int nbCommanderAnt=0; nbCommanderAnt < 5; nbCommanderAnt++) {
            CCommanderAnt ant = new CCommanderAnt(this, nbCommanderAnt);
            ant.setxPos(this.position.x);
            ant.setyPos(this.position.y);
            this.commanders.add(ant);
            this.publisher.subscribe(ant);
            ant.start();
        }
        for(int nbWorkerAnt=0; nbWorkerAnt < 50; nbWorkerAnt++) {
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

    public void run() {
        CMap map = CMap.shared();

        while(!map.getPartieFinie()) {
            // SI tempsRestant > 60 secondes
            // ALORS:   => donner ordre EQueenOrder.FOCUS_FOOD aux commandants dans un rayon de 100
            // SINON
            // ALORS:   => donner ordre EQueenOrder.GO_ANTHILL aux commandants dans un rayon de 100
            // FIN SI
            if(map.getTempsRestant() > 60) {
                CMessage message = new CMessage(EQueenOrder.GO_FIND_RESOURCE);
                this.publisher.submit(message);
            } else {
                CMessage message = new CMessage(EQueenOrder.GO_ANTHILL);
                this.publisher.submit(message);
            }

            CUtils.wait(50);
        }

    }

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

    public CAnt killAnt(CAnt pAnt) {
        int index = this.commanders.indexOf(pAnt);
        if(index > -1) {
            return this.commanders.remove(index);
        } else {
            index = this.workers.indexOf(pAnt);
            return this.workers.remove(index);
        }
    }

    public ArrayList<CWorkerAnt> getWorkers() {
        return this.workers;
    }

    public ArrayList<CCommanderAnt> getCommander() {
        return this.commanders;
    }

    public int getnResource() {
        return this.nResource;
    }

    public int getnPoint() {
        return this.nPoint;
    }

    public EAnthillColor getColor() {
        return this.color;
    }

    public Point getPosition() {
        return this.position;
    }
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
