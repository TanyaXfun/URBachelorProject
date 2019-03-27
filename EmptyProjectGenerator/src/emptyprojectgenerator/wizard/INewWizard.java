package emptyprojectgenerator.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

public interface INewWizard {

	public void init(IWorkbench workbench, IStructuredSelection selection);
	
	public boolean performFinish();
}