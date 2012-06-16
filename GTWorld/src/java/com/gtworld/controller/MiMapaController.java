/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.controller;

import com.gtworld.entity.Poi;
import com.gtworld.entity.Usuario;
import com.gtworld.entity.VisitaPoi;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
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
    private boolean misPOIsVisitados=false;
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
    
    public void imprimirMapa(){
        
    }
    
     public boolean isMisPOIs() {  
        return misPOIs;  
    }  
  
    public void setMisPOIs(boolean misPOIs) {  
        this.misPOIs = misPOIs;  
        update();
    }  
  
    public boolean isMisPOIsVisitados() {  
        return misPOIsVisitados;  
    }  
  
    public void setMisPOIsVisitados(boolean misPOIsVisitados) {  
        this.misPOIsVisitados = misPOIsVisitados;  
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
        
        try {
            
            if(isMisPOIs()&&!isMisPOIsVisitados()&&!isTodosLosPOIs()){ //SOLO POIS DEL USUARIO
               
               Object[] parameters = {"idUsuario", getUser()};
               poisList = poiFacade.find("Poi.findByUser",parameters); 
               
            }else if(isMisPOIs()&&!isMisPOIsVisitados()&&isTodosLosPOIs()){ //POIS DEL USUARIO Y LOS PUBLICOS
               
               Object[] parameters = {"idUsuario", getUser()};
               Object[] parameters2 = {"privacidadPoi", true};
               poisList = poiFacade.find("Poi.findByUser",parameters); 
               auxiliarList = poiFacade.find("Poi.findByPrivacidadPoi", parameters2);
               
               for(Poi x:auxiliarList){
                   if(!poisList.contains(x))
                       poisList.add(x);
               }
              
            }else if(isMisPOIs()&&isMisPOIsVisitados()&&!isTodosLosPOIs()){ //POIS DEL USUARIO Y LOS VISITADOS     
            }else if(!isMisPOIs()&&!isMisPOIsVisitados()&&isTodosLosPOIs()){
              
               Object[] parameters = {"privacidadPoi", true};
               poisList = poiFacade.find("Poi.findByPrivacidadPoi", parameters);
                
            }else if(!isMisPOIs()&&isMisPOIsVisitados()&&isTodosLosPOIs()){
            }else if(!isMisPOIs()&&isMisPOIsVisitados()&&!isTodosLosPOIs()){   
               
               Object[] parameters = {"idUsuario", User.getIdUsuario()};
               poisList = poiFacade.find("VisitaPoi.findByIdUsuario", parameters);
                
            }else if(isMisPOIs()&&isMisPOIsVisitados()&&isTodosLosPOIs()){ 
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
            } else {
              
            }

        } catch (Exception eex) {
           
        }
    }
}
