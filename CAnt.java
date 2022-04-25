public abstract class CAnt {
    public boolean isInjured = false;
    public EAnthillColor color;

    public CAnt(EAnthillColor pAnthillColor) {
        this.color = pAnthillColor;
    }

    public void setInjured(boolean pInjured) {
        this.isInjured = pInjured;
    }

}
