package jason.architecture;

import jason.asSyntax.Term;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

public class CommMiddleware implements NodeConnectionListener {

	private static String gatewayIP = "127.0.0.1";
	private static int gatewayPort = 5500;
	private MrUdpNodeConnection connection;
	private UUID myUUID = UUID
			.fromString("788b2b22-baa6-4c61-b1bb-01cff1f5f878");
	private String agName = "";
	private ArrayList<jason.asSemantics.Message> jMsg = new ArrayList<jason.asSemantics.Message>();

	public String getAgName() {
		return agName;
	}

	public void setAgName(String agName) {
		this.agName = agName;
	}

	public void setMyUUID(String myUUID) {
		this.myUUID = UUID.fromString(myUUID);
	}

	public CommMiddleware() {
		InetSocketAddress address = new InetSocketAddress(gatewayIP,
				gatewayPort);
		try {
			connection = new MrUdpNodeConnection(myUUID);
			connection.addNodeConnectionListener(this);
			connection.connect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connected(NodeConnection arg0) {
		ApplicationMessage message = new ApplicationMessage();
		message.setContentObject("Registering");
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnected(NodeConnection arg0) {
	}

	public void internalException(NodeConnection arg0, Exception arg1) {
	}

	public void newMessageReceived(NodeConnection remoteCon, Message message) {
		/*
		 * [Pantoja]: a) Tem que tratar as mensagens recebidas. Colocar em um
		 * pool de mensagens que ser�o limpas ap�s serem processadas no novo
		 * (poss�vel) checkMail. b) tamb�m � preciso desmontar as mensagens
		 * vinda do protocolo e transform�-las em mensagens do tipo Message do
		 * Jason.
		 */
		this.extractMessageFromContextNet(message.getContentObject().toString()
				.toCharArray());
		//System.out.println("[ARGO]: A Message has arrived: " + message.getContentObject().toString());
	}

	public void reconnected(NodeConnection arg0, SocketAddress arg1,
			boolean arg2, boolean arg3) {
	}

	public void unsentMessages(NodeConnection arg0, List<Message> arg1) {
	}

	/*
	 * [Pantoja]: Functions provided for working with Javino. These functions
	 * already exists in Javino
	 */

	public void cleanMailBox() {
		if (this.jMsg.size() > 0) {
			this.jMsg.remove(0);
		}
	}

	public boolean hasMsg() {
		//System.out.println("[ARGO]: Messages = " + this.jMsg.size());
		if (this.jMsg != null && this.jMsg.size() > 0)
			return true;
		else
			return false;
	}

	public jason.asSemantics.Message checkMailCN() {
		return this.jMsg.get(0);
	}

	public void extractMessageFromContextNet(char[] message) {
		String sender = "";
		String force = "";
		String msg = "";
		if (message[0] == 'f' && message[1] == 'f' && message[2] == 'f'
				&& message[3] == 'e') {
			for (int cont = 6; cont <= (this.hex2int(char2int(message[5]),
					char2int(message[4])) + 5); cont++) {
				sender += message[cont];
			}
			int nextValue = sender.length() + 6;
			int shift = this.hex2int(char2int(message[nextValue + 1]),
					char2int(message[nextValue]));
			for (int cont = nextValue + 2; cont <= (shift + nextValue + 1); cont++) {
				force += message[cont];
			}
			nextValue = shift + nextValue + 2;
			shift = this.hex2int(char2int(message[nextValue + 1]),
					char2int(message[nextValue]));
			for (int cont = nextValue + 2; cont <= (shift + nextValue + 1); cont++) {
				msg += message[cont];
			}
		} else {
			System.out.println("[ARGO]: The preamble does not match!");
		}

		jason.asSemantics.Message jasonMsgs = new jason.asSemantics.Message();
		jasonMsgs.setIlForce(force);
		jasonMsgs.setSender(sender);
		jasonMsgs.setPropCont(msg);
		jasonMsgs.setReceiver(this.agName);
		//System.out.println("[ARGO]: The message is: " + jasonMsgs.toString());
		this.jMsg.add(jasonMsgs);
	}

	public void sendMsgToContextNet(String sender, String receiver, Term force,
			Term msg) {
		ApplicationMessage message = new ApplicationMessage();
		message.setContentObject(this.prepareToSend(sender, force.toString(),
				msg.toString()));
		message.setRecipientID(UUID.fromString(receiver.substring(1,
				receiver.length() - 1)));
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String prepareToSend(String sender, String force, String msg) {
		msg = "fffe" + int2hex(sender.length()) + sender
				+ int2hex(force.length()) + force + int2hex(msg.length()) + msg;
		return msg;
	}

	private String int2hex(int v) {
		String stringOne = Integer.toHexString(v);
		if (v < 16) {
			stringOne = "0" + stringOne;
		}
		return stringOne;
	}

	private int char2int(char charValue) {
		int intValue = 0;
		switch (charValue) {
		case '1':
			intValue = 1;
			break;
		case '2':
			intValue = 2;
			break;
		case '3':
			intValue = 3;
			break;
		case '4':
			intValue = 4;
			break;
		case '5':
			intValue = 5;
			break;
		case '6':
			intValue = 6;
			break;
		case '7':
			intValue = 7;
			break;
		case '8':
			intValue = 8;
			break;
		case '9':
			intValue = 9;
			break;
		case 'a':
			intValue = 10;
			break;
		case 'b':
			intValue = 11;
			break;
		case 'c':
			intValue = 12;
			break;
		case 'd':
			intValue = 13;
			break;
		case 'e':
			intValue = 14;
			break;
		case 'f':
			intValue = 15;
			break;
		}
		return intValue;
	}

	private int hex2int(int x, int y) {
		int converted = x + (y * 16);
		return converted;
	}
}
