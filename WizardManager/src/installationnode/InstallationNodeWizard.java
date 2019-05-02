package installationnode;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import mavenGenerator.MavenInvokerHandler;
import modelClasses.IURCapMaven;
import modelClasses.MavenModel;
import modelClasses.installationnode.InstallationNodeMavenModel;
import modelClasses.installationnode.InstallationNodeModel;


/**
 * Sets up the wizard for program node installation
 * Adds the pages to the wizard
 * @author jacob
 *
 */
public class InstallationNodeWizard extends Wizard{
	
	private SetClassesNamePage setClassesPage;
	private SetAttributesPage setAttributesPage;
	private IURCapMaven nodeModel;
	private String artifactId, path, groupId;
	
	
	public InstallationNodeWizard(String artifactId, String path, String groupId) {
		super();
		this.artifactId = artifactId;
		this.path = path;
		this.groupId = groupId;
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adds the pages to the wizard. 
	 */
	@Override
	public void addPages() {
		this.setClassesPage = new SetClassesNamePage(this.artifactId); //TODO Remember to remove path here!
		this.setAttributesPage = new SetAttributesPage();
		addPage(this.setClassesPage);
		addPage(this.setAttributesPage);
	}
	
	/**
	 * Takes the program node information filled out in the wizard and creates a 
	 * programNodeModel class from them 
	 */
	@Override
	public boolean performFinish() {
		//this.setAttributesPage.setGeneratingLabel(); //does not work
		

		String serviceClassName = this.setClassesPage.getServiceClassname();
		String viewClassName = this.setClassesPage.getViewClassname();
		String contributionClassName = this.setClassesPage.getContributionClassname();
		String nodeTitle = this.setAttributesPage.getNodeTitle();
		String artifactId = this.setClassesPage.getArtifactId(); //In case of changes nodename
		
		MavenModel mavenModel = new InstallationNodeModel(serviceClassName, contributionClassName, viewClassName, nodeTitle);
		
		
		mavenModel.setProjectPath(this.path);
		mavenModel.setProjectGroupId(this.groupId); // TODO change this to real group id from POM
		mavenModel.setProjectArtifactId(artifactId);
		mavenModel.setProjectVersion("1.0");
		
		this.nodeModel = new InstallationNodeMavenModel(mavenModel);
		
		
		//Generate the program node classes using the program node model.
		MavenInvokerHandler prgen = new MavenInvokerHandler();
		
		Display display = Display.getDefault();
		Cursor waitCursor = new Cursor(display, SWT.CURSOR_WAIT);		
		Shell shell = getShell();
		shell.setCursor(waitCursor);
		
		prgen.invokeMavenExecution(this.nodeModel);	
		//Import project here!
		shell.setCursor(null);
		waitCursor.dispose();
		
		
		return true;
	}

}
