// Agent brok in project wesaac21Javino02

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+!askForHelp <-
	.difuse("////", tell, help);
	!wait.
	
+!wait : help(coming) <-
	.act(turnOn(led)).