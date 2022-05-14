import java.awt.*;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class CCommanderAnt extends CAnt implements Flow.Subscriber<CMessage> {

    private Flow.Subscription subscription;
    private SubmissionPublisher<CMessage> publisher;

    public CCommanderAnt(CAnthill pAnthill, int pId) {
        super(pAnthill, pId);
        this.publisher = new CPublisher();
        this.pileDeplacement.add(new Point(this.getxPos(),this.getyPos()));
    }

    public void update() {

    }

    public void run() {
        CMap map = CMap.shared();

        while(this.isAlive && !map.getPartieFinie()) {
            // recevoir des ordres de la reine (subscribe)
            // donner ordres au ouvrier
            if(this.order == EQueenOrder.GO_FIND_RESOURCE) {
                int randResourceType = CUtils.getRandom(0, 2);
                CMessage message;

                if(randResourceType == 0) message = new CMessage(EQueenOrder.FOCUS_FOOD, this);
                else if(randResourceType == 1) message = new CMessage(EQueenOrder.FOCUS_POINT, this);
                else message = new CMessage(EQueenOrder.FOCUS_ALL, this);

                this.publisher.submit(message);
                this.deplacementAleatoire();

            } else if(this.order == EQueenOrder.GO_ANTHILL) {
                CMessage message = new CMessage(EQueenOrder.GO_ANTHILL, this);
                this.publisher.submit(message);
                // => se deplacer jusqu'à la anthill
                this.depilerDeplacement();
            }
            // SI fourmis autre couleur sur la même case
            // ALORS:   => combat
            // FIN SI
            //...

            CUtils.wait(50);
        }

        while (this.pileDeplacement.size() > 0) {
            this.depilerDeplacement();
            CUtils.wait(50);
        }

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(2);
    }

    @Override
    public void onNext(CMessage pMessage) {
        // distance < 50
        int distanceX = this.getxPos() - pMessage.getAnthill().getPosition().x >= 0 ? this.getxPos() - pMessage.getAnthill().getPosition().x : (this.getxPos() - pMessage.getAnthill().getPosition().x)*-1;
        int distanceY = this.getyPos() - pMessage.getAnthill().getPosition().y >= 0 ? this.getyPos() - pMessage.getAnthill().getPosition().y : (this.getyPos() - pMessage.getAnthill().getPosition().y)*-1;

        if(distanceX <= 50 && distanceY <= 50) {
            this.order = pMessage.getOrder();
        }
        subscription.request(2);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

    public void linkWorker(CWorkerAnt pAnt) {
        this.publisher.subscribe(pAnt);
    }

    private void deplacementAleatoire() {
        int direction;
        boolean deplacement = true;

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
                CMap.shared().moveTo(this, tile.getxPos(), tile.getyPos());
                this.pileDeplacement.add(new Point(this.getxPos(),this.getyPos()));
            }
        }
    }

    private void depilerDeplacement() {
        if(this.pileDeplacement.size() > 0) {
            Point point = this.pileDeplacement.pollLast();
            CMap.shared().moveTo(this, point.x, point.y);
        }
    }

    private void fight() {
        // SI fourmis autre couleur
        // ALORS:   SI fourmisAdverse = commandant
        //          ALORS:  SI isInjured = true ET fourmisAdverse.isInjured = false
        //                  ALORS:  => isAlive = false (on se fait tuer)
        //                  SINON SI isInjured = false ET fourmisAdverse.isInjured = true
        //                  ALORS:  => foursAdverse.isAlive = false (on tue la fourmis)
        //                          => isInjured = true
        //                  SINON
        //                  ALORS:  => combat au hasard
        //                  FIN SI
        //          SINON
        //          ALORS:  => foursAdverse.isAlive = false (on tue la fourmis)
        //          FIN SI
        // FIN SI
    }
}
