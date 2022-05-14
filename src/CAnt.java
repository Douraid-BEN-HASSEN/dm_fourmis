import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class CAnt extends Thread {
    public boolean isInjured;
    public boolean isAlive;

    public CAnthill anthill;
    public EAnthillColor color;
    public int xPos;
    public int yPos;
    public int id;
    public EQueenOrder order;

    public Point focusPoint;

    public CSubscriber subscriber;

    public LinkedList<Point> pileDeplacement;

    public LinkedList<CResource> resources;

    public CAnt(CAnthill pAnthill, int pId) {
        this.isInjured = false;
        this.isAlive = true;
        this.color = pAnthill.getColor();
        this.anthill = pAnthill;
        this.setxPos(this.anthill.getPosition().x);
        this.setyPos(this.anthill.getPosition().y);
        this.id = pId;
        this.order = EQueenOrder.NO_ORDER;
        this.resources = new LinkedList<CResource>();
        this.pileDeplacement = new LinkedList<Point>();
    }

    public void setInjured(boolean pInjured) {
        this.isInjured = pInjured;
    }

    public void setOrder(EAnthillColor pColor, EQueenOrder pOrder) {
        if(this.color == pColor) this.order = pOrder;
    }

    public void setxPos(int pPos) {
        this.xPos = pPos;
    }

    public void setyPos(int pPos) {
        this.yPos = pPos;
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

    public int getAntId() {
        return this.id;
    }

    public EAnthillColor getColor() { return this.color; }

    public void setPoint(int pX, int pY) {
        this.focusPoint = new Point(pX, pX);
    }

    public void takeRessource(CResource pRessource) {
        this.resources.add(pRessource);
    }

    public CResource dropRessource() {
        return this.resources.pollLast();
    }
}
