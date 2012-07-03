/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.VisitaPoi;
import com.gtworld.entity.VisitaPoiPK;
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
@Path("com.gtworld.entity.visitapoi")
public class VisitaPoiFacadeREST extends AbstractFacade<VisitaPoi> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public VisitaPoiFacadeREST() {
        super(VisitaPoi.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(VisitaPoi entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(VisitaPoi entity) {
        super.edit(entity);
    }

    /*@DELETE
    @Path("{id}")
    public void remove(@PathParam("id") VisitaPoiPK id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public VisitaPoi find(@PathParam("id") VisitaPoiPK id) {
        return super.find(id);
    }*/

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<VisitaPoi> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<VisitaPoi> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
