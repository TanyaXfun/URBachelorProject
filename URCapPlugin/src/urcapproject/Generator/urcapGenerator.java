package urcapproject.Generator;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class urcapGenerator {

	private NewProjectModel model;

	public urcapGenerator() {

		this.model = new NewProjectModel();
	}

	public void executeMavenCommand() {

		InvocationRequest request = new DefaultInvocationRequest();
		//request.setPomFile(new File("C:\\Users\\Bruger\\Documents\\URBachelorProject\\URCapPlugin\\pom.xml"));
		request.setBaseDirectory(new File("C:\\Users\\Bruger\\Documents\\Empty"));
		request.setGoals(Collections.singletonList("archetype:generate"));
		request.setBatchMode(true);
		request.setProperties(this.model.setDefaultProperties());

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

		try {
			InvocationResult result = invoker.execute(request);
		} catch (MavenInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
