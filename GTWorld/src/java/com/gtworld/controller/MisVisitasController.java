/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.controller;

import com.gtworld.entity.Poi;
import com.gtworld.entity.Usuario;
import com.gtworld.entity.VisitaPoi;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author rokEr
 */
@ManagedBean(name="misVisitasController")
@SessionScoped
public class MisVisitasController {
      
    private MapModel mapModel; 
    private Marker marker; 
    public String centerMap;
    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    @EJB
    private com.gtworld.facade.VisitaPoiFacade visitaPoiFacade;
    private Usuario User;
    
    public MisVisitasController() {
         mapModel = new DefaultMapModel(); 
         
    }
  
    public MapModel getMapModel() {  
        return mapModel;  
    }
    
    public void setCenterMap(String centerMap){
        this.centerMap = centerMap;
    }
    
    public String getCenterMap(){
        return centerMap;
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {  
        marker = (Marker) event.getOverlay();  
    } 
    
     public Marker getMarker() {  
        return marker;  
    }
    

    public Usuario getUser() {
        return User;
    }

    public void setIdUser(Usuario User) {
        this.User = User;
        this.update();
    }
    
    public void update(){
        
        mapModel = new DefaultMapModel();
        List<VisitaPoi> visits=null;
        try {
           
            Object[] parameters = {"idUsuario", getUser().getIdUsuario()};
            visits = visitaPoiFacade.find("VisitaPoi.findByIdUsuario", parameters);
            
            if (!visits.isEmpty()) {
                boolean isFirst = true;

                for (VisitaPoi pois : visits) {
                    Double lat = pois.getPoi().getIdUbicacion().getLatitudUbicacion();
                    Double lon = pois.getPoi().getIdUbicacion().getLongitudUbicacion();
                    LatLng coord = new LatLng(lat, lon);
                    getMapModel().addOverlay(new Marker(coord,
                            pois.getPoi().getNombrePoi(), pois,
                            pois.getPoi().getIdTipoPoi().getUrlIconoPoi()));
                    if (isFirst) {
                        isFirst = false;
                        setCenterMap(lat.toString() + "," + lon.toString());
                    }

                }
            }

        } catch (Exception eex) {
           eex.printStackTrace();
        }
    }
}
