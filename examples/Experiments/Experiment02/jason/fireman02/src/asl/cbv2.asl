// Agent cbv2 in project wesaac21Javino02

/* Initial beliefs and rules */

/* Initial goals */
!start.
!wait.


/* Plans */
+!start : true <-
	.print("I am a Police");
	.port(COM5);
	.setMyRFId(POLI);
	.percepts(open);
	.getRFMessages(open).


+!wait : help <-
	+accident;
	.print("Recebi!!!");
	.diffuse("ROMA", tell, helpComing);
	!helping.

-!wait <-
	!wait.
	
+!helping : accident & waiting <-
	.print("Ajudado");
	-accident;
	-waiting[source(ROMA)];
	-help[source(ROMA)];
	!wait.

	