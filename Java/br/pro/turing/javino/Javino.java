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
		System.out.println("[JAVINO] Hello, You are using version " + this.version + " - CEFET/RJ, Brazil");
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
			if (file.getName().endsWith("lock") || file.getName().endsWith("py")) {
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
			System.out.println("[JAVINO] The port " + lc_PORT + " is busy!");
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

	public String decodeDiffusion(String message) {
		String content = new String();
		/*
		 * Para implementar a difusão. Caso mude todo o Javino para 64, bastaria chamar
		 * a decodificação modificada novamente. Retornaríamos só sender e o content
		 */
		return content;
	}

	public boolean diffuse(String port, String receiver, String content) {
		if (port != null) {
			if (receiver != null) {
				if (content != null) {
					String diffusionMessage = receiver + this.getMsgSizeInB64(content.length()) + content;
					if (diffusionMessage.length() > 64)
						return this.sendCommand(port, diffusionMessage);
					else
						System.out.println("[JAVINO] The message must have at most 64 characters "
								+ "including sender, receiver, and the content.");
				} else
					System.out.println("[JAVINO] The message content cannot be null!");
			} else
				System.out.println("[JAVINO] The receiver cannot be null!");
		} else
			System.out.println("[JAVINO] The port cannot be null!");
		return false;
	}

	public boolean sendCommand(String port, String message) {
		boolean result;
		if (portLocked(port)) {
			result = false;
		} else {
			lockPort(true, port);
			String operation = "command";
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = "javython.py";
			command[2] = operation;
			command[3] = port;
			command[4] = prepareToSend(message);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = true;
					lockPort(false, port);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					lockPort(false, port);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				lockPort(false, port);
			}
		}
		return result;

	}

	public boolean listenController(String PORT) {
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
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
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
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
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

	private void setFinalMsg(String s_msg) {
		this.finalMsg = s_msg;
	}

	private boolean preamble(char[] preArrayMsg) {
		try {
			char p1 = preArrayMsg[0];
			char p2 = preArrayMsg[1];
			char p3 = preArrayMsg[2];
			char p4 = preArrayMsg[3];
			if ((p1 == 'f') && (p2 == 'f') && (p3 == 'f') && (p4 == 'e')
					&& (this.monitorMsg(hexToInt(preArrayMsg[5]), hexToInt(preArrayMsg[4]), preArrayMsg.length))) {
				setFinalMsg(charToString(preArrayMsg, preArrayMsg.length));
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

	private String prepareToSend(String msg) {
		msg = "fffe" + intToHex(msg.length()) + msg;
		return msg;
	}

	private int hexToInt(char v) {
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

	private String charToString(char in[], int sizein) {
		int newsize = sizein - 6;
		char[] output = new char[newsize];
		int cont = 0;
		for (int i = 6; i < sizein; i++) {
			output[cont] = in[i];
			cont++;
		}
		return String.valueOf(output);
	}
	
	private String getMsgSizeInB64(int msgSize) {
		int msgInBits = msgSize * 8;
		return intToB64(msgInBits);
	}

	private String intToB64(int amount) {
		String vI = "A";
		switch (amount) {
			case 0:
				vI = "A";
				break;
			case 1:
				vI = "B";
				break;
			case 2:
				vI = "C";
				break;
			case 3:
				vI = "D";
				break;
			case 4:
				vI = "E";
				break;
			case 5:
				vI = "F";
				break;
			case 6:
				vI = "G";
				break;
			case 7:
				vI = "H";
				break;
			case 8:
				vI = "I";
				break;
			case 9:
				vI = "J";
				break;
			case 10:
				vI = "K";
				break;
			case 11:
				vI = "L";
				break;
			case 12:
				vI = "M";
				break;
			case 13:
				vI = "N";
				break;
			case 14:
				vI = "O";
				break;
			case 15:
				vI = "P";
				break;
			case 16:
				vI = "Q";
				break;
			case 17:
				vI = "R";
				break;
			case 18:
				vI = "S";
				break;
			case 19:
				vI = "T";
				break;
			case 20:
				vI = "U";
				break;
			case 21:
				vI = "V";
				break;
			case 22:
				vI = "W";
				break;
			case 23:
				vI = "X";
				break;
			case 24:
				vI = "Y";
				break;
			case 25:
				vI = "Z";
				break;
			case 26:
				vI = "a";
				break;
			case 27:
				vI = "b";
				break;
			case 28:
				vI = "c";
				break;
			case 29:
				vI = "d";
				break;
			case 30:
				vI = "e";
				break;
			case 31:
				vI = "f";
				break;
			case 32:
				vI = "g";
				break;
			case 33:
				vI = "h";
				break;
			case 34:
				vI = "i";
				break;
			case 35:
				vI = "j";
				break;
			case 36:
				vI = "k";
				break;
			case 37:
				vI = "l";
				break;
			case 38:
				vI = "m";
				break;
			case 39:
				vI = "n";
				break;
			case 40:
				vI = "o";
				break;
			case 41:
				vI = "p";
				break;
			case 42:
				vI = "q";
				break;
			case 43:
				vI = "r";
				break;
			case 44:
				vI = "s";
				break;
			case 45:
				vI = "t";
				break;
			case 46:
				vI = "u";
				break;
			case 47:
				vI = "v";
				break;
			case 48:
				vI = "w";
				break;
			case 49:
				vI = "x";
				break;
			case 50:
				vI = "y";
				break;
			case 51:
				vI = "z";
				break;
			case 52:
				vI = "0";
				break;
			case 53:
				vI = "1";
				break;
			case 54:
				vI = "2";
				break;
			case 55:
				vI = "3";
				break;
			case 56:
				vI = "4";
				break;
			case 57:
				vI = "5";
				break;
			case 58:
				vI = "6";
				break;
			case 59:
				vI = "7";
				break;
			case 60:
				vI = "8";
				break;
			case 61:
				vI = "9";
				break;
			case 62:
				vI = "+";
				break;
			case 63:
				vI = "/";
				break;
		}
		return vI;
	}

	private int b64ToInt(char character) {
		int vI = 0;
		switch (character) {
			case 'A':
				vI = 0;
				break;
			case 'B':
				vI = 1;
				break;
			case 'C':
				vI = 2;
				break;
			case 'D':
				vI = 3;
				break;
			case 'E':
				vI = 4;
				break;
			case 'F':
				vI = 5;
				break;
			case 'G':
				vI = 6;
				break;
			case 'H':
				vI = 7;
				break;
			case 'I':
				vI = 8;
				break;
			case 'J':
				vI = 9;
				break;
			case 'K':
				vI = 10;
				break;
			case 'L':
				vI = 11;
				break;
			case 'M':
				vI = 12;
				break;
			case 'N':
				vI = 13;
				break;
			case 'O':
				vI = 14;
				break;
			case 'P':
				vI = 15;
				break;
			case 'Q':
				vI = 16;
				break;
			case 'R':
				vI = 17;
				break;
			case 'S':
				vI = 18;
				break;
			case 'T':
				vI = 19;
				break;
			case 'U':
				vI = 20;
				break;
			case 'V':
				vI = 21;
				break;
			case 'W':
				vI = 22;
				break;
			case 'X':
				vI = 23;
				break;
			case 'Y':
				vI = 24;
				break;
			case 'Z':
				vI = 25;
				break;
			case 'a':
				vI = 26;
				break;
			case 'b':
				vI = 27;
				break;
			case 'c':
				vI = 28;
				break;
			case 'd':
				vI = 29;
				break;
			case 'e':
				vI = 30;
				break;
			case 'f':
				vI = 31;
				break;
			case 'g':
				vI = 32;
				break;
			case 'h':
				vI = 33;
				break;
			case 'i':
				vI = 34;
				break;
			case 'j':
				vI = 35;
				break;
			case 'k':
				vI = 36;
				break;
			case 'l':
				vI = 37;
				break;
			case 'm':
				vI = 38;
				break;
			case 'n':
				vI = 39;
				break;
			case 'o':
				vI = 40;
				break;
			case 'p':
				vI = 41;
				break;
			case 'q':
				vI = 42;
				break;
			case 'r':
				vI = 43;
				break;
			case 's':
				vI = 44;
				break;
			case 't':
				vI = 45;
				break;
			case 'u':
				vI = 46;
				break;
			case 'v':
				vI = 47;
				break;
			case 'w':
				vI = 48;
				break;
			case 'x':
				vI = 49;
				break;
			case 'y':
				vI = 50;
				break;
			case 'z':
				vI = 51;
				break;
			case '0':
				vI = 52;
				break;
			case '1':
				vI = 53;
				break;
			case '2':
				vI = 54;
				break;
			case '3':
				vI = 55;
				break;
			case '4':
				vI = 56;
				break;
			case '5':
				vI = 57;
				break;
			case '6':
				vI = 58;
				break;
			case '7':
				vI = 59;
				break;
			case '8':
				vI = 60;
				break;
			case '9':
				vI = 61;
				break;
			case '+':
				vI = 62;
				break;
			case '/':
				vI = 63;
				break;
		}
		return vI;
	}

	private String intToHex(int amount) {
		String stringOne = Integer.toHexString(amount);
		if (amount < 16) {
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
			saveFile.printf("\tprint (\"Error connecting on \"+PORT)\n");
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
				System.out.println("java -jar javino.jar [TYPE] [PORT] [MSG] [PythonPath]");
				System.out.println("\n[TYPE] " + "\n request -- send a request to a microcontroller, wait answer "
						+ "\n command -- send a command to a microcontroller, without wait answer");

				System.out.println("\n[PORT]" + "\n Set communication serial port" + "\n example: \t COM3 - For Windows"
						+ "\n \t\t /dev/ttyACM0 - For Linux");
				System.out.println(
						"\n[MSG]" + "\n Message for the microcontroller side" + "\n example: \t \"Hello Controller!\"");
				System.out.println("\n[PythonPath]" + "\n Set Path of Python in your system. This is a optional value."
						+ "example: \n\t \"C:\\\\Python27\" - For a System Windows"
						+ "\n\t \"/usr/bin\" - For a System Linux");
			} else {

				String port = args[1];
				String msg = args[2];
				String path = null;
				try {
					path = args[3];
				} catch (Exception ex) {
					System.out.println("[JAVINO] Using default path 'C:\\Python27' or '/usr/bin'");
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
			System.out.println("[JAVINO] Using version " + staticVersion + " CEFET/RJ, Brazil");
			System.out.println("\tTo use Javino, look for the User Manual at http://javino.sf.net");
			System.out.println("For more information try: \n\t java -jar javino.jar --help");

			// ex.printStackTrace();
		}

	}

}