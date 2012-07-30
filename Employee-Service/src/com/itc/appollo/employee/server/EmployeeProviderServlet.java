package com.itc.appollo.employee.server;

import org.apache.abdera.protocol.server.Provider;
import org.apache.abdera.protocol.server.impl.DefaultProvider;
import org.apache.abdera.protocol.server.impl.SimpleWorkspaceInfo;
import org.apache.abdera.protocol.server.servlet.AbderaServlet;

@SuppressWarnings("serial")
public  class EmployeeProviderServlet extends AbderaServlet {
    protected Provider createProvider() {
        EmployeeCollectionAdapter ca = new EmployeeCollectionAdapter();
        ca.setHref("employee");
       

        SimpleWorkspaceInfo wi = new SimpleWorkspaceInfo();
        wi.setTitle("Employee Directory Workspace");
        wi.addCollection(ca);

        DefaultProvider provider = new DefaultProvider("/");
        provider.addWorkspace(wi);

        provider.init(getAbdera(), null);
        return provider;
    }
}


