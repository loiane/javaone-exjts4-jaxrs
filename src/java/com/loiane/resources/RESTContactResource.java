/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.resources;

import com.loiane.entity.Contact;
import com.loiane.entity.ContactWrapper;
import com.loiane.entity.ExtJSContactReturn;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author loiane
 */
@Path("/rest")
public class RESTContactResource {
    
    @PersistenceContext(unitName="extjs4-jaxrsPU")
    EntityManager em;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ExtJSContactReturn view(
        @QueryParam("start") int start,
        @QueryParam("limit") int limit
            ){
        
        List<Contact> contacts = new ArrayList<Contact>();
        
        ExtJSContactReturn extReturn = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("extjs4-jaxrsPU");
        
        em = emf.createEntityManager();
        
        try{
            
          Query query = em.createNamedQuery("test");
          query.setFirstResult(start);
          query.setMaxResults(limit);
          
          contacts = query.getResultList();
          
          extReturn = new ExtJSContactReturn(getTotalContacts(), contacts, true);
            
        }catch(Exception ex){
            System.err.print(ex.getMessage());
            
            extReturn = new ExtJSContactReturn(0, contacts, false);
        } finally{
            return extReturn;
        }
    }
    
    private int getTotalContacts(){
     
        Query query = em.createNamedQuery("getContactCount");
        
        Number count =(Number) query.getSingleResult();
        
        return count.intValue();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ExtJSContactReturn update(String json) throws IOException{
        
        ExtJSContactReturn extReturn = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("extjs4-jaxrsPU");
        
        em = emf.createEntityManager();
 
        List<Contact> contacts = new ArrayList<Contact>();
        
        try {
            ContactWrapper wrapper = new ContactWrapper(json);
            Contact contact = wrapper.getContact();
            
            UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            
            Contact contactUpdate = em.find(Contact.class, contact.getId());
            contactUpdate.setName(contact.getName());
            
            transaction.commit();
            
            contacts.add(contactUpdate);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, true);
            
        } catch (Exception ex) {
            Logger.getLogger(ContactResource.class.getName()).log(Level.SEVERE, null, ex);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, false);
        } finally {
            em.close();
            return extReturn;
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public ExtJSContactReturn create (String json) throws IOException{
        
        ExtJSContactReturn extReturn = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("extjs4-jaxrsPU");
        
        em = emf.createEntityManager();
 
        List<Contact> contacts = new ArrayList<Contact>();
        
        try {
            ContactWrapper wrapper = new ContactWrapper(json);
            Contact contact = wrapper.getContact();
            
            UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            
            em.persist(contact);
            
            transaction.commit();
            
            contacts.add(contact);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, true);
            
        } catch (Exception ex) {
            Logger.getLogger(ContactResource.class.getName()).log(Level.SEVERE, null, ex);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, false);
        } finally {
            em.close();
            return extReturn;
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ExtJSContactReturn delete (String json) throws IOException{
        
        ExtJSContactReturn extReturn = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("extjs4-jaxrsPU");
        
        em = emf.createEntityManager();
 
        List<Contact> contacts = new ArrayList<Contact>();
        
        try {
            ContactWrapper wrapper = new ContactWrapper(json);
            Contact contact = wrapper.getContact();
            
            UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            
            Contact contactUpdate = em.find(Contact.class, contact.getId());
            em.remove(contactUpdate);
            
            transaction.commit();
            
            contacts.add(contact);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, true);
            
        } catch (Exception ex) {
            Logger.getLogger(ContactResource.class.getName()).log(Level.SEVERE, null, ex);
            
            extReturn = new ExtJSContactReturn(contacts.size(), contacts, false);
        } finally {
            em.close();
            return extReturn;
        }
    }
}
