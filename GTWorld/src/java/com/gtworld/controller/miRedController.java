/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.controller;
import com.gtworld.entity.Red;
import com.gtworld.entity.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.DragDropEvent;


/**
 *
 * @author ale
 */
@ManagedBean(name="miRedController")
@RequestScoped
public class miRedController implements Serializable {

    private boolean misPOIs=false; 
    private boolean todosLosPOIs=false; 
    public String centerMap;
    private List<Red> redList; 
    private List<Red> redDrop;
    
    
    private Date date1;  
    private Date date2;
    private Usuario User;
    
    @EJB
    private com.gtworld.facade.RedFacade RedFacade;
    
    public miRedController() {
       
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
          
      }
    
  
    public void imprimirMapa()
    {
        
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
    
    public List<Red> getListaRed(){
         Object[] parameters = {"idUsuario", "admin"};
          redList = RedFacade.find("Red.findByUser",parameters); 
          return redList;
    }
    
    
     
    
    public void onRedDrop(DragDropEvent ddEvent) {  
        Red red = ((Red) ddEvent.getData());  
  
        redDrop.add(red);  
        redList.remove(red);  
    }  
  
  
    public List<Red> getRedDrop() {  
        return redDrop;  
    }
   
}

