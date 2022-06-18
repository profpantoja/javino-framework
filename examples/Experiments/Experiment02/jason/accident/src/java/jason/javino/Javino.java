package jason.javino;

import jason.javino.exception.BaseConversionException;
import jason.javino.enums.Base64;
import jason.javino.enums.OperationMode;
import jason.javino.constants.JavinoConstants;
import jason.javino.utils.Conversion;
import jason.javino.utils.PythonCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Classe Principal do Javino. */
public class Javino {

	/** Caminho completo do arquivo execut�vel do Python. */
	private final String pythonPlataform;

	/** Mensagem final. */
	private String finalMsg = null;

	private String dstMsg;

	private String srcMsg;
	
	private String myRFId;

	/**
	 * Construtor.
	 */
	public Javino() {
		this.pythonPlataform = PythonCommunication.load();
	}

	/**
	 * Construtor.
	 *
	 * @param pathPython Caminho espec�fico do arquivo execut�vel do Python.
	 */
	public Javino(String pathPython) {
		this.pythonPlataform = PythonCommunication.load(pathPython);
	}

	public String decodeDiffusion(String port, String src, String message) {
		String content = new String();
		if (this.requestRFData(port, message)) {
			content = this.srcMsg + ";" + this.dstMsg + ";" + this.getData();
			//System.out.println("C: "+content);
		}
		return content;
	}

	public boolean diffuse(String port, String receiver, String sender, String content) {
		if (port != null) {
			if (sender != null) {
				if (receiver != null) {
					if (content != null) {
						String diffusionMessage = "";
						try {
							diffusionMessage = sender + receiver + Base64.getMsgSize(content.length()) + content;
							//System.out.println("Diff: "+diffusionMessage);
						} catch (BaseConversionException e) {
							e.printStackTrace();
						}
						//if (diffusionMessage.length() > JavinoConstants.MESSAGE_MIN_SIZE)
							return this.sendMessage(port, diffusionMessage);
						//else
						//	System.out.println("[JAVINO] The message must have at most 64 characters "
						//			+ "including sender, receiver, and the content.");
					} else
						System.out.println("[JAVINO] The message content cannot be null!");
				} else
					System.out.println("[JAVINO] The receiver cannot be null!");
			} else
				System.out.println("[JAVINO] The port cannot be null!");
		} else
			System.out.println("[JAVINO] The sender cannot be null!");
		return false;
	}

