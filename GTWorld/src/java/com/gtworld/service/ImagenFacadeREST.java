/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Imagen;
import com.gtworld.entity.Poi;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author Antonio
 */
@Stateless
@Path("com.gtworld.entity.imagen")
public class ImagenFacadeREST extends AbstractFacade<Imagen> {

    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;
    @Context
    ServletContext context;

    public ImagenFacadeREST() {
        super(Imagen.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Imagen entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Imagen entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /*
     * @GET @Path("{id}") @Produces({"application/xml", "application/json"})
     * public Imagen find(@PathParam("id") Long id) { return super.find(id); }
     */
    @GET
    @Path("{idPoi}")
    @Produces("application/json")
    public List<Imagen> findByPoi(@PathParam("idPoi") Long id) {
        Poi poi = new Poi(id);
        Object[] parameters = {"idPoi", poi};
        List<Imagen> imagenes = super.find("Imagen.findByIdPoi", parameters);
        if (!imagenes.isEmpty()) {
            return imagenes;
        } else {
            return null;
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postImage(MultiPart multiPart) {
        try {
            String path = context.getRealPath("/Images/Users/").concat("\\");
            ByteArrayBody byteArray = (ByteArrayBody) multiPart.getBodyParts().get(0).getEntity();
            String fileName = byteArray.getFilename();
            StringBody strBody = (StringBody) multiPart.getBodyParts().get(1).getEntity();
            String idImg = strBody.getFilename();
            strBody = (StringBody) multiPart.getBodyParts().get(2).getEntity();
            String idUser = strBody.getFilename();
            byteArray = null;
            BodyPartEntity bpe = (BodyPartEntity) multiPart.getBodyParts().get(1).getEntity();
            if (JsfUtil.saveImage(IOUtils.toByteArray(bpe.getInputStream()), path + fileName)) {

                Long l = Long.valueOf(idImg);
                if (l.equals(new Long(1))) {
                    Imagen nueva = new Imagen();
                    nueva.setDescripcionImagen(idUser);
                    nueva.setTituloImagen(idUser);
                    nueva.setUrlImagen("/Images/Users/".concat(fileName));
                    super.create(nueva);
                    return Response.created(URI.create(nueva.getCorrelativoImagen().toString())).entity(nueva.getCorrelativoImagen().toString()).build();
                } else {
                    Imagen actual = new Imagen(l);
                    actual = super.find(actual);
                    actual.setUrlImagen("/Images/Users/".concat(fileName));
                    super.edit(actual);
                    return Response.created(URI.create(actual.getCorrelativoImagen().toString())).entity(actual.getCorrelativoImagen().toString()).build();

                }


            } else {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(ImagenFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Imagen> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Imagen> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
