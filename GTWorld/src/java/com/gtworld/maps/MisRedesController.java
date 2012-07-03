/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.maps;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Miembro;
import com.gtworld.entity.Poi;
import com.gtworld.entity.Red;
import com.gtworld.entity.Usuario;
import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author rokEr
 */
@Named(value = "misRedesController")
@SessionScoped
public class MisRedesController implements Serializable {

   private boolean misPOIs=false; 
    private boolean todosLosPOIs=false; 
    private MapModel mapModel; 
    private Marker marker; 
    private String centerMap;
    private Red selectedRed=new Red();
    private String selectedUser= "";
    List<String> IdUser= new ArrayList<String>();
    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    
    private List<Usuario> usuario= new ArrayList<Usuario>();    
    
    private Date date1;  
    private Date date2;
    private Usuario User;
    
    @EJB
    private com.gtworld.facade.RedFacade RedFacade;
    
    public MisRedesController() {
       mapModel = new DefaultMapModel(); 
    }
    
    public MapModel getMapModelRed() {  
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
    
    public void setSelectedRed(Red selectedRed){
        this.selectedRed=selectedRed;
    }
    
    public Red getSelectedRed(){
        return selectedRed;
    }
    
     public void setSelectedUser(String selectedUser){
        this.selectedUser=selectedUser;
    }
    
    public String getSelectedUser(){
        return selectedUser;
    }
    
   public Date getDate1() {  
        return date1;  
    }  
  
    public void setDate1(Date date1) {  
        this.date1 = date1;  
    }  
    
    public Date getDate2() {  
        return date2;  
    }  
  
    public void setDate2(Date date2) {  
        this.date2 = date2;  
    }  
    
    
    
    public SelectItem[] getItemsRedes() {
        Object[] parameters = {"idUsuario", getUser().getIdUsuario()};
        return JsfUtil.getSelectItems(RedFacade.find("Red.findByUser", parameters));
    }
 
    public SelectItem[] getItemsMiembros() {
       if(selectedRed.getIdUsuario()!=null){
       List<Miembro> usuarios= selectedRed.getMiembroList(); 
       usuario= new ArrayList<Usuario>();
       
       for(Miembro miembros:usuarios){
           if(!usuario.contains(miembros))
           usuario.add(miembros.getUsuario());           
       }
       
       clearIdUsers();
       for(Usuario x: usuario){
           if(!IdUser.contains(x))
           IdUser.add(x.getIdUsuario());
       }
       
       return JsfUtil.getSelectItems(IdUser);
       }else{
           List<String> Error= new ArrayList<String>();
           Error.add("Selecciona una red");
           return JsfUtil.getSelectItems(Error);
       }
    }
       
    public void update(){
        
        mapModel = new DefaultMapModel();
        List<Poi> poisList=new ArrayList<Poi>(),auxiliarList=null;
 
        try {
            
            if(isMisPOIs()&&!isTodosLosPOIs()){ //SOLO POIS DEL USUARIO
               Usuario miembro=new Usuario();
               miembro.setIdUsuario(getSelectedUser());
               Object[] parameters = {"idUsuario", miembro};
               poisList = poiFacade.find("Poi.findByUser",parameters); 
       
               
            }else if(isMisPOIs()&&isTodosLosPOIs()){ //POIS DEL USUARIO Y LOS PUBLICOS
                
            }else if(!isMisPOIs()&&isTodosLosPOIs()){ // SOLO POIS PUBLICOS
    
               Object[] parameters = {"idRed", getSelectedRed().getIdRed()};
               poisList = poiFacade.find("Poi.findByRed",parameters); 
                
            } 
            
            if (!poisList.isEmpty()) {
                boolean isFirst = true;

                for (Poi pois : poisList) {
                    Double lat = pois.getIdUbicacion().getLatitudUbicacion();
                    Double lon = pois.getIdUbicacion().getLongitudUbicacion();
                    LatLng coord = new LatLng(lat, lon);
                    getMapModelRed().addOverlay(new Marker(coord,
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
    
    public void clearIdUsers() {  
       IdUser= new ArrayList<String>();
    } 
}
