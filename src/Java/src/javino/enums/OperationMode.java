package javino.enums;

/** Classe enumerável para as operações que podem ser realizadas pelo Javino. */
public enum OperationMode {

    /** Operação de ouvir. */
    LISTEN("listen"),

    /** Operação para execução de comandos. */
    COMMAND("command"),

    /** Operação de requisição. */
    REQUEST("request");

    /** Nome da operação. */
    private String name;

    /**
     * Construtor.
     *
     * @param name {@link #name}
     */
    OperationMode(String name) {
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
