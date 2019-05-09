package deployModels;

import java.util.Properties;

import mavenGenerator.MavenInvokerHandler;

public class DeployToRobot implements IDeploy{

	private String ipaddress, username, password, path, artifactID;
	private Properties properties;
	private static final String GOAL = "install -P remote";
	
	public DeployToRobot(String ipaddress, String username, String password, String path, String artifactID) {
		this.ipaddress = ipaddress;
		this.username = username;
		this.password = password;
		this.path = path;
		this.artifactID = artifactID;
		this.setProperties();
	}
	
	@Override
	public String getProjectPath() {
		return this.path;
	}


	public void setProjectPath(String path) {
		this.path = path;
		
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	private void setProperties() {
		this.properties = new Properties();
		this.properties.setProperty("urcap.install.host", this.ipaddress);
		this.properties.setProperty("urcap.install.username", this.username);
		this.properties.setProperty("urcap.install.password", this.password);
		
	}

	@Override
	public String getGoal() {
		return GOAL;
	}
	
	@Override
	public String deploy() {
		MavenInvokerHandler invoker = new MavenInvokerHandler();
		String message = invoker.invokeMavenExecutionDeploy(this,this.artifactID);
		
		return message;
	}


}
