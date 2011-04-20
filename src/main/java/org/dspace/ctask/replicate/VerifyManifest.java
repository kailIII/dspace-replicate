/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://dspace.org/license/
 */

package org.dspace.ctask.replicate;

import java.io.IOException;

import org.dspace.content.DSpaceObject;
import org.dspace.core.ConfigurationManager;
import org.dspace.curate.AbstractCurationTask;
import org.dspace.curate.Curator;

/**
 * VerifyManifest task will simply test for the presence of a manifest
 * of the object in the remote store. It succeeds if found, otherwise fails.
 * 
 * @author richardrodgers
 */

public class VerifyManifest extends AbstractCurationTask {

    private ReplicaManager repMan = ReplicaManager.instance();
    private String archFmt = ConfigurationManager.getProperty("replicate", "packer.archfmt");
    
    // Group where all Manifests are stored
    private final String manifestGroupName = ConfigurationManager.getProperty("replicate", "group.manifest.name");

    @Override
    public int perform(DSpaceObject dso) throws IOException
    {
        String objId = ReplicaManager.safeId(dso.getHandle());
        boolean found = repMan.objectExists(manifestGroupName, objId);
        String result = "Manifest for object: " + dso.getHandle() + " found: " + found;
        report(result);
        setResult(result);
        return found ? Curator.CURATE_SUCCESS : Curator.CURATE_FAIL;
    }
}