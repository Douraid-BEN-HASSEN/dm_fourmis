public abstract class CAnt extends Thread {
    public boolean isInjured = false;
    public EAnthillColor color;

    public int xPos;
    public int yPos;

    public int id;

    public CAnt(EAnthillColor pAnthillColor, int pId) {
        this.color = pAnthillColor;
        this.id = pId;
    }

    public void setInjured(boolean pInjured) {
        this.isInjured = pInjured;
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

}
