/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.maps;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Poi;
import com.gtworld.entity.Ubicacion;
import com.gtworld.entity.Usuario;
import com.gtworld.facade.ImagenFacade;
import com.gtworld.facade.PoiFacade;
import com.gtworld.facade.UbicacionFacade;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Antonio
 */
@ManagedBean(name = "createPOIController")
@SessionScoped
public class CreatePOI implements Serializable {

    @EJB
    private com.gtworld.facade.PoiFacade ejbFacade;
    @EJB
    private com.gtworld.facade.ImagenFacade imagenFacade;
     @EJB
    private com.gtworld.facade.UbicacionFacade ubicaFacade;
    private MapModel pois;
    private Marker selectedMarker;
    private Poi nuevo;
    private double lat;
    private double lng;
    private String descripcionFotos;

    public CreatePOI() {
        pois = new DefaultMapModel();
        nuevo = new Poi();
    }
   
    
    public void savePoi(ActionEvent event){
        
        Usuario user = (Usuario) event.getComponent().getAttributes().get("user");
        //Para futuras versiones se debe comprobar si ya existe ubicación
        Ubicacion ubc = new Ubicacion();
        ubc.setLatitudUbicacion(lat);
        ubc.setLongitudUbicacion(lng);
        Calendar calendar = new GregorianCalendar();
        try {
            getUbicaFacade().create(ubc);
            nuevo.setFechaCreacionPoi(calendar.getTime());
            nuevo.setIdUbicacion(ubc);
            nuevo.setIdUsuario(user);
            getEjbFacade().create(nuevo);
            Marker marker = new Marker(new LatLng(lat, lng), nuevo.getNombrePoi(), nuevo,
                            nuevo.getIdTipoPoi().getUrlIconoPoi());  
            pois.addOverlay(marker);  
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PoiCreated"));
            nuevo = new Poi();
        } catch (Exception e){
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    public PoiFacade getEjbFacade() {
        return ejbFacade;
    }

    public ImagenFacade getImagenFacade() {
        return imagenFacade;
    }

    public UbicacionFacade getUbicaFacade() {
        return ubicaFacade;
    }

    public MapModel getPois() {
        return pois;
    }

    public void setPois(MapModel pois) {
        this.pois = pois;
    }

    public Marker getSelectedMarker() {
        return selectedMarker;
    }

    public void setSelectedMarker(Marker selectedMarker) {
        this.selectedMarker = selectedMarker;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Poi getNuevo() {
        return nuevo;
    }

    public void setNuevo(Poi nuevo) {
        this.nuevo = nuevo;
    }

    public String getDescripcionFotos() {
        return descripcionFotos;
    }

    public void setDescripcionFotos(String descripcionFotos) {
        this.descripcionFotos = descripcionFotos;
    }
}
