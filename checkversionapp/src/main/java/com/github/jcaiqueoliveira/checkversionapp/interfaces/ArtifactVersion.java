package com.github.jcaiqueoliveira.checkversionapp.interfaces;

/**
 * Created by caique.oliveira on 22/09/2016.
 */

public interface ArtifactVersion extends Comparable<ArtifactVersion> {
    int getMajorVersion();

    int getMinorVersion();

    int getIncrementalVersion();

    int getBuildNumber();

    String getQualifier();

    void parseVersion(String version);
}
