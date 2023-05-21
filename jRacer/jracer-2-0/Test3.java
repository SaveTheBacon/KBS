package test;

import com.racersystems.jracer.*;

public class Test3 {

	public static void main(String[] args) {

		String filename = "/Applications/RacerPro 2.0 preview/examples/owl/people-pets.owl";

		String ip = "localhost";
		int port = 8088;

		RacerClient racer = new RacerClient(ip, port);

		try {

			racer.openConnection();

			racer.fullReset();

			racer.owlReadFile("\"" + filename + "\"");
			
			System.out.println(racer.racerAnswerQuery("(?x ?y)",
					"(and (?x #!:person) (?x ?y #!:has_pet))"));

			@SuppressWarnings("unchecked")
			RacerList<RacerList<RacerList<RacerSymbol>>> res2 = 
			    (RacerList<RacerList<RacerList<RacerSymbol>>>) racer
					.racerAnswerQuery$("(?x ?y)",
							"(and (?x #!:person) (?x ?y #!:has_pet))");

			for (RacerList<RacerList<RacerSymbol>> bindings : res2) {
				for (RacerList<RacerSymbol> binding : bindings) {
					for (RacerSymbol varval : binding) {
						System.out.println(varval);
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
