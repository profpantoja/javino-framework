package Java.br.pro.turing.javino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Javino {
	private final String version = staticVersion;
	private static final String staticVersion = "1.2";
	private String pythonPlataform;
	private String finalMsg = null;

	public Javino() {
		load();
	}

	public Javino(String pathPython) {
		load();
		if (whichOperationalSystem() == 1) {
			this.pythonPlataform = pathPython + "\\python.exe";
		} else {
			this.pythonPlataform = pathPython + "/python";
		}
	}

	private void load() {
		unlock();
		System.out.println("[JAVINO] Hello, You are using version " + this.version
				+ " - CEFET/RJ, Brazil");
		this.setPath(whichOperationalSystem());
		this.createPythonFile();
	}

	private void setPath(int sp_OS) {
		if (sp_OS == 1) {
			this.pythonPlataform = "C:\\Python27\\python.exe";
		} else {
			this.pythonPlataform = "/usr/bin/python";
		}
	}

	private int whichOperationalSystem() {
		// Linux = 0
		// Windows = 1
		String os = System.getProperty("os.name");
		if (os.substring(0, 1).equals("W")) {
			return 1;
		} else {
			return 0;
		}
	}

	private void unlock() {
		File folder = new File(".");
		File[] files = folder.listFiles();

		for (File file : files) {
			if (file.getName().endsWith("lock")
					|| file.getName().endsWith("py")) {
				file.delete();
			}
		}
	}

	private String lockFileName(String PORT) {
		PORT = PORT + ".lock";
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

	private boolean portLocked(String lc_PORT) {
		boolean busy;
		File file = new File(lockFileName(lc_PORT));
		if (file.exists()) {
			busy = true;
			System.out.println("[JAVINO] The port " + lc_PORT + " is busy");
		} else {
			busy = false;
		}
		return busy;
	}

	private void lockPort(boolean lock, String PORT) {
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

	public boolean sendCommand(String PORT, String MSG) {
		boolean result;
		if (portLocked(PORT)) {
			result = false;
		} else {
			lockPort(true, PORT);
			String operation = "command";
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = "javython.py";
			command[2] = operation;
			command[3] = PORT;
			command[4] = prepareToSend(MSG);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader output = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = true;
					lockPort(false, PORT);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					lockPort(false, PORT);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				lockPort(false, PORT);
			}
		}
		return result;

	}
	
	public boolean listenArduino(String PORT){
			boolean result;
		if (portLocked(PORT)) {
			result = false;
		} else {
			lockPort(true, PORT);
			String operation = "listen";
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = "javython.py";
			command[2] = operation;
			command[3] = PORT;
			command[4] = "listen";
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = setArryMsg(array);
					lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening the microcontrolled board.");
				e.printStackTrace();
				result = false;
				lockPort(false, PORT);
			}
		}		
		return result;		
	}
	
	public boolean requestData(String PORT, String MSG) {
		boolean result;
		if (portLocked(PORT)) {
			result = false;
		} else {
			lockPort(true, PORT);
			String operation = "request";
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = "javython.py";
			command[2] = operation;
			command[3] = PORT;
			command[4] = prepareToSend(MSG);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = setArryMsg(array);
					lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening!");
				e.printStackTrace();
				result = false;
				lockPort(false, PORT);
			}
		}
		return result;
	}

	public String getData() {
		String output = this.finalMsg;
		this.finalMsg = null;
		return output;
	}
		
	private boolean setArryMsg(BufferedReader reader) {
		String line = null;
		String out = new String();
		try {
			while ((line = reader.readLine()) != null) {
				out = out + line;
			}
		} catch (IOException e) {
			System.out.println("[JAVINO] Error in message processing.");
			return false;
		}
		return preamble(out.toCharArray());
	}

	private String char2String(char in[], int sizein) {
		int newsize = sizein - 6;
		char[] output = new char[newsize];
		int cont = 0;
		for (int i = 6; i < sizein; i++) {
			output[cont] = in[i];
			cont++;
		}
		return String.valueOf(output);
	}

	private void setFinalMsg(String s_msg) {
		this.finalMsg = s_msg;
	}

	private boolean preamble(char[] preArrayMsg) {
		try {
			char p1 = preArrayMsg[0];
			char p2 = preArrayMsg[1];
			char p3 = preArrayMsg[2];
			char p4 = preArrayMsg[3];
			if ((p1 == 'f')
					&& (p2 == 'f')
					&& (p3 == 'f')
					&& (p4 == 'e')
					&& (this.monitorMsg(forInt(preArrayMsg[5]),
							forInt(preArrayMsg[4]), preArrayMsg.length))) {
				setFinalMsg(char2String(preArrayMsg, preArrayMsg.length));
				return true;
			} else {
				char[] newArrayMsg;
				newArrayMsg = new char[(preArrayMsg.length - 1)];
				for (int cont = 0; cont < newArrayMsg.length; cont++) {
					newArrayMsg[cont] = preArrayMsg[cont + 1];
				}
				return this.preamble(newArrayMsg);
			}
		} catch (Exception ex) {
			System.out.println("[JAVINO] Invalid message.");
			ex.printStackTrace();
			return false;
		}
	}

	private boolean monitorMsg(int x, int y, int m_size) {
		int converted = x + (y * 16);
		int size_of_msg = m_size - 6;
		if (converted == size_of_msg) {
			return true;
		}
		return false;
	}

	private int forInt(char v) {
		int vI = 0;
		switch (v) {
		case '1':
			vI = 1;
			break;
		case '2':
			vI = 2;
			break;
		case '3':
			vI = 3;
			break;
		case '4':
			vI = 4;
			break;
		case '5':
			vI = 5;
			break;
		case '6':
			vI = 6;
			break;
		case '7':
			vI = 7;
			break;
		case '8':
			vI = 8;
			break;
		case '9':
			vI = 9;
			break;
		case 'a':
			vI = 10;
			break;
		case 'b':
			vI = 11;
			break;
		case 'c':
			vI = 12;
			break;
		case 'd':
			vI = 13;
			break;
		case 'e':
			vI = 14;
			break;
		case 'f':
			vI = 15;
			break;
		}
		return vI;
	}

	private String prepareToSend(String msg) {
		msg = "fffe" + int2Hex(msg.length()) + msg;
		return msg;
	}

	private String int2Hex(int v) {
		String stringOne = Integer.toHexString(v);
		if (v < 16) {
			stringOne = "0" + stringOne;
		}
		return stringOne;
	}

	private void createPythonFile() {
		try {
			FileWriter file = new FileWriter("javython.py");
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
			saveFile.printf("\tprint (\"Error on conect \"+PORT)\n");
			saveFile.printf("\tsys.exit(1)\n");

			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String args[]) {
		try {
			String type = args[0];
			if (type.equals("--help")) {
				System.out
						.println("java -jar javino.jar [TYPE] [PORT] [MSG] [PythonPath]");
				System.out
						.println("\n[TYPE] "
								+ "\n request -- send a request to Arduino, wait answer "
								+ "\n command -- send a command to Arduino, without wait answer");
								
				System.out.println("\n[PORT]"
						+ "\n Set communication serial port"
						+ "\n example: \t COM3 - For Windows"
						+ "\n \t\t /dev/ttyACM0 - For Linux");
				System.out.println("\n[MSG]" + "\n Message for Arduino-side"
						+ "\n example: \t \"Hello Arduino!\"");
				System.out
						.println("\n[PythonPath]"
								+ "\n Set Path of Python in your system. This is a optional value."
								+ "example: \n\t \"C:\\\\Python27\" - For a System Windows"
								+ "\n\t \"/usr/bin\" - For a System Linux");
			} else {

				String port = args[1];
				String msg = args[2];
				String path = null;
				try {
					path = args[3];
				} catch (Exception ex) {
					System.out
							.println("[JAVINO] Using default path 'C:\\Python27' or '/usr/bin'");
				}

				Javino j;
				if (path == null) {
					j = new Javino();
				} else {
					j = new Javino(path);
				}

				if (type.equals("command")) {
					if (j.sendCommand(port, msg) == false) {
						System.exit(1);
					}
				} else if (type.equals("request")) {
					if (j.requestData(port, msg) == true) {
						System.out.println(j.getData());
					} else {
						System.exit(1);
					}
				}
			}

		} catch (Exception ex) {
			System.out.println("[JAVINO] Using version " + staticVersion
					+ " CEFET/RJ, Brazil");
			System.out
					.println("\tTo use Javino, look for the User Manual at http://javino.sf.net");
			System.out
					.println("For more information try: \n\t java -jar javino.jar --help");

			// ex.printStackTrace();
		}

	}

}