/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.ui.handler;

import javax.inject.Named;

import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class RemoveExtJSSupport {

    @Execute
    public static void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection) {
        final IAdaptable adaptable = (IAdaptable) selection.getFirstElement();
        final IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();
        Job job = new Job("Remove ExtJS support") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    final IFacetedProject fproject = ProjectFacetsManager.create(project, false, monitor);

                    fproject.uninstallProjectFacet(fproject.getInstalledVersion(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT)), null, monitor);
                } catch (CoreException e) {
                    return Status.CANCEL_STATUS;
                }

                return Status.OK_STATUS;
            }
        };

        job.schedule();
    }

}
