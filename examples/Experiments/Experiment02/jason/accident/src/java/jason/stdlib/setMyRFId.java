package jason.stdlib;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class setMyRFId extends DefaultInternalAction {

	private static final long serialVersionUID = -4841692752581197132L;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {

		Term illoc = args[0];
		ts.getUserAgArch().setRfId(illoc.toString());
		ts.getUserAgArch().getArgo().setMyRFId(illoc.toString());
		return true;
	}
	
}
