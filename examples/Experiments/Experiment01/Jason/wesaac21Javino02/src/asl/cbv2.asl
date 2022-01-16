// Agent cbv2 in project wesaac21Javino02

/* Initial beliefs and rules */

/* Initial goals */
!wait.


/* Plans */
+!wait : accident <-
	!helping.

-!wait <-
	!wait.
	
+!helping : accident & waiting <-
	.print("Ajudado");
	-accident[source(ROMA)];
	-waiting[source(ROMA)];
	!wait.

+!helping : accident <-
	.print("Recebi!!!");
	.diffuse("ROMA", tell, helpComing);
	!helping.
	