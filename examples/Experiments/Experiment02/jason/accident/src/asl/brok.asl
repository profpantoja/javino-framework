// Agent bus in project accidentMAS

/* Initial beliefs and rules */
accidentReported(0).
light(0).

/* Initial goals */

/* Plans */
!start.

+!start : true <-
	.print("I am the bus and I will suffer a accident!"); 
	.port(COM3);
	.setMyRFId(ROMA);
	.percepts(open);
	.getRFMessages(open);
	.act(runBus);
	!checkBus.

+!checkBus : true <-
	!checkLuminosity;
	!checkCollision;
	!checkBus.

+!checkLuminosity : luminosity(Status) & Status < 34 & light(Value) & Value = 0 <- 
	.print("Is Night, Turn on light.");
	.act(turnOnLight);
	-light(0);
	+light(1).

+!checkLuminosity : luminosity(Status) & Status >= 34 & light(Value) & Value = 1 <- 
	.print("Is Day, Turn off light.");
	.act(turnOffLight);
	-light(1);
	+light(0).
	
-!checkLuminosity : true <-
	.print("Nothing to do about luminosity.").

+!checkCollision : proximity(Status) & Status < 20 & Status > 0 & accidentReported(Value) & Value = 0 <-
	.print("I have suffed a accident!!!");
	-accidentReported(0);
	+accidentReported(1);
	.act(stopBus);
	.act(accidentAlert);
	.print("Asking for help!!!");
	.diffuse("POLI", tell, help);
	+wait;
	!waitForHelp.
	
-!checkCollision : true <-
	.print("Nothing to do about obstacles proximity.").

+!waitForHelp : wait <-
	!checkHelpComing;
	!waitForHelp.
	
+!checkHelpComing : helpComing <-
	.print("The help is coming!");
	-wait;
	.diffuse("POLI", tell, waiting);
	-accidentReported(1);
	+accidentReported(0);
	-helpComing[source(POLI)].

-!checkHelpComing : true <-
	.print("Wainting help!!!").

	
//-!checkHelpComing : true <-
//	.act(accidentAlert);
//	.print("Asking for help, again!!!");
//	.wait(300);
//	.diffuse("POLI", tell, help);
//	.wait(1000).
