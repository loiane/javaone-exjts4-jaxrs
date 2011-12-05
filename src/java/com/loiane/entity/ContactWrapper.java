/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.entity;

import java.io.IOException;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author loiane
 */
@XmlRootElement
public class ContactWrapper {
    
    private Contact contact;
    
    public ContactWrapper(String json) throws IOException, JSONException{
        JSONObject data = new JSONObject(json);
        contact = new ObjectMapper().readValue(data.getString("data"), Contact.class);
    }

    /**
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
}
