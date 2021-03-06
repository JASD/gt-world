/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.maps;

import com.gtworld.entity.Poi;
import com.gtworld.entity.Imagen;
import com.gtworld.entity.Usuario;
import com.gtworld.entity.VisitaPoi;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.component.log.Log;
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
    
    private boolean misPOIs=false;  
    private boolean todosLosPOIs=false;    
    private MapModel mapModel; 
    private Marker marker; 
    public String centerMap;
    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    @EJB
    private com.gtworld.facade.VisitaPoiFacade visitaPoiFacade;
    private Usuario User;
  
    
    public MiMapaController() {
        
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
    
     public boolean isMisPOIs() {  
        return misPOIs;  
    }  
  
    public void setMisPOIs(boolean misPOIs) {  
        this.misPOIs = misPOIs;  
        update();
    }  
   
    public boolean isTodosLosPOIs() {  
        return todosLosPOIs;  
    }  
  
    public void setTodosLosPOIs(boolean todosLosPOIs) {  
        this.todosLosPOIs = todosLosPOIs;  
        update();
    }

    public Usuario getUser() {
        return User;
    }

    public void setIdUser(Usuario User) {
        this.User = User;
    }

    
    
    
    public void update(){
        
        mapModel = new DefaultMapModel();
        List<Poi> poisList=null,auxiliarList=null;
        List<VisitaPoi> visitas=null;
        try {
            
            if(isMisPOIs()&&!isTodosLosPOIs()){ //SOLO POIS DEL USUARIO
               
               Object[] parameters = {"idUsuario", getUser()};
               poisList = poiFacade.find("Poi.findByUser",parameters); 
               
            }else if(isMisPOIs()&&isTodosLosPOIs()){ //POIS DEL USUARIO Y LOS PUBLICOS
               
               Object[] parameters = {"idUsuario", getUser()};
               Object[] parameters2 = {"privacidadPoi", true};
               poisList = poiFacade.find("Poi.findByUser",parameters); 
               auxiliarList = poiFacade.find("Poi.findByPrivacidadPoi", parameters2);
               
               for(Poi x:auxiliarList){
                   if(!poisList.contains(x))
                       poisList.add(x);
               }
             
            }else if(!isMisPOIs()&&isTodosLosPOIs()){ // SOLO POIS PUBLICOS
              
               Object[] parameters = {"privacidadPoi", true};
               poisList = poiFacade.find("Poi.findByPrivacidadPoi", parameters);
                
            } 
            
            if (!poisList.isEmpty()) {
                boolean isFirst = true;

                for (Poi pois : poisList) {
                    Double lat = pois.getIdUbicacion().getLatitudUbicacion();
                    Double lon = pois.getIdUbicacion().getLongitudUbicacion();
                    LatLng coord = new LatLng(lat, lon);
                    getMapModel().addOverlay(new Marker(coord,
                            pois.getNombrePoi(), pois,
                            pois.getIdTipoPoi().getUrlIconoPoi()));
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
