import java.awt.*;
import java.util.LinkedList;

// classe mère fourmis
public abstract class CAnt extends Thread {
    public boolean isInjured;
    public boolean isAlive;
    public int xPos;
    public int yPos;
    public int id; // id pour suivre les threads
    public CAnthill anthill;
    public EAnthillColor color;
    public EQueenOrder order;
    public CSubscriber subscriber;
    public LinkedList<Point> pileDeplacement; // contient tous les déplacement de la fourmis
    public LinkedList<CResource> resources;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // contructor
    public CAnt(CAnthill pAnthill, int pId) {
        this.id = pId;

        this.isInjured = false;
        this.isAlive = true;
        this.color = pAnthill.getColor();
        this.anthill = pAnthill;

        // placement de la fourmis à la même position que la anthill
        this.setxPos(this.anthill.getPosition().x);
        this.setyPos(this.anthill.getPosition().y);

        this.order = EQueenOrder.NO_ORDER;
        this.resources = new LinkedList<CResource>();
        this.pileDeplacement = new LinkedList<Point>();
    }

    // methode qui prend une ressource
    public void takeRessource(CResource pRessource) {
        this.resources.add(pRessource);
    }

    // methode qui pose une ressource
    public CResource dropRessource() {
        return this.resources.pollLast();
    }

    // +--------+
    // | SETTER |
    // +--------+

    // methode pour changer le status isInjured de la fourmis
    public void setInjured(boolean pInjured) {
        this.isInjured = pInjured;
    }

    // methode pour change le status isAlive de la fourmis
    public void setIsAlive(boolean pIsAlive) {
        this.isAlive = pIsAlive;
        if(!this.isAlive) this.anthill.killAnt(this);
    }

    // methode qui change la position x de la fourmis
    public void setxPos(int pPos) {
        this.xPos = pPos;
    }

    // methode qui change la position y de la fourmis
    public void setyPos(int pPos) {
        this.yPos = pPos;
    }

    // +--------+
    // | GETTER |
    // +--------+

    // methode qui retourne la positon x de la fourmis
    public int getxPos() {
        return this.xPos;
    }

    // methode qui retourne la position y de la fourmis
    public int getyPos() {
        return this.yPos;
    }

    // methode qui retourne l'id de la fourmis
    public int getAntId() {
        return this.id;
    }

    // methode qui retourne la couleur de la fourmis
    public EAnthillColor getColor() { return this.color; }

}
