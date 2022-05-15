// class message pour subscriber/publisher
public class CMessage {
    private EQueenOrder order;
    private CAnthill anthill;
    private CCommanderAnt commander;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // constructor avec CAnthill
    CMessage(EQueenOrder pOrder, CAnthill pAnthill) {
        this.order = pOrder;
        this.anthill = pAnthill;
    }

    // constructor avec CCommanderAnt
    CMessage(EQueenOrder pOrder, CCommanderAnt pCommander) {
        this.order = pOrder;
        this.commander = pCommander;
    }

    // +--------+
    // | GETTER |
    // +--------+

    // methode qui retourne l'ordre
    public EQueenOrder getOrder() {
        return this.order;
    }

    // methode qui retourne la anthill
    public CAnthill getAnthill() {
        return this.anthill;
    }

    // methode qui retourne le commander
    public CCommanderAnt getCommander() {
        return this.commander;
    }

}
