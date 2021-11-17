package javino.enums;

/** Classe de enumerável para identificação do Sistema Operacional. */
public enum OperationalSystem {

    /** Sistema Operacional Windows. */
    WINDOWS("WINDOWS"),

    /** Sistema Operacional Linux. */
    LINUX("LINUX");

    /** Nome do sistema operacional. */
    private String name;

    /**
     * Construtor.
     *
     * @param name {@link #name}
     */
    OperationalSystem(String name) {
        this.name = name;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
