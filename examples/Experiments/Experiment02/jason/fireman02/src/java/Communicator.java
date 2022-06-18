import jason.architecture.AgArch;
import jason.architecture.CommMiddleware;

public class Communicator extends AgArch {
	
	private CommMiddleware commBridge = new CommMiddleware();

	@Override
	public CommMiddleware getCommBridge() {
		this.commBridge.setAgName(this.getAgName());
		return this.commBridge;
	}

	public void addMessageToC() {
		this.getTS().getC().addMsg(this.commBridge.checkMailCN());
		/* Pode ser aqui o problema */
		//this.commBridge.cleanMailBox();
	}
}
