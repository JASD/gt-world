/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.AsistenciaEvento;
import com.gtworld.entity.AsistenciaEventoPK;
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
@Path("com.gtworld.entity.asistenciaevento")
public class AsistenciaEventoFacadeREST extends AbstractFacade<AsistenciaEvento> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public AsistenciaEventoFacadeREST() {
        super(AsistenciaEvento.class);
    }

    @POST
    @Override
    @Consumes("application/json")
    public void create(AsistenciaEvento entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes("application/json")
    public void edit(AsistenciaEvento entity) {
        super.edit(entity);
    }

    /*
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") AsistenciaEventoPK id) {
        super.remove(super.find(id));
    }
*/
    @GET
    @Path("{idPoi}/{idUser}/{idEvent}")
    @Produces("application/json")
    public List<AsistenciaEvento> findByIds(@PathParam("idPoi") Long idPoi, @PathParam("idEvent") Long idEvento, @PathParam("idUser") String idUser){
        AsistenciaEventoPK As = new AsistenciaEventoPK();
        As.setIdEvento(idEvento);
        As.setIdPoi(idPoi);
        As.setIdUsuario(idUser);
        Object[] parameters = {"idPoi", As.getIdPoi(),"idUsuario",As.getIdUsuario(),"idEvento",As.getIdEvento()};
        
        List<AsistenciaEvento> asist = super.find("AsistenciaEvento.findByIds", parameters);
        if (!asist.isEmpty()) {
            return asist;
        } else {
            return null;
        }
    }

    @GET
    @Override
    @Produces("application/json")
    public List<AsistenciaEvento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{id}/{num}")
    @Produces("application/json")
    public List<AsistenciaEvento> findLast(@PathParam("id") String id, @PathParam("num") Integer num) {
        
        Object[] parameters = {"idUsuario", id};
        List<AsistenciaEvento> visits = super.findRange(new int[]{0, num},
                "AsistenciaEvento.findByLastFechaEvento", parameters);
        if (!visits.isEmpty()) {
            return visits;
        } else {
            return null;
        }
    }
    
    /*@GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AsistenciaEvento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/

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
