package com.itc.appollo.employee.client;


import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.AtomDate;
import org.apache.abdera.model.Collection;
import org.apache.abdera.model.Content.Type;
import org.apache.abdera.model.Document;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Service;

import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;


public class EmployeeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	Abdera abdera=new Abdera();
		AbderaClient client =new AbderaClient(abdera);
		Entry entry=abdera.newEntry();
		entry.setContent("Keshav");
		entry.setTitle("Keshav is great");
		entry.setId("Keshav");
		entry.addAuthor("Keshav");
		entry.setUpdated(new Date());
		ClientResponse resp=client.post("http://localhost:8080/EmployeeService/employee", entry);
		System.out.println("Here is the response i am getting here "+resp.getStatus());
		if(resp.getStatus()==200){
			System.out.println(" i did it buddy ");
			Document<Feed> doc = resp.getDocument();
		
			System.out.println(doc.getBaseUri());
			System.out.println(doc.getBaseUri());

		}
		*/
		 Abdera abdera = new Abdera();
	        AbderaClient abderaClient = new AbderaClient(abdera);

	        Factory factory = abdera.getFactory();

	        // Perform introspection. This is an optional step. If you already
	        // know the URI of the APP collection to POST to, you can skip it.
	        Document<Service> introspection = abderaClient.get("http://localhost:9080/EmployeeService/").getDocument();
	        Service service = introspection.getRoot();
	        System.out.println(service.getWorkspaces());
	        Collection collection = service.getCollectionThatAccepts("application/atom+xml;type=entry");//("itc Employee Database", "employee");
	        report("The Collection Element1", collection.toString());

	        // Create the entry to post to the collection
	        
	        Feed feed=factory.newFeed();
	        feed.setId("itc.com:employeefeed");
	        feed.setUpdated(new AtomDate().getDate());
	        feed.setTitle("EmployeeFeed");
	        feed.addLink("http://localhost:9080/EmployeeService/employee/feed", "self");
	        
	        Entry entry = factory.newEntry();
	        
	        entry.setPublished(new AtomDate().getDate());
	        entry.setId("tag:acme.com,2007:employee:entry:1001");
	      //  entry.setId("tag:itc.com");
	        entry.setTitle("Kesh");
	       entry.setUpdated(new AtomDate().getDate());
	        entry.addAuthor("syed");

	        entry.setContent("<employee><firstname>Kiran</firstname><lastname>Kumar</lastname><jobrole>IT  Consultant</jobrole><projectDetails>appollo</projectDetails><type>perm</type></employee>", Type.XML);//addAuthor(arg0)setContentAsHtml("Keshav9999999");
	        feed.addEntry(entry);
	        report("The Entry to Post", entry.toString());
	        report("here is the post URL ",collection.getResolvedHref().toString());

	        // Post the entry. Be sure to grab the resolved HREF of the collection
	    ClientResponse resp=abderaClient.post(collection.getResolvedHref().toString(), entry);
	    
	    
	 //   System.out.println(abderaClient.put("http://localhost:8080/EmployeeService/employee/1001-keshavGopalaiah", entry).getStatus());
	    
	   // resp.getDocument();
	/*  try {
		System.out.println("Here i got this doc "+resp.getReader().toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	      
	        // In some implementations (such as Google's GData API, the entry URI is
	        // distinct from it's edit URI. To be safe, we should assume it may be
	        // different
	        /*try{
	        IRI entryUri = doc.getBaseUri();
	        report("The Created Entry", doc.getRoot().toString());

	        // Grab the Edit URI from the entry. The entry MAY have more than one
	        // edit link. We need to make sure we grab the right one.
	        IRI editUri = getEditUri(doc.getRoot());
	        report("Edit Url for the created entry is ", editUri.toString());

	        // If there is an Edit Link, we can edit the entry
	       if (editUri != null) {
	            // Before we can edit, we need to grab an "editable" representation
	    	   
	            doc =abderaClient.get(editUri.toString()).getDocument();

	            // Change whatever you want in the retrieved entry
	            doc.getRoot().getTitleElement().setValue("This is the changed title");

	            // Put it back to the server
	            abderaClient.put(editUri.toString(), doc.getRoot());

	            // This is just to show that the entry has been modified
	            doc = abderaClient.get(entryUri.toString()).getDocument();
	            report("The Modified Entry", doc.getRoot().toString());
	        } else {
	            // Otherwise, the entry cannot be modified (no suitable edit link was found)
	            report("The Entry cannot be modified", null);
	        }

	        // Delete the entry. Again, we need to make sure that we have the current
	        // edit link for the entry
	        doc = abderaClient.get(entryUri.toString()).getDocument();
	      
				editUri = getEditUri(doc.getRoot());
			
	        if (editUri != null) {
	            abderaClient.delete(editUri.toString());
	            report("The Enry has been deleted", null);
	        } else {
	            report("The Entry cannot be deleted", null);
	        }
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
*/	        }
	    private static IRI getEditUri(Entry element) throws Exception {
	        IRI editUri = null;
	        //Entry entry=(Entry)element;
	        List<Link> editLinks = element.getLinks("edit");
	        for (Link link : editLinks) {
	            // if there is more than one edit link, we should not automatically
	            // assume that it's always going to point to an Atom document
	            // representation.
	            if (link.getMimeType() != null) {
	                if (link.getMimeType().match("application/atom+xml")) {
	                    editUri = link.getResolvedHref();
	                    break;
	                }
	            } else { // assume that an edit link with no type attribute is the right one to use
	                editUri = link.getResolvedHref();
	                break;
	            }
	        }
	        System.out.println("what is the Url i am getting here");
	        return editUri;
	    }

	    private static void report(String title, String message) {
	        System.out.println("== " + title + " ==");
	        if (message != null)
	            System.out.println(message);
	        System.out.println();
	    }
	
	
		

	}


