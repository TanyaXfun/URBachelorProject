package wizardmanager;

import programnode.ProgramNodeWizard;
import org.eclipse.jface.wizard.Wizard;


/**
 * Factory class to setup specific wizard type.
 * Returns the wizard of requested type.
 * Wizard type should be set in the main plugin class as a description on the command attached to the specific button. 
 * fx. if the wizard for a program node should be should be called, the description is set on the program node command for the Program Node extension point, 
 * and the exact same is added as a case in the switch case on getWizard method. The case the return a creation call on the specified wizard class.
 * @author jacob
 *
 */
public class WizardFactory {
	/**
	 * Get wizard of specied string type. Types a set as the description on the extension point command. Ex: "ProgramNode" for the program node menu item
	 * Remember to null check when calling the method, since default return is null. 
	 * @param wizardType
	 * @return
	 */
	public Wizard getWizard(String wizardType) {
		switch (wizardType) {
		case "ProgramNode":
			return new ProgramNodeWizard();
		case "InstallationNode":
			// TODO installation node not yet implemented
			return null;
		case "ToolbarNode":
			// TODO Contribution node not yes implemented
			return null;
		default:
			return null;
		}
	
	}
}