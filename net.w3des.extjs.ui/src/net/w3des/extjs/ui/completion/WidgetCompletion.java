package net.w3des.extjs.ui.completion;

import java.util.List;

import net.w3des.extjs.core.internal.ExtJSCore;
import net.w3des.extjs.core.model.basic.Alias;
import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.core.model.basic.File;
import net.w3des.extjs.core.model.basic.Widget;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

public class WidgetCompletion extends AliasCompletion {
    public WidgetCompletion() {
        super(new String[] { "Ext.createWidget(", "xtype:", ".xtype=" });
    }

    @Override
    protected void computeCompletionProposals(boolean inString, String start, List<ICompletionProposal> proposals,
            JavaContentAssistInvocationContext context, IProgressMonitor monitor) {
        final ExtJSProject project = ExtJSCore.getProjectManager().createProject(context.getProject().getProject());

        for (final File f : project.getFiles()) {
            for (final Alias a : f.getAliases()) {

                if (a instanceof Widget && a.getName().startsWith(start)) {
                    proposals.add(createTextProposal(inString, aliasImage(a), a.getName(),
                            context.getInvocationOffset(), start, null, null));

                }
            }
        }
    }
}