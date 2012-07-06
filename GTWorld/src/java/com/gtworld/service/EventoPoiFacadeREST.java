/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.EventoPoi;
import com.gtworld.entity.EventoPoiPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;

/**
 *
 * @author Antonio
 */
@Stateless
@Path("com.gtworld.entity.eventopoi")
public class EventoPoiFacadeREST extends AbstractFacade<EventoPoi> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public EventoPoiFacadeREST() {
        super(EventoPoi.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(EventoPoi entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(EventoPoi entity) {
        super.edit(entity);
    }

    /*
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") EventoPoiPK id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public EventoPoi find(@PathParam("id") EventoPoiPK id) {
        return super.find(id);
    }*/

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<EventoPoi> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<EventoPoi> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
