/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.Evento;
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
@Path("com.gtworld.entity.evento")
public class EventoFacadeREST extends AbstractFacade<Evento> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public EventoFacadeREST() {
        super(Evento.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Evento entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Evento entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }
/*
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Evento find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces("application/json")
    public List<Evento> findAll() {
        return super.findAll();
    }
  */  
    @GET
    @Path("{idPoi}")
    @Produces("application/json")
    public List<Evento> findByPoi(@PathParam("idPoi") Long idPoi) {
        Object[] parameters = {"idPoi", idPoi};
        return super.findByPoi("Evento.findByPoi",parameters);
        
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Evento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
