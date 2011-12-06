/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.resources.service;

import com.loiane.entity.Contact;
import com.loiane.entity.ContactWrapper;
import com.loiane.entity.ExtJSContactReturn;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author loiane
 */
@Stateless
@Path("contacts")
public class ContactFacadeREST extends AbstractFacade<Contact> {
    @PersistenceContext(unitName = "extjs4-jaxrsPU")
    private EntityManager em;

    public ContactFacadeREST() {
        super(Contact.class);
    }

    @POST
    @Produces("application/json")
    @Consumes({"application/xml", "application/json"})
    public ExtJSContactReturn create(String json) throws IOException, JSONException {
        ContactWrapper wrapper = new ContactWrapper(json);
        Contact entity = wrapper.getContact();
        super.create(entity);
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(entity);
        ExtJSContactReturn extReturn = new ExtJSContactReturn(String.valueOf(contacts.size()), contacts, true);
        return extReturn;
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes({"text/plain,text/html,application/json"})
    public ExtJSContactReturn edit(String json) throws IOException, JSONException {
        ContactWrapper wrapper = new ContactWrapper(json);
        Contact entity = wrapper.getContact();
        super.edit(entity);
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(entity);
        ExtJSContactReturn extReturn = new ExtJSContactReturn(String.valueOf(contacts.size()), contacts, true);
        return extReturn;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Contact find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({"application/json"})
    public ExtJSContactReturn findAll(@QueryParam("start") int start,
        @QueryParam("limit") int limit, @QueryParam("page") int page) {
        
        List<Contact> contacts = super.findRange(new int[]{start, limit*page});
        
        ExtJSContactReturn extReturn = new ExtJSContactReturn(countREST(), contacts, true);
        
        return extReturn;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Contact> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
