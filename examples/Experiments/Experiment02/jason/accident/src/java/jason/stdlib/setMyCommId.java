//----------------------------------------------------------------------------
// Copyright (C) 2003  Rafael H. Bordini, Jomi F. Hubner, et al.
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
// 
// To contact the authors:
// http://www.inf.ufrgs.br/~bordini
// http://www.das.ufsc.br/~jomi
//
//----------------------------------------------------------------------------

package jason.stdlib;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * <p>
 * Internal action: <b><code>.send</code></b>.
 * 
 * <p>
 * Description: sends a message to an agent.
 * 
 * <p>
 * Parameters:
 * <ul>
 * 
 * <li>+ receiver (atom, string, or list): the receiver of the message. It is
 * the unique name of the agent that will receive the message (or list of
 * names).<br/>
 * 
 * <li>+ ilf (atom): the illocutionary force of the message (tell, achieve,
 * ...).<br/>
 * 
 * <li>+ message (literal): the content of the message.<br/>
 * 
 * <li><i>+ answer</i> (any term [optional]): the answer of an ask message (for
 * performatives askOne, askAll, and askHow).<br/>
 * 
 * <li><i>+ timeout</i> (number [optional]): timeout (in milliseconds) when
 * waiting for an ask answer.<br/>
 * 
 * </ul>
 * 
 * <p>
 * Messages with an <b>ask</b> illocutionary force can optionally have arguments
 * 3 and 4. In case they are given, <code>.send</code> suspends the intention
 * until an answer is received and unified with <code>arg[3]</code>, or the
 * message request times out as specified by <code>arg[4]</code>. Otherwise, the
 * intention is not suspended and the answer (which is a tell message) produces
 * a belief addition event as usual.
 * 
 * <p>
 * Examples (suppose that agent <code>jomi</code> is sending the messages):
 * <ul>
 * 
 * <li> <code>.send(rafael,tell,value(10))</code>: sends <code>value(10)</code>
 * to the agent named <code>rafael</code>. The literal
 * <code>value(10)[source(jomi)]</code> will be added as a belief in
 * <code>rafael</code>'s belief base.</li>
 * 
 * <li> <code>.send(rafael,achieve,go(10,30)</code>: sends <code>go(10,30)</code>
 * to the agent named <code>rafael</code>. When <code>rafael</code> receives
 * this message, an event <code>&lt;+!go(10,30)[source(jomi)],T&gt;</code> will
 * be added in <code>rafael</code>'s event queue.</li>
 * 
 * <li> <code>.send(rafael,askOne,value(beer,X))</code>: sends
 * <code>value(beer,X)</code> to the agent named rafael. This askOne is an
 * asynchronous ask since it does not suspend jomi's intention. If rafael has,
 * for instance, the literal <code>value(beer,2)</code> in its belief base, this
 * belief is automatically sent to jomi. Otherwise an event like
 * <code>+?value(beer,X)[source(self)]</code> is generated in rafael's side and
 * the result of this query is then sent to jomi. In the jomi's side, the
 * rafael's answer is added in the jomi's belief base and an event like
 * <code>+value(beer,10)[source(rafael)]</code> is generated.</li>
 * 
 * <li> <code>.send(rafael,askOne,value(beer,X),A)</code>: sends
 * <code>value(beer,X)</code> to the agent named <code>rafael</code>. This
 * askOne is a synchronous askOne, it suspends <code>jomi</code>'s intention
 * until <code>rafael</code>'s answer is received. The answer (something like
 * <code>value(beer,10)</code>) unifies with <code>A</code>.</li>
 * 
 * <li> <code>.send(rafael,askOne,value(beer,X),A,2000)</code>: as in the
 * previous example, but agent <code>jomi</code> waits for 2 seconds. If no
 * message is received by then, <code>A</code> unifies with <code>timeout</code>
 * .</li>
 * 
 * </ul>
 * 
 * @see jason.stdlib.broadcast
 * @see jason.stdlib.my_name
 */
@SuppressWarnings("serial")
public class setMyCommId extends DefaultInternalAction {

	@Override
	public boolean canBeUsedInContext() {
		return false;
	}

	@Override
	public int getMinArgs() {
		return 3;
	}

	@Override
	public int getMaxArgs() {
		return 5;
	}

	@Override
	public Object execute(final TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		ts.getUserAgArch().getCommBridge().setMyUUID(args[0].toString().substring(1, args[0].toString().length()-1));
		return true;
	}

}
