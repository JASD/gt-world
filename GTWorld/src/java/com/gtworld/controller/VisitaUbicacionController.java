package com.gtworld.controller;

import com.gtworld.entity.VisitaUbicacion;
import com.gtworld.controller.util.JsfUtil;
import com.gtworld.controller.util.PaginationHelper;
import com.gtworld.facade.VisitaUbicacionFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
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

@ManagedBean(name = "visitaUbicacionController")
@SessionScoped
public class VisitaUbicacionController implements Serializable {

    private VisitaUbicacion current;
    private DataModel items = null;
    @EJB
    private com.gtworld.facade.VisitaUbicacionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public VisitaUbicacionController() {
    }

    public VisitaUbicacion getSelected() {
        if (current == null) {
            current = new VisitaUbicacion();
            current.setVisitaUbicacionPK(new com.gtworld.entity.VisitaUbicacionPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private VisitaUbicacionFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (VisitaUbicacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new VisitaUbicacion();
        current.setVisitaUbicacionPK(new com.gtworld.entity.VisitaUbicacionPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitaUbicacionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (VisitaUbicacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitaUbicacionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (VisitaUbicacion) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitaUbicacionDeleted"));
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

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
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

    @FacesConverter(forClass = VisitaUbicacion.class)
    public static class VisitaUbicacionControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VisitaUbicacionController controller = (VisitaUbicacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "visitaUbicacionController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.gtworld.entity.VisitaUbicacionPK getKey(String value) {
            com.gtworld.entity.VisitaUbicacionPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.gtworld.entity.VisitaUbicacionPK();
            key.setIdUbicacion(Long.parseLong(values[0]));
            key.setIdUsuario(values[1]);
            key.setFechaVisitaUbicacion(java.sql.Date.valueOf(values[2]));
            return key;
        }

        String getStringKey(com.gtworld.entity.VisitaUbicacionPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getIdUbicacion());
            sb.append(SEPARATOR);
            sb.append(value.getIdUsuario());
            sb.append(SEPARATOR);
            sb.append(value.getFechaVisitaUbicacion());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VisitaUbicacion) {
                VisitaUbicacion o = (VisitaUbicacion) object;
                return getStringKey(o.getVisitaUbicacionPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + VisitaUbicacionController.class.getName());
            }
        }
    }
}
