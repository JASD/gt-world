package com.gtworld.session;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.entity.Usuario;
import com.gtworld.facade.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private Usuario user;
    private String idUser = "usuario";
    private String passUser = "contraseña";
    @EJB
    private com.gtworld.facade.UsuarioFacade ejbFacade;

    public SessionController() {
    }

    public void login() {
        Object[] parameters = {"idUsuario", getIdUser(), "contrasenaUsuario", 
            getPassUser()};
        try {
            setUser(getFacade().getSingleResult("Usuario.findByLogin", parameters));
            JsfUtil.redirect("faces/home.xhtml");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, "Usuario No Válido");
        }
    }

    public void logout() {
        setUser(null);
        try {
            JsfUtil.redirect("index.xhtml");
        } catch (IOException ex) {
            JsfUtil.addErrorMessage(ex, null);
        }
    }

    public void checkLogin() {
        if (getUser() != null) {
            try {
                JsfUtil.redirect("faces/home.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
    }

    public void checkLogout() {
        if (getUser() == null) {
            try {
                JsfUtil.redirect("faces/index.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage(ex, null);
            }
        }
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
