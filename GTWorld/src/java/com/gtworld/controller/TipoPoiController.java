package com.gtworld.controller;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.controller.util.PaginationHelper;
import com.gtworld.entity.Imagen;
import com.gtworld.entity.TipoPoi;
import com.gtworld.facade.TipoPoiFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "tipoPoiController")
@SessionScoped
public class TipoPoiController implements Serializable {

    private TipoPoi current;
    private DataModel items = null;
    @EJB
    private com.gtworld.facade.TipoPoiFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean isEditing;
    private UploadedFile icono;

    public TipoPoiController() {
    }

    public TipoPoi getSelected() {
        if (current == null) {
            current = new TipoPoi();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoPoiFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(12) {

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

    public void prepareList() {
        recreateModel();
    }

    public String prepareView() {
        current = (TipoPoi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() {
        current = new TipoPoi();
        selectedItemIndex = -1;
    }

    public void create() {

        if (icono != null
                && icono.getContentType().equals("image/png")) {

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/resources/poi_icon/").concat("\\");
            if (JsfUtil.saveImage(icono.getContents(),
                    path + icono.getFileName())) {
                current.setUrlIconoPoi("resources/poi_icon/" + icono.getFileName());
                try {
                    getFacade().create(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoPoiCreated"));
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } else {
                JsfUtil.addErrorMessage("Ocurrio un Error al Guardar Icono");
            }
        }else{
            JsfUtil.addErrorMessage("Icono no válido");
        }

    }

    public void closeEdit(ToggleEvent event) {
        String status = event.getVisibility().name();
        if (status.equals("VISIBLE") && !isEditing) {
            setIsEditing(true); //forzar cierre de panel
        }

        if (status.equals("HIDDEN") && isEditing) {
            setIsEditing(false);
            current = null;
        }
    }

    public String prepareEdit() {
        current = (TipoPoi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void update() {

        if (icono != null && icono.getContentType().equals("image/png")) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/resources/poi_icon/").concat("\\");
            if (JsfUtil.saveImage(icono.getContents(),
                    path + icono.getFileName())) {
                boolean borra = JsfUtil.deleteImage(path
                        + current.getUrlIconoPoi().substring(19));
                if (!borra) {
                    //Logger :no se pudo borrar icono anterior
                }
                current.setUrlIconoPoi("resources/poi_icon/" + icono.getFileName());
            } else {
                JsfUtil.addErrorMessage("Icono no válido");
            }
        }
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoPoiUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String destroy() {
        current = (TipoPoi) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoPoiDeleted"));
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
    
    public SelectItem[] getItemsSelect() {
        return JsfUtil.getSelectItems(ejbFacade.findAll());
    }
    
    public List<TipoPoi> autocomplete(String query) {
        List<TipoPoi> sugerencias = new ArrayList<TipoPoi>();
        for (TipoPoi x : ejbFacade.findAll()) {
            if (x.getNombreTipoPoi().toLowerCase().startsWith(query)) {
                sugerencias.add(x);
            }
        }
        return sugerencias;
    }
    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public UploadedFile getIcono() {
        return icono;
    }

    public void setIcono(UploadedFile icono) {
        this.icono = icono;
    }

    @FacesConverter(forClass = TipoPoi.class)
    public static class TipoPoiControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoPoiController controller = (TipoPoiController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoPoiController");
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
            if (object instanceof TipoPoi) {
                TipoPoi o = (TipoPoi) object;
                return getStringKey(o.getIdTipoPoi());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoPoiController.class.getName());
            }
        }
    }
}
