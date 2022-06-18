package jason.stdlib;

import jason.asSemantics.*;
import jason.asSyntax.*;

public class filter extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        String tag = args[0].toString();
        ts.getAg().changeFilter(tag);        
        return true;
    }
}

