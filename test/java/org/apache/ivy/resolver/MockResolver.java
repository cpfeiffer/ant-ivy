/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivy.resolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ivy.Artifact;
import org.apache.ivy.DefaultModuleDescriptor;
import org.apache.ivy.DependencyDescriptor;
import org.apache.ivy.DependencyResolver;
import org.apache.ivy.Ivy;
import org.apache.ivy.ModuleDescriptor;
import org.apache.ivy.ModuleRevisionId;
import org.apache.ivy.ResolveData;
import org.apache.ivy.ResolvedModuleRevision;
import org.apache.ivy.report.DownloadReport;
import org.apache.ivy.resolver.AbstractResolver;



public class MockResolver extends AbstractResolver {
    static MockResolver buildMockResolver(String name, boolean findRevision, final Date publicationDate) {
        return buildMockResolver(name, findRevision, ModuleRevisionId.newInstance("test", "test", "test"), publicationDate);
    }

    static MockResolver buildMockResolver(String name, boolean findRevision, final ModuleRevisionId mrid, final Date publicationDate) {
        return buildMockResolver(name, findRevision, mrid, publicationDate, false);
    }
    static MockResolver buildMockResolver(String name, boolean findRevision, final ModuleRevisionId mrid, final Date publicationDate, final boolean isdefault) {
        final MockResolver r = new MockResolver();
        r.setName(name);
        if (findRevision) {
            r.rmr = new ResolvedModuleRevision() {
                public DependencyResolver getResolver() {
                    return r;
                }

                public ModuleRevisionId getId() {
                    return mrid;
                }

                public Date getPublicationDate() {
                    return publicationDate;
                }

                public ModuleDescriptor getDescriptor() {
                    return new DefaultModuleDescriptor(mrid, "integration", new Date(), isdefault);
                }
                public boolean isDownloaded() {
                    return true;
                }
                public boolean isSearched() {
                    return true;
                }

                public DependencyResolver getArtifactResolver() {
                    return r;
                }
                public URL getLocalMDUrl() {
                	return null;
                }
            };
        }
        return r;
    }

    List askedDeps = new ArrayList();
    ResolvedModuleRevision rmr;
    
    public ResolvedModuleRevision getDependency(DependencyDescriptor dd, ResolveData data) throws ParseException {
        askedDeps.add(dd);
        return rmr;
    }

    public DownloadReport download(Artifact[] artifacts, Ivy ivy, File cache, boolean useOrigin) {
        return null;
    }
    public void publish(Artifact artifact, File src, boolean overwrite) throws IOException {
    }

}