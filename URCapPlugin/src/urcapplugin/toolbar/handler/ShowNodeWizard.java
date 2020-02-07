package urcapplugin.toolbar.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import verifier.ProjectSelectionVerifier;
import wizardmanager.IWizardFactory;
import wizardmanager.WizardFactory;

/**
 * Class to open the program node wizard.
 * 
 * @author jacob
 *
 */
public class ShowNodeWizard extends AbstractHandler {

	private ProjectSelectionVerifier projectVerifier = new ProjectSelectionVerifier();
	private String artifactId = "", path = "";

	public ShowNodeWizard() {

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (this.projectVerifier.selectedProject()) {
			IWizardFactory factory = new WizardFactory();
			Wizard wizard;
			PomFileReader pomReader = new PomFileReader();
			String groupId = pomReader.getGroupId(this.projectVerifier.getPath());
			this.setWizardParameters();
			
			//DELETE
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "Path", this.projectVerifier.getPath().toString());
			
			try {
				wizard = factory.getWizard(event.getCommand().getDescription(), this.artifactId,
						this.path, groupId);
				WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), wizard);
				dialog.setHelpAvailable(true);
				dialog.open();

			} catch (NullPointerException | NotDefinedException ex) {
				System.err.println("No sutible wizard class found: " + ex);
			}

		} else {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "WARNING!",
					"This is not an URCap project. Please convert it to a URCap project by right-click -> configure -> convert to URCap");
		}

		return null;
	}
	
	private void setWizardParameters() {
		if (this.projectVerifier.getProjectArtifactId() != null) {
			this.artifactId = this.projectVerifier.getProjectArtifactId();
		}
		
		
		if (this.projectVerifier.getPath() != null) {
			this.path = this.projectVerifier.getPath().toString();
		}
		

	}

}
