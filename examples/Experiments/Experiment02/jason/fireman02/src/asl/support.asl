// Agent support in project supportMAS

/* Initial beliefs and rules */

/* Initial goals */
!start.
!wait.


/* Plans */
+!start : true <-
	.print("I am a Support agent");
	.port(COM8);
	.setMyRFId(SUPP);
	.percepts(open);
	.getRFMessages(open).


+!wait : someoneNeedHelp <-
	!askForHelp.

-!wait : true <-
	!wait.

+!askForHelp : someoneNeedHelp <-
	.print("Asking for help!!!");
	.diffuse("SUHO", tell, someoneNeedHelp);
	-someoneNeedHelp;
	!helping.
	
+!helping : ambulanceIsComming <-
	.print("I understood! The Ambulance is comming!!!");
	.diffuse("POLI", tell, helpComing);
	-ambulanceIsComming;
	!wait.
	
