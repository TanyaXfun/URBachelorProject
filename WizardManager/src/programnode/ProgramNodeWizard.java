package programnode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import mavenGenerator.IMavenHandler;
import mavenGenerator.MavenInvokerHandler;
import modelClasses.IURCapMaven;
import modelClasses.MavenModel;
import modelClasses.programnode.ProgramNodeModel;
import modelClasses.programnode.ProgramNodeProjectModel;

/**
 * Sets up the wizard for program node installation
 * Adds the pages to the wizard
 * @author jacob
 *
 */
public class ProgramNodeWizard extends Wizard{
	
	private SetClassesNamePage setClassesPage;
	private SetAttributesPage setAttributesPage;
	private IURCapMaven nodeModel;
	private String artifactId, groupId, path;
	private IMavenHandler mavenHandler;

	public ProgramNodeWizard(String artifactId, String path, String groupId) {
		super();
		setNeedsProgressMonitor(true);
		this.artifactId = artifactId;
		this.path = path;
		this.groupId = groupId;
		this.mavenHandler = new MavenInvokerHandler();
		
	}
	
	/**
	 * Adds the pages to the wizard. 
	 */
	@Override
	public void addPages() {
		this.setClassesPage = new SetClassesNamePage(this.artifactId);
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

		String serviceClassName = this.setClassesPage.getServiceClassname();
		String viewClassName = this.setClassesPage.getViewClassname();
		String contributionClassName = this.setClassesPage.getContributionClassname();
		String nodeId = this.setAttributesPage.getNodeId();
		String nodeTitle = this.setAttributesPage.getNodeTitle();
		boolean setChildrenAllowed = true;
		
		MavenModel mavenModel = new ProgramNodeModel(nodeTitle, nodeId, setChildrenAllowed, serviceClassName, contributionClassName, viewClassName);
		mavenModel.setProjectPath(this.path);
		mavenModel.setProjectGroupId(this.groupId);
		mavenModel.setProjectArtifactId(this.artifactId);
		mavenModel.setProjectVersion("1.0");
		
		this.nodeModel = new ProgramNodeProjectModel(mavenModel);
		
		Display display = Display.getDefault();
		Cursor waitCursor = new Cursor(display, SWT.CURSOR_WAIT);		
		Shell shell = getShell();
		shell.setCursor(waitCursor);
		
		//Executes the maven command and checks whether it has been a succes or not.
		String invokeMessage= mavenHandler.invokeGenerator(this.nodeModel);
		if(invokeMessage != "") {
		MessageDialog.openWarning(shell, "Maven Execution Message", invokeMessage);
		}else {
			MessageDialog.openInformation(shell, "Program node message", "The program node has succesfully been added to the project!" + "\n" + "Please right-click the project and Refresh the project to see result.");
		}
		
		shell.setCursor(null);
		waitCursor.dispose();
		
		
		return true;
	}

}
