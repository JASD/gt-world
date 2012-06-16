/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.controller;

import com.gtworld.entity.Poi;
import com.gtworld.entity.Usuario;
import java.awt.event.ActionEvent;
import java.io.Serializable;
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
@ManagedBean(name="miMapaController")
@SessionScoped
public class MiMapaController implements Serializable {
    
    private boolean misPOIs;  
    private boolean misZonasFrecuentes;
    private boolean todosLosPOIs;
    private MapModel mapModel; 
    private Marker marker; 
    private String centerMap;
    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    private List<String> selectedOptions; 
    
    public MiMapaController() {
        
        mapModel = new DefaultMapModel(); 
    }
    
    public MapModel getMapModel() {  
        return mapModel;  
    }
    
     public List<String> getSelectedOptions() {  
        return selectedOptions;  
    }  
    public void setSelectedOptions(List<String> selectedOptions) {  
        this.selectedOptions = selectedOptions;  
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
    
    public void imprimirMapa(){
        
    }
    
     public boolean isMisPOIs() {  
        return misPOIs;  
    }  
  
    public void setMisPOIs(boolean misPOIs) {  
        this.misPOIs = misPOIs;  
    }  
  
    public boolean isMisZonasFrecuentes() {  
        return misZonasFrecuentes;  
    }  
  
    public void setMisZonasFrecuentes(boolean misZonasFrecuentes) {  
        this.misZonasFrecuentes = misZonasFrecuentes;  
    }  
    public boolean isTodosLosPOIs() {  
        return todosLosPOIs;  
    }  
  
    public void setTodosLosPOIs(boolean todosLosPOIs) {  
        this.todosLosPOIs = todosLosPOIs;  
    }  
    public void update(ActionEvent actionEvent){
        
        Usuario user = new Usuario();
        user.setIdUsuario("admin");
        Object[] parameters = {"idUsuario", user};
        try {

            List<Poi> poisList = poiFacade.find("Poi.findByUser",parameters);
            if (!poisList.isEmpty()) {
                boolean isFirst = true;

                for (Poi pois : poisList) {
                    Double lat = pois.getIdUbicacion().getLatitudUbicacion();
                    Double lon = pois.getIdUbicacion().getLongitudUbicacion();
                    LatLng coord = new LatLng(lat, lon);
                    mapModel.addOverlay(new Marker(coord,
                            pois.getNombrePoi(), pois,
                            pois.getIdTipoPoi().getUrlIconoPoi()));
                    if (isFirst) {
                        isFirst = false;
                        centerMap = lat.toString() + "," + lon.toString();
                    }

                }
            } else {
                centerMap = "13.734,-89.29389";
            }

        } catch (Exception e) {
            centerMap = "13.734,-89.29389";
        }
    }
}
