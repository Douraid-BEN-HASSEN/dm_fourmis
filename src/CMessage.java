public class CMessage {
    private EQueenOrder order;
    private CAnthill anthill;
    private CCommanderAnt commander;

    CMessage(EQueenOrder pOrder, CAnthill pAnthill) {
        this.order = pOrder;
        this.anthill = pAnthill;
    }

    CMessage(EQueenOrder pOrder, CCommanderAnt pCommander) {
        this.order = pOrder;
        this.commander = pCommander;
    }

    public EQueenOrder getOrder() {
        return this.order;
    }

    public CAnthill getAnthill() {
        return this.anthill;
    }

    public CCommanderAnt getCommander() {
        return this.commander;
    }
}
