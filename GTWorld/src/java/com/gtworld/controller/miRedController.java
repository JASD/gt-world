/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.controller;
import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Miembro;
import com.gtworld.entity.Red;
import com.gtworld.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;


/**
 *
 * @author ale
 */
@ManagedBean(name="miRedController")
@RequestScoped
public class miRedController implements Serializable {

    private boolean misPOIs=false; 
    private boolean todosLosPOIs=false; 
    private Red selectedRed=new Red();
    private List<Usuario> usuario= new ArrayList<Usuario>();
  
    private String selectedUser;
    
    private Date date1;  
    private Date date2;
    private Usuario User;
    
    @EJB
    private com.gtworld.facade.RedFacade RedFacade;
    
    public miRedController() {
       
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
    
    
    public boolean isMisPOIs() {  
        return misPOIs;  
    }  
  
    public void setMisPOIs(boolean misPOIs) {  
        this.misPOIs = misPOIs;  
        //update();
    } 
    
     public boolean isTodosLosPOIs() {  
        return todosLosPOIs;  
    }  
  
    public void setTodosLosPOIs(boolean todosLosPOIs) {  
        this.todosLosPOIs = todosLosPOIs;  
       // update();
    }

    public Usuario getUser() {
        return User;
    }
    
      public void setIdUser(Usuario User) {
        this.User = User;
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
    
    
    
    public SelectItem[] getItemsRedes() {
        Object[] parameters = {"idUsuario", "admin"};
        return JsfUtil.getSelectItems(RedFacade.find("Red.findByUser", parameters));
    }
 
       public SelectItem[] getItemsMiembros() {
       List<Miembro> usuarios= selectedRed.getMiembroList(); 
       for(Miembro miembros:usuarios){
           usuario.add(miembros.getUsuario());           
       }
       
       List<String> IdUser= new ArrayList<String>();
       for(Usuario x: usuario){
           IdUser.add(x.getIdUsuario());
       }
       
       return JsfUtil.getSelectItems(IdUser);
    }
}

