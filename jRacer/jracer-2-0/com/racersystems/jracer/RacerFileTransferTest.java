package com.racersystems.jracer;

public class RacerFileTransferTest {

    public static void main(String[] args) {

	// please adjust this path to match your environment: 

	// String peopleAndPets = "C:/jracer-2-0/demo/people+pets.owl"; 

	String peopleAndPets = "/home/mi.wessel/racer-support/jracer-2-0/demo/people+pets.owl"; 

	String ip = "localhost";
	int port = 8088;

	RacerClient racer = new RacerClient(ip,port);		

	try {

	    racer.openConnection();

	    racer.loggingOn();
	    racer.sendRaw("(logging-on)");
	
	    racer.fullReset();

	    racer.transferFile(peopleAndPets,"owl"); 
	    
	    RacerList<RacerList<RacerList<RacerSymbol>>>
		res2 = (RacerList<RacerList<RacerList<RacerSymbol>>>)
		racer.racerAnswerQuery$("(?x ?y)","(and (?x #!:person) (?x ?y #!:has_pet))"); 

 	    for (RacerList<RacerList<RacerSymbol>> bindings : res2) {
		for (RacerList<RacerSymbol> binding : bindings) {
		    System.out.println();
		    System.out.print(binding.getValue().get(0));
		    System.out.print(" "+binding.getValue().get(1));
		}
		System.out.println(); 
	    } 

	} catch (Exception e) {

	    e.printStackTrace();
	    
	}
	
    }
    
}


