package javino;

import javino.model.JavinoConstants;
import javino.model.OperationEnum;
import javino.utils.ConversionUtils;
import javino.utils.PythonCommunicationUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/** Classe Princial do Javino. */
public class Javino {

	/** Caminho completo do arquivo executável do Python. */
	private final String pythonPlataform;

	/** Mensagem final. */
	private String finalMsg = null;

	/**
	 * Construtor.
	 */
	public Javino() {
		this.pythonPlataform = PythonCommunicationUtils.load();
	}

	/**
	 * Construtor.
	 *
	 * @param pathPython Caminho específico do arquivo executável do Python.
	 */
	public Javino(String pathPython) {
		this.pythonPlataform = PythonCommunicationUtils.load(pathPython);
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
					String diffusionMessage = receiver + ConversionUtils.getMsgSizeInB64(content.length()) + content;
					if (diffusionMessage.length() > 64)
						return this.sendMessage(port, diffusionMessage);
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
		if (PythonCommunicationUtils.portLocked(port)) {
			result = false;
		} else {
			PythonCommunicationUtils.lockPort(true, port);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationEnum.COMMAND.getName();
			command[3] = port;
			command[4] = ConversionUtils.prepareToSend(message);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = true;
					PythonCommunicationUtils.lockPort(false, port);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					PythonCommunicationUtils.lockPort(false, port);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				PythonCommunicationUtils.lockPort(false, port);
			}
		}
		return result;

	}

	public boolean sendMessage(String port, String message) {
		boolean result;
		if (PythonCommunicationUtils.portLocked(port)) {
			result = false;
		} else {
			PythonCommunicationUtils.lockPort(true, port);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationEnum.COMMAND.getName();
			command[3] = port;
			command[4] = message;
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = true;
					PythonCommunicationUtils.lockPort(false, port);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					PythonCommunicationUtils.lockPort(false, port);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				PythonCommunicationUtils.lockPort(false, port);
			}
		}
		return result;

	}

	public boolean listenController(String PORT) {
		boolean result;
		if (PythonCommunicationUtils.portLocked(PORT)) {
			result = false;
		} else {
			PythonCommunicationUtils.lockPort(true, PORT);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationEnum.LISTEN.getName();
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
					PythonCommunicationUtils.lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					PythonCommunicationUtils.lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening the microcontrolled board.");
				e.printStackTrace();
				result = false;
				PythonCommunicationUtils.lockPort(false, PORT);
			}
		}
		return result;
	}

	public boolean requestData(String PORT, String MSG) {
		boolean result;
		if (PythonCommunicationUtils.portLocked(PORT)) {
			result = false;
		} else {
			PythonCommunicationUtils.lockPort(true, PORT);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationEnum.LISTEN.getName();
			command[3] = PORT;
			command[4] = ConversionUtils.prepareToSend(MSG);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = setArryMsg(array);
					PythonCommunicationUtils.lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					PythonCommunicationUtils.lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening!");
				e.printStackTrace();
				result = false;
				PythonCommunicationUtils.lockPort(false, PORT);
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
					&& (this.monitorMsg(ConversionUtils.hexToInt(preArrayMsg[5]), ConversionUtils.hexToInt(preArrayMsg[4]),
					preArrayMsg.length))) {
				setFinalMsg(ConversionUtils.charToString(preArrayMsg, preArrayMsg.length));
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
			System.out.println("[JAVINO] Using version " + JavinoConstants.VERSION + " CEFET/RJ, Brazil");
			System.out.println("\tTo use Javino, look for the User Manual at http://javino.sf.net");
			System.out.println("For more information try: \n\t java -jar javino.jar --help");

			// ex.printStackTrace();
		}

	}

}