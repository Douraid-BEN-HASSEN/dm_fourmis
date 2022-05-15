import java.awt.*;
import java.util.concurrent.Flow;
public class CWorkerAnt extends CAnt implements Flow.Subscriber<CMessage> {
    private Flow.Subscription subscription;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // constructor
    public CWorkerAnt(CAnthill pAnthill, int pId) {
        super(pAnthill, pId);
        this.pileDeplacement.add(new Point(this.getxPos(),this.getyPos()));
    }

    // methode run
    public void run() {
        // recevoir ordre d'un comandant
        // executer les ordres
        // si fourmis sur la même case, alors combat

        // SI fourmis sur la même case
        // ALORS:   => combatre
        // SINON SI ordre = FOCUS_FOOD
        // ALORS:   => se deplacer pour trouver des ressources
        // SINON SI order = FOCUS_POINT
        // ALORS:   => se deplacer au point indiqué
        // FIN SI

        CMap map = CMap.shared();

        while (this.isAlive && !map.getPartieFinie()) {
            if(this.order == EQueenOrder.GO_ANTHILL) { // || this.resources.size() == CConstants.MAX_RESSOURCES
                this.depilerDeplacement();
            } else if((this.order == EQueenOrder.FOCUS_FOOD || this.order == EQueenOrder.FOCUS_POINT || this.order == EQueenOrder.FOCUS_ALL)
                    && this.resources.size() < CConstants.MAX_RESSOURCES) {
                this.deplacementAleatoire();
            }
            CUtils.wait(50);
        }

        while (this.pileDeplacement.size() > 0) {
            this.depilerDeplacement();
            CUtils.wait(50);
        }
    }

    // methode pour se deplacer aleatoirement
    private void deplacementAleatoire() {
        CMap map = CMap.shared();

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
                    tile = map.getRightTile(this);
                    if(tile == null) deplacement = true;
                    break;
                case 1:
                    // LEFT
                    tile = map.getLeftTile(this);
                    if(tile == null) deplacement = true;
                    break;
                case 2:
                    // BOTTOM
                    tile = map.getBottomTile(this);
                    if(tile == null) deplacement = true;
                    break;
                case 3:
                    // TOP
                    tile = map.getTopTile(this);
                    if(tile == null) deplacement = true;
                    break;
            }

            if(!deplacement) {
                map.moveTo(this, tile.getxPos(), tile.getyPos());
                this.pileDeplacement.add(new Point(this.getxPos(),this.getyPos()));

                // prendre la ressource uniquement si on est pas dans la anthill
                if(tile.getxPos() != this.anthill.getPosition().x && this.getyPos() != this.anthill.getPosition().y) {
                    if(this.order == EQueenOrder.FOCUS_FOOD && tile.getResourceType() == EResourceType.FOOD && tile.getTileResource() > 0) {
                        CResource ressource = tile.takeResource();
                        if(ressource != null) this.takeRessource(ressource);
                    } else if(this.order == EQueenOrder.FOCUS_POINT && tile.getResourceType() == EResourceType.POINT && tile.getTileResource() > 0) {
                        CResource ressource = tile.takeResource();
                        if(ressource != null) this.takeRessource(ressource);
                    }
                }

            }
        }
    }

    // methode pour revenir sur ses deplacements
    private void depilerDeplacement() {
        if(this.pileDeplacement.size() > 0) {
            CMap map = CMap.shared();
            Point point = this.pileDeplacement.pollLast();
            map.moveTo(this, point.x, point.y);

            // SI fourmis sur dans la fourmilière (pile finie)
            // ALORS:   => drop/ajoute chaque ressource 1 par 1
            // FIN SI
            if(this.pileDeplacement.size() == 0) {
                if(this.resources.size() > 0) this.anthill.addResource(this.resources);

                CTile tile = map.getTile(this.getxPos(), this.getyPos());
                int nResource = this.resources.size();
                for(int nbRessources=0; nbRessources < nResource; nbRessources++) {
                    tile.dropResource(this.resources.pollLast(), this);
                }
            }
        }
    }

    // methode de gestion des combats
    private void gestionCombat() {
        // SI fourmis autre couleur
        // ALORS:   SI fourmisAdverse = commandant
        //          ALORS:  ALORS:  => isAlive = false (on se fait tuer)
        //                  FIN SI
        //          SINON
        //          ALORS:  SI isInjured = false ET fourmisAdverse.isInjured = true
        //                  ALORS:  => foursAdverse.isAlive = false (on tue la fourmis)
        //                  SINON SI isInjured = true ET fourmisAdverse.isInjured = false
        //                  ALORS:  => isAlive = false (on se fait tuer)
        //                          => foursAdverse.isInjured = true
        //                  SINON:
        //                  ALORS: => combat au hasard
        //                  FIN SI
        //          FIN SI
        // FIN SI

    }

    // +------+
    // | FLOW |
    // +------+
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(2);
    }
    @Override
    public void onNext(CMessage pMessage) {
        // distance < 100
        int distanceX = this.getxPos() - pMessage.getCommander().getxPos() >= 0 ? this.getxPos() - pMessage.getCommander().getxPos() : (this.getxPos() - pMessage.getCommander().getxPos())*-1;
        int distanceY = this.getyPos() - pMessage.getCommander().getyPos() >= 0 ? this.getyPos() - pMessage.getCommander().getyPos() : (this.getyPos() - pMessage.getCommander().getyPos())*-1;

        if(distanceX <= 100 && distanceY <= 100) {
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

}
