import jason.architecture.AgArch;
import jason.architecture.DiffuserAgArch;
import jason.asSemantics.Message;
import jason.javino.Javino;

public class Diffuser extends AgArch {
	
	private DiffuserAgArch diffuserAgArch = new DiffuserAgArch();
	
	private String lastMsg = "";
	
	private int attempt = 0;

	public Diffuser() {
		//this.setPort("COM5");
		//this.setRfId("POLI");
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
		if(this.getPort() != null && !this.getPort().isEmpty() 
				&& this.getRfId() != null && !this.getRfId().isEmpty()) {
			//if (this.attempt < 3 && (this.lastMsg == null || this.lastMsg.trim().isEmpty())) {
			//	this.attempt++;
			//} else {
				//System.out.println("Antes de Difusion!");
				String diffusion = this.getArgo().decodeDiffusion(getPort(), getRfId(), 
						"getMessage");
				//System.out.println("Diffusion: "+diffusion);
				if (diffusion != null && !diffusion.isEmpty()) {
					String [] messageSlited = diffusion.split(";");
					
					Message message = new Message();
					message.setSender(messageSlited[0]);
					message.setReceiver(messageSlited[1]);
					message.setIlForce(messageSlited[2]);
					message.setPropCont(messageSlited[3]);
					
					getTS().getC().addMsg(message);
				}
				//this.lastMsg = diffusion;
				//this.attempt = 0;
			//}
		}
		/* Pode ser aqui o problema */
		//this.commBridge.cleanMailBox();
	}
}
