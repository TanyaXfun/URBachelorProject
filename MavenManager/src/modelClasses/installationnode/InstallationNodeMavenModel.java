package modelClasses.installationnode;

import java.util.Properties;

import modelClasses.IURCapMaven;
import modelClasses.MavenModel;
import modelClasses.URCapProjectModel;




/**
 * Model class for maven generation of a program node. Takes the groupid, artifactid which should be found be the wizard when from the selected project,
 * the program node should be generated in. Also takes a path (might not be useful in this case?) and a programNodeModel which the wizard 
 * also should create. 
 * The archetype is set to programnodearchetype. 
 * @author jacob
 *
 */
public class InstallationNodeMavenModel implements IURCapMaven {

	private InstallationNodeModel installationNodeModel;
	private static final String ARCHETYPE_GROUPID = "com.ur.urcap";
	private static final String ARCHETYPE_ARTIFACTID = "installationnodearchetype";
	private static final String ARCHETYPE_VERSION = "1.0";

	private Properties properties;
	
	/**
	 * Constructor the set all class fields.
	 * @param groupID
	 * @param artifactID
	 * @param version
	 * @param apiVersion
	 * @param projectPath
	 */
	public InstallationNodeMavenModel(MavenModel installationNode) {
		this.installationNodeModel = (InstallationNodeModel) installationNode;
		this.installationNodeModel.setArchetypeGroupId(ARCHETYPE_GROUPID);
		this.installationNodeModel.setArchetypeArtifactId(ARCHETYPE_ARTIFACTID);
		this.installationNodeModel.setArchetypeVersion(ARCHETYPE_VERSION);
		this.installationNodeModel.setArchetypeVersionAPI("1.5.0");
		this.installationNodeModel.setMavenGoal("archetype:generate");
		this.setProperties();

	}

	/**
	 * Sets properties for maven project to be generated. 
	 */
	private void setProperties() {
		this.properties = new Properties();
		properties.setProperty("interactiveMode", "false");
		properties.setProperty("archetypeGroupId", this.installationNodeModel.getArchetypeGroupId());
		properties.setProperty("archetypeArtifactId", this.installationNodeModel.getArchetypeArtifactId());
		properties.setProperty("archetypeVersion", ARCHETYPE_VERSION);
		properties.setProperty("package", this.installationNodeModel.getProjectGroupId());
		properties.setProperty("groupId", this.installationNodeModel.getProjectGroupId());
		properties.setProperty("artifactId", this.installationNodeModel.getProjectArtifactId());
		properties.setProperty("contributionClassName", this.installationNodeModel.getContributionClassName());
		properties.setProperty("serviceClassName", this.installationNodeModel.getServiceClassName());
		properties.setProperty("viewClassName", this.installationNodeModel.getViewClassName());
		properties.setProperty("nodeTitle", this.installationNodeModel.getNodeTitle());
		properties.setProperty("version", this.installationNodeModel.getProjectVersion());
	}
	
	/**
	 * Get all properties set for maven project generation
	 * @return Properties of type Properties
	 */
	@Override
	public Properties getProperties() {
		return this.properties;
	}

	@Override
	public String getProjectPath() {
		return this.installationNodeModel.getProjectPath();
	}

	@Override
	public String getGoal() {
		return this.installationNodeModel.getMavenGoal();
	}
	

}
