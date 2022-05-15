// classe qui represente les ressources
public class CResource {
    private EResourceType type;

    // +-----------+
    // | CLASS FCT |
    // +-----------+

    // constructor
    public CResource(EResourceType pType) {
        this.type = pType;
    }

    // +--------+
    // | GETTER |
    // +--------+

    // methode qui retourne le type de la ressource
    public EResourceType getType() {
        return this.type;
    }


}
