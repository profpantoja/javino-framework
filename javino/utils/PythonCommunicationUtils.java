package javino.utils;

import javino.model.JavinoConstants;
import javino.model.OperationSystemEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/** Classe de utilidade para a comunicação com o Python. */
public class PythonCommunicationUtils {

    /** Extensão dos arquivos desenvolvidos na línguagem Python. */
    private static final String PYTHON_FILE_EXTENSION = "py";

    /** Extensão dos arquivos travados para escrita. */
    private static final String LOCK_FILE_EXTENSION = "lock";

    /** String de propriedade para recuperar o nome do Sistema Operacional atual. */
    private static final String OPERATION_SYSTEM_NAME_STRING_PROPERTY = "os.name";

    /** Caminho padrão do Python instalado no sistema operacional Windows. */
    private static final String PYTHON_DEFAULT_PATH_OS_WINDOWS = "C:\\Python27";

    /** Nome do arquivo executável do Python no sistema operacional Windows. */
    private static final String PYTHON_FILE_NAME_OS_WINDOWS = "\\python.exe";

    /** Caminho padrão do Python instalado no sistema operacional Windows. */
    private static final String PYTHON_DEFAULT_PATH_OS_LINUX = "/usr/bin";

    /** Nome do arquivo executável do Python no sistema operacional Windows. */
    private static final String PYTHON_FILE_NAME_OS_LINUX = "/python";


    /** Ponto. */
    private static final String DOT = ".";

