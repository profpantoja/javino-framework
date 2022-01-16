// Agent cbv1 in project wesaac21Javino02

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+!contactTroop: help[A] <-
	.diffuse("CB//", tell, accident);
	.diffuse(A, tell, help(coming)).