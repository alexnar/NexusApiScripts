/**
 * Nexus create maven repository API script.
 */

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.repository.storage.WritePolicy;
import org.sonatype.nexus.repository.maven.VersionPolicy;
import org.sonatype.nexus.repository.maven.LayoutPolicy;

// Getting arguments from Post parameters
def argsMap = args.split('&').inject([:]) { map, token ->
    token.split('=').with { map[it[0]] = it[1] }
    map
}

def repositoryName = argsMap.getAt("repositoryName").toString()
repository.createMavenHosted(repositoryName, BlobStoreManager.DEFAULT_BLOBSTORE_NAME, true,
        VersionPolicy.MIXED, WritePolicy.ALLOW, LayoutPolicy.STRICT)






