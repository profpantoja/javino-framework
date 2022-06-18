// Internal action code for project javinoArchitectureWithInternalActions



//If you want only to use '.move' command type 'package jason.stdlib'. By Pantoja.
package jason.stdlib;

import jason.asSemantics.*;
import jason.asSyntax.*;

public class act extends DefaultInternalAction {

	private static final long serialVersionUID = -4841692752581197132L;
	private boolean isIlloc;
	
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		
		Term action = args[0];
		if (ts.getUserAgArch().getArgo().sendCommand(ts.getUserAgArch().getPort(), action.toString())) {
			return true;
		} else { return false;}
	}
}
