package jason.javino.constants;

/** Classe para centralizar todas as constantes do código do Javino. */
public class JavinoConstants {

    /** Nome do arquivo do python criado na comunicação entre o Java e o Arduino. */
    public static final String PYTHON_FILE_NAME = System.getenv("JAVINO")+"\\javino.py";

    /** Versão atual do Javino. */
    public static final String VERSION = "2.0";

    /** Javino idetifier for messages. */
    public static final String JAVINO_IDENTIFIER = "[Javino] ";

    /** Minimum amount of message characters. */
    public static final int MESSAGE_MIN_SIZE = 64;

    /** Maximum amount of message characters. */
    public static final int MESSAGE_MAX_SIZE = 512;

    /** Maximum amount of message characters in bits. */
    public static final int MESSAGE_MAX_SIZE_IN_BIT = MESSAGE_MAX_SIZE * Byte.SIZE;
}
