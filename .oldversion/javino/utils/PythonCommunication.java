package javino.utils;

import javino.constants.JavinoConstants;
import javino.enums.OperationalSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/** Utility class for communicating with Python. */
public class PythonCommunication {

    /** Extension of files developed in the Python language. */
    private static final String PYTHON_FILE_EXTENSION = "py";

    /** Extension of locked files for writing. */
    private static final String LOCK_FILE_EXTENSION = "lock";

    /** Property String to retrieve the name of the current Operating System. */
    private static final String OPERATION_SYSTEM_NAME_STRING_PROPERTY = "os.name";

    /** Standard Python path installed on the Windows operating system. */
    private static final String PYTHON_DEFAULT_PATH_OS_WINDOWS = "C:\\Python27";

    /** Python executable file name on the Windows operating system. */
    private static final String PYTHON_FILE_NAME_OS_WINDOWS = "\\python.exe";

    /** Standard Python path installed on the Windows operating system. */
    private static final String PYTHON_DEFAULT_PATH_OS_LINUX = "/usr/bin";

    /** Python executable file name on the Windows operating system. */
    private static final String PYTHON_FILE_NAME_OS_LINUX = "/python";

    /** Dot. */
    private static final String DOT = ".";

    /**
     * Searches for files with the extension "py" and "lock" and deletes them in the folder to unlock the writing of
     * a new one.
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
     * Performs the identification of the Operating System that Javino is being used.
     *
     * @return {@code #OperationSystemEnum} Enumerable for the Operating System that Javino works with.
     */
    private static OperationalSystem whichOperationalSystem() {
        String os = System.getProperty(OPERATION_SYSTEM_NAME_STRING_PROPERTY);
        if (os.startsWith(OperationalSystem.WINDOWS.getName().substring(0,1))) {
            return OperationalSystem.WINDOWS;
        } else {
            return OperationalSystem.LINUX;
        }
    }

    /**
     * Performs the recovery of the address where python is installed in the operating system.
     *
     * @param os {@code #OperationSystemEnum} Enumerable for the Operating System that Javino works with.
     */
    private static String getPath(OperationalSystem os) {
        if (OperationalSystem.WINDOWS.equals(os)) {
            return PYTHON_DEFAULT_PATH_OS_WINDOWS + PYTHON_FILE_NAME_OS_WINDOWS;
        } else {
            return PYTHON_DEFAULT_PATH_OS_LINUX + PYTHON_FILE_NAME_OS_LINUX;
        }
    }

    /**
     * Performs the recovery of the address where python is installed in the operating system.
     *
     * @param os           {@code #OperationSystemEnum} Enumerable for the Operating System that Javino works with.
     * @param specificPath Specified path of the executable file.
     */
    private static String getPath(OperationalSystem os, String specificPath) {
        if (OperationalSystem.WINDOWS.equals(os)) {
            return specificPath + PYTHON_FILE_NAME_OS_WINDOWS;
        } else {
            return specificPath + PYTHON_FILE_NAME_OS_LINUX;
        }
    }

    /**
     * Performs the creation of the javython.py file.
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
     * Performs the initial load for Python to be able to establish Java communication with Arduino.
     */
    public static String load() {
        unlock();
        System.out.println("[JAVINO] Hello, You are using version " + JavinoConstants.VERSION + " - CEFET/RJ, Brazil");
        final String path = PythonCommunication.getPath(whichOperationalSystem());
        createPythonFile();
        return path;
    }

    /**
     * Performs the initial load for Python to be able to establish Java communication with Arduino.
     *
     * @param specificPath Specific path to the Python executable file.
     */
    public static String load(String specificPath) {
        unlock();
        System.out.println("[JAVINO] Hello, You are using version " + JavinoConstants.VERSION + " - CEFET/RJ, Brazil");
        final String path = getPath(whichOperationalSystem(), specificPath);
        createPythonFile();
        return path;
    }

    /**
     * Performs recovery of the write lock file.
     *
     * @param PORT Serial communication port.
     * @return {@code #String} Writing file in the communication part with Arduino.
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
     * Run the check to identify whether the port is occupied or free for use.
     *
     * @param lc_PORT Path to the Door.
     * @return {@code #true} If the port is Busy and {@code #false} if it is Free.
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
     * Performs the locking or unlocking of the port, creating or deleting the file for communication between Java
     * and Arduino.
     *
     * @param lock {@code #true} Unlock the communication port and {@code #false} Lock the communication port.
     * @param PORT port Path.
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
