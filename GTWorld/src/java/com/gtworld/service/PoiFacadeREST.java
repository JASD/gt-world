/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.service;

import com.gtworld.entity.Poi;
import com.gtworldmobile.services.CalculoDistancias;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Antonio
 */
@Stateless
@Path("com.gtworld.entity.poi")
public class PoiFacadeREST extends AbstractFacade<Poi> {

    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    public PoiFacadeREST() {
        super(Poi.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Poi entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Poi entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Poi find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces("application/json")
    public String mxPois(
            @QueryParam("user") String id,
            @QueryParam("lon") Double lon,
            @QueryParam("lat") Double lat,
            @QueryParam("alt") Double alt,
            @QueryParam("rad") Double rad) {
        JSONArray array = new JSONArray();
        try {
            if (id != null) {
                Object[] parameters = {"idUsuario", id};
                List<Poi> pois = super.find("Poi.findByCoord", parameters);
                if (!pois.isEmpty()) {
                    for (Poi p : pois) {
                        CalculoDistancias dc = new CalculoDistancias(lat, p.getIdUbicacion().getLatitudUbicacion(), lon, p.getIdUbicacion().getLongitudUbicacion());
                        Double dist = dc.calcularDistanciaKm();
                        if (dist.compareTo(rad) < 0) {
                            JSONObject jObj = new JSONObject();
                            jObj.put("id", p.getIdPoi());
                            jObj.put("lat", p.getIdUbicacion().getLatitudUbicacion().toString());
                            jObj.put("lng", p.getIdUbicacion().getLongitudUbicacion().toString());
                            jObj.put("elevation", p.getIdUbicacion().getAltitudUbicacion().toString());
                            jObj.put("title", p.getNombrePoi());
                            jObj.put("distance", dist.toString());
                            String web = p.getUrlWebPoi();
                            if (web != null && !web.equals("")) {
                                jObj.put("webpage", web);
                                jObj.put("has_detail_page", "1");
                            } else {
                                jObj.put("has_detail_page", "0");
                            }
                            jObj.put("type", p.getIdTipoPoi().getUrlIconoPoi());
                            array.put(jObj);
                        }
                    }
                }
            }
            JSONObject jObj = new JSONObject();
            jObj.put("status", "OK");
            jObj.put("num_results", array.length());
            jObj.put("results", array);
            return jObj.toString();
        } catch (JSONException ex) {
            Logger.getLogger(PoiFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /*@GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Poi> findAll() {
        return super.findAll();
    }*/

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Poi> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
