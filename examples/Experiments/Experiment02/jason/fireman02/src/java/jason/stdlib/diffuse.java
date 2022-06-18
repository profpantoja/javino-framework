package jason.stdlib;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class diffuse extends DefaultInternalAction{

	private static final long serialVersionUID = -4841692752581197132L;
	
	@Override
	protected void checkArguments(Term[] args) throws JasonException {
		super.checkArguments(args); // check number of arguments
		if (!args[0].isAtom() && !args[0].isList() && !args[0].isString())
			throw JasonException.createWrongArgument(this, "TO parameter ('"
					+ args[0]
					+ "') must be an atom, a string or a list of receivers!");

		if (!args[1].isAtom())
			throw JasonException.createWrongArgument(this,
					"illocutionary force parameter ('" + args[1]
							+ "') must be an atom!");
	}
	
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		
		checkArguments(args);
		String to = args[0].toString().replace("\"", "");
		Term force = args[1];
		Term action = args[2];
		
		if (ts.getUserAgArch().getArgo().diffuse(ts.getUserAgArch().getPort(), ts.getUserAgArch().getRfId(), to, force.toString() + ";" + action.toString())) {
			return true;
		} else { 
			return false;
		}
	}
	
}
