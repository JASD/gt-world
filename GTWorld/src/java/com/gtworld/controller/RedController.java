package com.gtworld.controller;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.controller.util.PaginationHelper;
import com.gtworld.entity.*;
import com.gtworld.facade.MiembroFacade;
import com.gtworld.facade.NotificacionFacade;
import com.gtworld.facade.RedFacade;
import com.gtworld.facade.UsuarioFacade;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "redController")
@SessionScoped
public class RedController implements Serializable {

    private Red current;
    private DataModel items = null;
    @EJB
    private com.gtworld.facade.RedFacade ejbFacade;
    @EJB
    private com.gtworld.facade.UsuarioFacade usuarioFacade;
    @EJB
    private com.gtworld.facade.MiembroFacade miembroFacade;
    @EJB
    private com.gtworld.facade.NotificacionFacade notificacionFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Usuario> miembros;
    private String email;
    private Usuario actual;
    private Red red;
    private List<Miembro> miembro;
    private boolean redSelected;
    private Miembro selected;

    public RedController() {
    }

    public Red getSelected() {
        if (current == null) {
            current = new Red();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RedFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(16) {

                Object[] parameters = {"idUsuario", getActual().getIdUsuario()};

                @Override
                public int getItemsCount() {
                    return getFacade().getCont();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "Red.findByUser", parameters));
                }
            };
        }
        return pagination;
    }

    public void prepareList() {
        recreateModel();
    }

    public void salirRed() {
        Object[] parameters = {"idUsuario", getActual().getIdUsuario(), "idRed", current.getIdRed()};
        try {
            Miembro m = getMiembroFacade().getSingleResult("Miembro.findByUserRed", parameters);
            getMiembroFacade().remove(m);
            recreatePagination();
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MiembroDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void viewMembers() {
        Object[] parameters = {"idRed", getRed().getIdRed()};
        setMiembro(getMiembroFacade().find("Miembro.findByIdRed", parameters));
        setRedSelected(true);

    }

    public void reset() {
        setRedSelected(false);
        red = null;
        miembro = null;
        prepareView();
    }

    public void prepareView() {
        current = (Red) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public void addUser() {

        Object[] parameters = {"emailUsuario", getEmail()};
        try {
            Usuario usuario = getUsuarioFacade().getSingleResult("Usuario.findByEmailUsuario", parameters);
            getMiembros().add(usuario);
            email = "";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No se Encontro Usuario");

        }
    }

    public void prepareCreate() {
        current = new Red();
        miembros = new ArrayList<Usuario>();
        email = "";
        selectedItemIndex = -1;
    }


    public void addMember() {

        Calendar calendar = new GregorianCalendar();
        try {
            for (Usuario user : getMiembros()) {
                MiembroPK pk = new MiembroPK(user.getIdUsuario(), red.getIdRed());
                Miembro mb = new Miembro(pk);
                mb.setRed(red);
                mb.setUsuario(user);
                mb.setFechaMiembro(calendar.getTime());
                Notificacion nueva = new Notificacion();
                nueva.setEstadoNotificacion(true);
                nueva.setTituloNotificacion("Te Agregaron a una RED!");
                nueva.setFechaNotificacion(calendar.getTime());
                nueva.setContenidoNotificacion("Ahora Perteneces a la Red "
                        + red.getNombreRed()
                        + " creada por " + red.getIdUsuario().getNombreUsuario());
                nueva.setIdUsuario(user);
                getMiembroFacade().create(mb);
                getNotificacionFacade().create(nueva);
            }
            JsfUtil.addSuccessMessage("Miembros Agregados");
            prepareCreate();
            viewMembers();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }

    }

    public void create() {
        current.setIdUsuario(actual);
        Calendar calendar = new GregorianCalendar();
        current.setFechaCreacionRed(calendar.getTime());
        try {
            getFacade().create(current);
            MiembroPK pka = new MiembroPK(actual.getIdUsuario(), current.getIdRed());
            Miembro mba = new Miembro(pka);
            mba.setRed(current);
            mba.setUsuario(actual);
            mba.setFechaMiembro(calendar.getTime());
            getMiembroFacade().create(mba);
            for (Usuario user : getMiembros()) {
                MiembroPK pk = new MiembroPK(user.getIdUsuario(), current.getIdRed());
                Miembro mb = new Miembro(pk);
                mb.setRed(current);
                mb.setUsuario(user);
                mb.setFechaMiembro(calendar.getTime());
                Notificacion nueva = new Notificacion();
                nueva.setEstadoNotificacion(true);
                nueva.setTituloNotificacion("Te Agregaron a una RED!");
                nueva.setFechaNotificacion(calendar.getTime());
                nueva.setContenidoNotificacion("Ahora Perteneces a la Red "
                        + current.getNombreRed()
                        + " creada por " + actual.getNombreUsuario());
                nueva.setIdUsuario(user);
                getMiembroFacade().create(mb);
                getNotificacionFacade().create(nueva);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedCreated"));
            prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void prepareEdit() {
        current = (Red) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public void update() {
        try {
            if (current.getIdUsuario().getIdUsuario().equals(getActual().getIdUsuario())) {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedUpdated"));
            } else {
                JsfUtil.addErrorMessage("No puedes Editar esta Red");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String destroy() {
        current = (Red) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public void setPagination(PaginationHelper pagination) {
        this.pagination = pagination;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public List<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getActual() {
        return actual;
    }

    public void setActual(Usuario actual) {
        this.actual = actual;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public MiembroFacade getMiembroFacade() {
        return miembroFacade;
    }

    public NotificacionFacade getNotificacionFacade() {
        return notificacionFacade;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public boolean isRedSelected() {
        return redSelected;
    }

    public void setRedSelected(boolean redSelected) {
        this.redSelected = redSelected;
    }

    public Miembro getMemberSelected() {
        return selected;
    }

    public void setMemberSelected(Miembro selected) {
        this.selected = selected;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsRedes() {
        Object[] parameters = {"idUsuario", getActual().getIdUsuario()};
        return JsfUtil.getSelectItems(ejbFacade.find("Red.findByUser", parameters));
    }

    public List<Miembro> getMiembro() {
        return miembro;
    }

    public void setMiembro(List<Miembro> miembro) {
        this.miembro = miembro;
    }

    @FacesConverter(forClass = Red.class)
    public static class RedControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RedController controller = (RedController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "redController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Red) {
                Red o = (Red) object;
                return getStringKey(o.getIdRed());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RedController.class.getName());
            }
        }
    }
}