	public boolean sendCommand(String port, String message) {
		boolean result;
		if (PythonCommunication.portLocked(port)) {
			result = false;
		} else {
			PythonCommunication.lockPort(true, port);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationMode.COMMAND.getName();
			command[3] = port;
			command[4] = Conversion.prepareToSend(message);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = true;
					PythonCommunication.lockPort(false, port);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					PythonCommunication.lockPort(false, port);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				PythonCommunication.lockPort(false, port);
			}
		}
		return result;

	}

	public boolean sendMessage(String port, String message) {
		boolean result;
		if (PythonCommunication.portLocked(port)) {
			result = false;
		} else {
			PythonCommunication.lockPort(true, port);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationMode.COMMAND.getName();
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
					PythonCommunication.lockPort(false, port);
				} else {
					String line = null;
					String out = "";
					while ((line = output.readLine()) != null) {
						out = out + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + out + "]");
					result = false;
					PythonCommunication.lockPort(false, port);
				}
			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on command execution!");
				e.printStackTrace();
				result = false;
				PythonCommunication.lockPort(false, port);
			}
		}
		return result;

	}

	public boolean listenController(String PORT) {
		boolean result;
		if (PythonCommunication.portLocked(PORT)) {
			result = false;
		} else {
			PythonCommunication.lockPort(true, PORT);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationMode.LISTEN.getName();
			command[3] = PORT;
			command[4] = OperationMode.LISTEN.getName();
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = this.setArrayMsg(array);
					PythonCommunication.lockPort(false, PORT);
				} else {
					String line;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					PythonCommunication.lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening the microcontrolled board.");
				e.printStackTrace();
				result = false;
				PythonCommunication.lockPort(false, PORT);
			}
		}
		return result;
	}

	public boolean requestData(String PORT, String MSG) {
		boolean result;
		if (PythonCommunication.portLocked(PORT)) {
			result = false;
		} else {
			PythonCommunication.lockPort(true, PORT);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationMode.REQUEST.getName();
			command[3] = PORT;
			command[4] = Conversion.prepareToSend(MSG);
			//System.out.println("Command: "+command[4]);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = this.setArrayMsg(array);
					PythonCommunication.lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					PythonCommunication.lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening!");
				e.printStackTrace();
				result = false;
				PythonCommunication.lockPort(false, PORT);
			}

		}
		return result;
	}
	
	public boolean requestRFData(String PORT, String MSG) {
		boolean result;
		if (PythonCommunication.portLocked(PORT)) {
			result = false;
		} else {
			PythonCommunication.lockPort(true, PORT);
			String[] command = new String[5];
			command[0] = this.pythonPlataform;
			command[1] = JavinoConstants.PYTHON_FILE_NAME;
			command[2] = OperationMode.REQUEST_RF.getName();
			command[3] = PORT;
			command[4] = Conversion.prepareToSend(MSG);
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.redirectErrorStream(true);
			try {
				Process p = pBuilder.start();
				p.waitFor();
				BufferedReader array = new BufferedReader(new InputStreamReader(p.getInputStream()));
				if (p.exitValue() == 0) {
					result = this.setArrayMsg(array);
					PythonCommunication.lockPort(false, PORT);
				} else {
					String line = null;
					String output = "";
					while ((line = array.readLine()) != null) {
						output = output + line;
					}
					System.out.println("[JAVINO] Fatal error! [" + output + "]");
					result = false;
					PythonCommunication.lockPort(false, PORT);
				}

			} catch (IOException | InterruptedException e) {
				System.out.println("[JAVINO] Error on listening!");
				e.printStackTrace();
				result = false;
				PythonCommunication.lockPort(false, PORT);
			}

		}
		return result;
	}

	public String getData() {
		String output = this.finalMsg;
		this.finalMsg = null;
		return output;
	}

	private boolean setArrayMsg(BufferedReader reader) {
		String line;
		StringBuilder out = new StringBuilder();
		try {
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			//System.out.println("Out: "+out.toString());
		} catch (IOException e) {
			System.out.println("[JAVINO] Error in message processing.");
			return false;
		}
		return preamble(out.toString().toCharArray());
	}

	private void setFinalMsg(String s_msg) {
		this.finalMsg = s_msg;
	}

	public String getDstMsg() {
		return dstMsg;
	}

	public void setDstMsg(String dstMsg) {
		this.dstMsg = dstMsg;
	}

	public String getSrcMsg() {
		return srcMsg;
	}

	public void setSrcMsg(String srcMsg) {
		this.srcMsg = srcMsg;
	}
	
	public void setMyRFId(String myRFId) {
		this.myRFId = myRFId;
	}

	private boolean preamble(char[] preArrayMsg) {
		try {
			if(preArrayMsg != null && preArrayMsg.length > 0) {
				this.dstMsg = String.valueOf(preArrayMsg[0]) + String.valueOf(preArrayMsg[1])
						+ String.valueOf(preArrayMsg[2]) + String.valueOf(preArrayMsg[3]);
				this.srcMsg = String.valueOf(preArrayMsg[4]) + String.valueOf(preArrayMsg[5])
						+ String.valueOf(preArrayMsg[6]) + String.valueOf(preArrayMsg[7]);
				Integer sizeMsg = Base64.getMsgSize(preArrayMsg[8], preArrayMsg[9]);
				Integer sizeArray = preArrayMsg.length - 10;
				
				if (this.dstMsg.equals("++++") || this.dstMsg.equals("////") || this.dstMsg.equals(this.myRFId)) {
					this.setFinalMsg(Conversion.charToString(preArrayMsg, preArrayMsg.length));
					return true;
				} else {
					char[] newArrayMsg;
					newArrayMsg = new char[(preArrayMsg.length - 1)];
					for (int cont = 0; cont < newArrayMsg.length; cont++) {
						newArrayMsg[cont] = preArrayMsg[cont + 1];
					}
					//return this.preamble(newArrayMsg);
					return false;
				}
			}
			return false;
		} catch (Exception ex) {
			System.out.println("[JAVINO] Invalid message.");
			System.out.println(preArrayMsg);
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

	public static void main(String[] args) {
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
						+ "example: \n\t \"C:\\\\Python\\\\Python310\" - For a System Windows"
						+ "\n\t \"/usr/bin\" - For a System Linux");
			} else {

				String port = args[1];
				String msg = args[2];
				String path = null;
				try {
					path = args[3];
				} catch (Exception ex) {
					System.out.println("[JAVINO] Using default path 'C:\\Python\\Python310' or '/usr/bin'");
				}

				Javino j;
				if (path == null) {
					j = new Javino();
				} else {
					j = new Javino(path);
				}

				if (type.equals(OperationMode.COMMAND.getName())) {
					if (!j.sendCommand(port, msg)) {
						System.exit(1);
					}
				} else if (type.equals(OperationMode.REQUEST.getName())) {
					if (j.requestData(port, msg)) {
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

			// Javino j2 = new Javino();
			// if(j2.requestData("COM6","Ping")) {
			// System.out.println(j2.getData());
			// }


			// ex.printStackTrace();
		}

	}

}