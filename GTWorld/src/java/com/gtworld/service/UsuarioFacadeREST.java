/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.Usuario;
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
@Path("com.gtworld.entity.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes("application/json")
    public void edit(Usuario entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/{id}/{pass}")
    @Produces("application/json")
    public Usuario find(@PathParam("id") String id,
                        @PathParam("pass") String pass) {
        Object[] parameters = {"idUsuario", id, "contrasenaUsuario",
            pass};
        try {
            Usuario u = super.getSingleResult("Usuario.findByLogin", parameters);
            return u;
        } catch (Exception ex) {
            
            return null;
        }
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
