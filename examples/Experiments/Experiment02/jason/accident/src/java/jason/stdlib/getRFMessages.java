package jason.stdlib;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class getRFMessages extends DefaultInternalAction{
	
	private static final long serialVersionUID = -4841692752581197132L;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {

		if (args[0].toString().equals("block")) {
			ts.blockedRFMessages = true;
			return true;
		} else if (args[0].toString().equals("open")) {
			ts.blockedRFMessages = false;
			return true;
		} else {
			return false;
		}
	}

}
