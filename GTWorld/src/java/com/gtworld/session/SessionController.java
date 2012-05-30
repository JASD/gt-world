package com.gtworld.session;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Usuario;
import com.gtworld.facade.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private Usuario user;
    private String idUser;
    private String passUser;
    private String currentUI;
    @EJB
    private com.gtworld.facade.UsuarioFacade ejbFacade;

    public SessionController() {
    }

    /**
     * Inicia Sesión de Usuario
     */
    public void login() {
        Object[] parameters = {"idUsuario", getIdUser(), "contrasenaUsuario",
            getPassUser()};
        try {
            setUser(getFacade().getSingleResult("Usuario.findByLogin", parameters));
            setCurrentUI("UI/home/main.xhtml");
            JsfUtil.redirect("faces/home.xhtml");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, "Usuario No Válido");
        }
    }

    /**
     * Cierra Sesión de Usuario
     */
    public void logout() {
        setUser(null);
        try {
            JsfUtil.redirect("index.xhtml");
        } catch (IOException ex) {
            JsfUtil.addErrorMessage(ex, null);
        }
    }

    /**
     * Verifica si la Sesión se encuentra activa
     */
    public void checkLogin() {
        if (getUser() != null) {
            try {
                JsfUtil.redirect("faces/home.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
    }

    /**
     * Verifica si la sesión ha expirado
     */
    public void checkLogout() {
        if (getUser() == null) {
            try {
                JsfUtil.redirect("faces/index.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
    }

    /**
     * Redirecciona a la Fan Page de Facebook
     */
    public void goFb() {
        try {
            JsfUtil.redirect("https://www.facebook.com/");
        } catch (IOException ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redirecciona a la página de Twitter
     */
    public void goTwitter() {
        try {
            JsfUtil.redirect("https://www.twitter.com/");
        } catch (IOException ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redireccion a la Ayuda del Sistema
     */
    public void goHelp() {
        try {
            JsfUtil.redirect("");
        } catch (IOException ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redirecciona a la página de Registro
     */
    public void goRegister() {
        try {
            JsfUtil.redirect("faces/register.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redirecciona a la página de inicio de sesión
     */
    public void goLogin() {

        try {
            JsfUtil.redirect("faces/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Crea un nuevo usuario
     */
    public void createUser() {

        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(getUser().getEmailUsuario());
        boolean matchFound = m.matches();
        if (!matchFound) {
            JsfUtil.addErrorMessage("Email Incorrecto");
        } else {
            Calendar calendar = java.util.Calendar.getInstance();
            getUser().setFechaIngresoUsuario(calendar.getTime());
            getUser().setTipoUsuario(false);
            try {
                getFacade().create(user);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

            }
        }

    }
    
    public void updateUI(ActionEvent event) {
        String ui = (String) event.getComponent().getAttributes().get("uiName");
        setCurrentUI("UI/" + ui + ".xhtml");
    }

    /**
     * Prepara la creación de un nuevo usuario
     */
    public void prepareCreate() {
        setUser(new Usuario());
    }

    public String getCurrentUI() {
        return currentUI;
    }

    public void setCurrentUI(String currentUI) {
        this.currentUI = currentUI;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }
}
