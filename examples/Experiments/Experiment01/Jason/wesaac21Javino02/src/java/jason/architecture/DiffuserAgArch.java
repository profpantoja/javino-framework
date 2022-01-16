package jason.architecture;

import jason.javino.Javino;

public class DiffuserAgArch extends AgArch{

	public Javino jBridge = new Javino();

	public DiffuserAgArch() {
		this.setPort("COM3");
	}
	
	public Javino getArgo() {
		return this.jBridge;
	}
	
}
