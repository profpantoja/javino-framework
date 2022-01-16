import jason.architecture.AgArch;
import jason.architecture.DiffuserAgArch;
import jason.asSemantics.Message;
import jason.javino.Javino;

public class Diffuser extends AgArch {
	
	private DiffuserAgArch diffuserAgArch = new DiffuserAgArch();

	public Diffuser() {
		this.setPort(this.diffuserAgArch.getPort());
	}
	
	@Override
	public Javino getArgo() {
		return this.diffuserAgArch.jBridge;
	}
	
	@Override
	public DiffuserAgArch getDiffuserAgArch() {
		return this.diffuserAgArch;
	}

	@Override
	public void addMessageToC() {
		String difusion = this.diffuserAgArch.jBridge.decodeDiffusion("COM3", "POLI", "getMessage");
		if (difusion != null && !difusion.isEmpty()) {
			String [] messageSlited = difusion.split(";");
			
			Message message = new Message();
			message.setSender(messageSlited[0]);
			message.setReceiver(messageSlited[1]);
			message.setIlForce(messageSlited[2]);
			message.setPropCont(messageSlited[3]);
			
			this.getTS().getC().addMsg(message);
		}
		/* Pode ser aqui o problema */
		//this.commBridge.cleanMailBox();
	}
}
