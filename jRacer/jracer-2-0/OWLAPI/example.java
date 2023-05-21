
package test;

import com.racersystems.racer.*;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;

public class example {

	public static void main(String[] args) {

		String DOCUMENT_IRI = "file:///Applications/RacerPro%202.0%20preview/examples/owl/people-pets.owl";
				
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			IRI docIRI = IRI.create(DOCUMENT_IRI);
			OWLOntology ont = manager.loadOntologyFromOntologyDocument(docIRI);
			OWLReasonerFactory reasonerFactory = new ReasonerFactory();
			ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
			OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
			OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);

			boolean consistent = reasoner.isConsistent();
			System.out.println("Consistent: " + consistent);
			System.out.println("\n");

			Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
			Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
			if (!unsatisfiable.isEmpty()) {
				System.out.println("The following classes are unsatisfiable: ");
				for (OWLClass cls : unsatisfiable) {
					System.out.println("    " + cls);
				}
			} else {
				System.out.println("There are no unsatisfiable classes");
			}
			System.out.println("\n");

			reasoner.dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