    /**
     * Procura arquivos com a extensão "py" e "lock" e os deleta na pasta para desbloquear a escrita de um novo.
     */
    private static void unlock() {
        File folder = new File(DOT);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(LOCK_FILE_EXTENSION) || file.getName().endsWith(PYTHON_FILE_EXTENSION)) {
                file.delete();
            }
        }
    }

    /**
     * Executa a identificação do Sistema operacional que o Javino está sendo utilizado.
     * @return {@code #OperationSystemEnum} Enúmeravel para o Sistema Operacional que o Javino trabalha.
     */
    private static OperationSystemEnum whichOperationalSystem() {
        String os = System.getProperty(OPERATION_SYSTEM_NAME_STRING_PROPERTY);
        if (os.startsWith("W")) {
            return OperationSystemEnum.WINDOWS;
        } else {
            return OperationSystemEnum.LINUX;
        }
    }

    /**
     * Executa a recuperação do endereco de onde está instalado o python no sistema operacional.
     *
     * @param os {@code #OperationSystemEnum} Enúmeravel para o Sistema Operacional que o Javino trabalha.
     */
    private static String getPath(OperationSystemEnum os) {
        if (OperationSystemEnum.WINDOWS.equals(os)) {
            return PYTHON_DEFAULT_PATH_OS_WINDOWS + PYTHON_FILE_NAME_OS_WINDOWS;
        } else {
            return PYTHON_DEFAULT_PATH_OS_LINUX + PYTHON_FILE_NAME_OS_LINUX;
        }
    }

    /**
     * Executa a recuperação do endereco de onde está instalado o python no sistema operacional.
     *
     * @param os {@code #OperationSystemEnum} Enúmeravel para o Sistema Operacional que o Javino trabalha.
     * @param specificPath Caminho especificado do arquivo executável.
     */
    private static String getPath(OperationSystemEnum os, String specificPath) {
        if (OperationSystemEnum.WINDOWS.equals(os)) {
            return specificPath + PYTHON_FILE_NAME_OS_WINDOWS;
        } else {
            return specificPath + PYTHON_FILE_NAME_OS_LINUX;
        }
    }

    /**
     * Executa a criação do arquivo javython.py.
     */
    private static void createPythonFile() {
        try {
            FileWriter file = new FileWriter(JavinoConstants.PYTHON_FILE_NAME);
            PrintWriter saveFile = new PrintWriter(file);
            saveFile.printf("import sys\n");
            saveFile.printf("import serial\n");

            saveFile.printf("OP=sys.argv[1]\n");
            saveFile.printf("PORT=sys.argv[2]\n");
            saveFile.printf("MSG=sys.argv[3]\n");

            saveFile.printf("try:\n");
            saveFile.printf("\tcomm = serial.Serial(PORT, 9600)\n");
            saveFile.printf("\tcomm.open\n");
            saveFile.printf("\tcomm.isOpen\n");
            saveFile.printf("\tif(OP=='command'):\n");
            saveFile.printf("\t\tcomm.write(MSG)\n");
            saveFile.printf("\tif(OP=='request'):\n");
            saveFile.printf("\t\tcomm.write(MSG)\n");
            saveFile.printf("\t\tprint (comm.readline())\n");
            saveFile.printf("\tif(OP=='listen'):\n");
            saveFile.printf("\t\tprint (comm.readline())\n");
            saveFile.printf("\tcomm.close\n");

            saveFile.printf("except:\n");
            saveFile.printf("\tprint (\"Error connecting on \"+PORT)\n");
            saveFile.printf("\tsys.exit(1)\n");

            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executa o carregamento inicial para o Python poder estabelecer a comunicação do Java com o Arduino.
     */
    public static String load() {
        unlock();
        System.out.println("[JAVINO] Hello, You are using version " + JavinoConstants.VERSION + " - CEFET/RJ, Brazil");
        final String path = PythonCommunicationUtils.getPath(whichOperationalSystem());
        createPythonFile();
        return path;
    }

    /**
     * Executa o carregamento inicial para o Python poder estabelecer a comunicação do Java com o Arduino.
     * @param specificPath Caminho específico do arquivo executável do Python.
     */
    public static String load(String specificPath) {
        unlock();
        System.out.println("[JAVINO] Hello, You are using version " + JavinoConstants.VERSION + " - CEFET/RJ, Brazil");
        final String path = getPath(whichOperationalSystem(), specificPath);
        createPythonFile();
        return path;
    }

    /**
     * Executa a recuperação do arquivo lock de escrita.
     *
     * @param PORT Porta serial de comunicação.
     * @return Arquivo de escrita na parta de comunicação com o Arduino.
     */
    private static String lockFileName(String PORT) {
        PORT = PORT + DOT + LOCK_FILE_EXTENSION;
        PORT = PORT.replace("/", "_");
        PORT = PORT.replace("1", "i");
        PORT = PORT.replace("2", "ii");
        PORT = PORT.replace("3", "iii");
        PORT = PORT.replace("4", "iv");
        PORT = PORT.replace("5", "v");
        PORT = PORT.replace("6", "vi");
        PORT = PORT.replace("7", "vii");
        PORT = PORT.replace("8", "viii");
        PORT = PORT.replace("9", "ix");
        PORT = PORT.replace("0", "x");
        return PORT;
    }

    /**
     * Executa a verificação para identificar se a porta está está ocupada ou livre para uso.
     * @param lc_PORT Caminho da Porta.
     * @return {@code #true} Caso a porta esteja Ocupada e {@code #false} caso esteja Livre.
     */
    public static boolean portLocked(String lc_PORT) {
        boolean busy;
        File file = new File(lockFileName(lc_PORT));
        if (file.exists()) {
            busy = true;
            System.out.println("[JAVINO] The port " + lc_PORT + " is busy!");
        } else {
            busy = false;
        }
        return busy;
    }

    /**
     * Executa o trancamento ou destrancamento da porta, criando ou excluindo o arquivo para comunicação do Java com
     * o Arduino.
     * @param lock {@code #true} Destranca a porta para a comunicação e {@code #false} Tranca a porta de comunicação.
     * @param PORT Caminho da porta.
     */
    public static void lockPort(boolean lock, String PORT) {
        try {
            File file = new File(lockFileName(PORT));
            if (lock) {
                file.createNewFile();
            } else {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
