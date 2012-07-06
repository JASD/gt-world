package com.gtworld.session;

import com.gtworld.controller.util.JsfUtil;
import com.gtworld.controller.util.PaginationHelper;
import com.gtworld.entity.*;
import com.gtworld.facade.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private Usuario user;
    private Usuario newUser;
    private String idUser;
    private String passUser;
    private String currentUI;
    private MapModel lastVisitPoi;
    private Marker selectedMarker;
    private List<Notificacion> unreadNotificaciones;
    private Notificacion selectedNotificacion;
    private String centerMap;
    private Poi current;
    private DataModel items = null;
    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    @EJB
    private com.gtworld.facade.ImagenFacade imagenFacade;
    @EJB
    private com.gtworld.facade.UsuarioFacade usuarioFacade;
    @EJB
    private com.gtworld.facade.NotificacionFacade notificacionFacade;
    @EJB
    private com.gtworld.facade.VisitaPoiFacade visitaFacade;
    private List<Imagen> uploaded = new ArrayList<Imagen>();
    private List<Imagen> viewSelected;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private MapModel poiModel;
    private boolean isEditing;
    private String descripcionFotos;
    private String picture;
    private UploadedFile foto;

    public SessionController() {
    }

    /**
     * Inicia Sesión de Usuario
     */
    public void login() {
        Object[] parameters = {"idUsuario", getIdUser(), "contrasenaUsuario",
            getPassUser()};
        try {
            setUser(getUsuarioFacade().getSingleResult("Usuario.findByLogin", parameters));
            setCurrentUI("UI/home/main.xhtml");
            //loadVisits();
            loadProfilePicture();
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
        setPagination(null);
        items = null;
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
        if (user != null) {
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
        if (user == null) {
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
        Matcher m = p.matcher(getNewUser().getEmailUsuario());
        boolean matchFound = m.matches();
        if (!matchFound) {
            JsfUtil.addErrorMessage("Email Incorrecto");
        } else {
            Calendar calendar = new GregorianCalendar();
            getNewUser().setFechaIngresoUsuario(calendar.getTime());
            getNewUser().setTipoUsuario(false);
            Notificacion nueva = new Notificacion();
            nueva.setEstadoNotificacion(true);
            nueva.setTituloNotificacion("Bienvenido a GTWorld!");
            nueva.setFechaNotificacion(calendar.getTime());
            nueva.setContenidoNotificacion("Tu cuenta de GTWorld ha sido creada"
                    + " con éxito, empieza a descubrir y explorar tus Puntos"
                    + " de Interés!");
            getNewUser().setCorrelativoImagen(
                    getImagenFacade().find(new Long(1)));
            try {
                getUsuarioFacade().create(newUser);
                nueva.setIdUsuario(newUser);
                getNotificacionFacade().create(nueva);
                getNewUser().getNotificacionList().add(nueva);
                setUser(newUser);
                Thread.sleep(2000);
                loadProfilePicture();
                setCurrentUI("UI/home/main.xhtml");
                JsfUtil.redirect("faces/home.xhtml");
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
        setNewUser(new Usuario());
    }

    /**
     * Carga las últimas 5 visitas a un POI del usuario
     */
    public void loadVisits() {
        setLastVisitPoi(new DefaultMapModel());
        Object[] parameters = {"idUsuario", getUser().getIdUsuario()};

        try {

            List<VisitaPoi> visits = getVisitaFacade().findRange(new int[]{0, 5},
                    "VisitaPoi.findByLastFechaVisitaPoi", parameters);
            if (!visits.isEmpty()) {
                boolean isFirst = true;

                for (VisitaPoi visita : visits) {
                    Double lat = visita.getPoi().getIdUbicacion().getLatitudUbicacion();
                    Double lon = visita.getPoi().getIdUbicacion().getLongitudUbicacion();
                    LatLng coord = new LatLng(lat, lon);
                    lastVisitPoi.addOverlay(new Marker(coord,
                            visita.getPoi().getNombrePoi(), visita,
                            visita.getPoi().getIdTipoPoi().getUrlIconoPoi()));
                    if (isFirst) {
                        isFirst = false;
                        centerMap = lat.toString() + "," + lon.toString();
                    }

                }
            } else {
                centerMap = "13.734,-89.29389";
            }

        } catch (Exception e) {
        }

    }

    /**
     * Carga las notificaciones no leidas por el Usuario
     */
    public void loadNotificactions() {
        Object[] parameters = {"idUsuario", getUser()};
        try {
            setUnreadNotificaciones(getNotificacionFacade().find(
                    "Notificacion.findByUsuarioEstadoNotificacion", parameters));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("NotificationsError"));
        }
    }

    /**
     * Cierra una notificación
     */
    public void closeNotification(CloseEvent event) {
        try {
            Notificacion close = (Notificacion) event.getComponent().getAttributes().get("noti");
            close.setEstadoNotificacion(false);
            getNotificacionFacade().edit(close);
            getUnreadNotificaciones().remove(close);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("NotificationsError"));
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        selectedMarker = (Marker) event.getOverlay();
    }

    public String getCurrentUI() {
        return currentUI;
    }

    public void setCurrentUI(String currentUI) {
        this.currentUI = currentUI;
    }

    private UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public NotificacionFacade getNotificacionFacade() {
        return notificacionFacade;
    }

    public VisitaPoiFacade getVisitaFacade() {
        return visitaFacade;
    }

    public void setVisitaFacade(VisitaPoiFacade visitaFacade) {
        this.visitaFacade = visitaFacade;
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

    public MapModel getLastVisitPoi() {
        return lastVisitPoi;
    }

    public void setLastVisitPoi(MapModel lastVisitPoi) {
        this.lastVisitPoi = lastVisitPoi;
    }

    public Marker getSelectedMarker() {
        return selectedMarker;
    }

    public void setSelectedMarker(Marker selectedMarker) {
        this.selectedMarker = selectedMarker;
    }

    public Notificacion getSelectedNotificacion() {
        return selectedNotificacion;
    }

    public void setSelectedNotificacion(Notificacion selectedNotificacion) {
        this.selectedNotificacion = selectedNotificacion;
    }

    public List<Notificacion> getUnreadNotificaciones() {
        return unreadNotificaciones;
    }

    public void setUnreadNotificaciones(List<Notificacion> unreadNotificaciones) {
        this.unreadNotificaciones = unreadNotificaciones;
    }

    public String getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(String centerMap) {
        this.centerMap = centerMap;
    }

    public Usuario getNewUser() {
        return newUser;
    }

    public void setNewUser(Usuario newUser) {
        this.newUser = newUser;
    }

    /**
     * Todo lo relacionado con los POIs del usuario
     */
    public Poi getSelected() {
        if (current == null) {
            current = new Poi();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(9) {

                Object[] parameters = {"idUsuario", getUser()};

                @Override
                public int getItemsCount() {
                    return getPoiFacade().getCont();
                }

                @Override
                public DataModel createPageDataModel() {

                    return new ListDataModel(getPoiFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "Poi.findByUser", parameters));
                }
            };
        }
        return pagination;
    }

    public void prepareList() {
        recreateModel();
    }

    public void prepareView() {
        current = (Poi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public void prepareViewOnMap() {
        current = (Poi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setPoiModel(new DefaultMapModel());
        LatLng coord = new LatLng(current.getIdUbicacion().getLatitudUbicacion(),
                current.getIdUbicacion().getLongitudUbicacion());
        poiModel.addOverlay(new Marker(coord,
                current.getNombrePoi(), current,
                current.getIdTipoPoi().getUrlIconoPoi()));
    }

    public void prepareViewImages() {
        current = (Poi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        Object[] parameters = {"idPoi", current};
        try {
            viewSelected = getImagenFacade().find("Imagen.findByIdPoi", parameters);
            if (viewSelected.isEmpty()) {
                Imagen img = new Imagen();
                img.setTituloImagen("Imagen no Encontrada");
                img.setDescripcionImagen("Imagen no Encontrada");
                img.setUrlImagen("Images/POIs/image_not_found.jpg");
                viewSelected.add(img);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }

    }

    public void prepareEdit() {
        current = (Poi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setIsEditing(true);
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

    public void poiImageUpload(FileUploadEvent event) {

        UploadedFile imageUpload = event.getFile();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("/Images/POIs/").concat("\\");
        if (JsfUtil.saveImage(imageUpload.getContents(),
                path + imageUpload.getFileName())) {
            Imagen imagen = new Imagen();
            imagen.setIdPoi(current);
            imagen.setTituloImagen(current.getNombrePoi());
            imagen.setDescripcionImagen("");
            imagen.setUrlImagen("Images/POIs/" + imageUpload.getFileName());
            try {
                getImagenFacade().create(imagen);
                getUploaded().add(imagen);
                JsfUtil.addSuccessMessage(imageUpload.getFileName() + " Guardado");
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addErrorMessage(imageUpload.getFileName() + " no guardado");
        }
    }

    public void update() {
        try {
            getPoiFacade().edit(current);
            for (Imagen img : uploaded) {
                img.setDescripcionImagen(descripcionFotos);
                getImagenFacade().edit(img);
                current.getImagenList().add(img);
            }
            descripcionFotos = "";
            setUploaded(new ArrayList<Imagen>());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PoiUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void destroy() {
        current = (Poi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        try {
            for (Imagen img : current.getImagenList()) {
                getImagenFacade().remove(img);
            }
            for (VisitaPoi vp : current.getVisitaPoiList()) {
                getVisitaFacade().remove(vp);
            }
            performDestroy();
            recreatePagination();
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void destroyAndView() {
        try {
            for (Imagen img : current.getImagenList()) {
                getImagenFacade().remove(img);
            }
            for (VisitaPoi vp : current.getVisitaPoiList()) {
                getVisitaFacade().remove(vp);
            }
            performDestroy();
            recreatePagination();
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performDestroy() {
        try {
            getPoiFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PoiDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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

    public void next() {
        getPagination().nextPage();
        recreateModel();
    }

    public void previous() {
        getPagination().previousPage();
        recreateModel();
    }

    public Poi getCurrent() {
        return current;
    }

    public void setCurrent(Poi current) {
        this.current = current;
    }

    public String getDescripcionFotos() {
        return descripcionFotos;
    }

    public void setDescripcionFotos(String descripcionFotos) {
        this.descripcionFotos = descripcionFotos;
    }

    public ImagenFacade getImagenFacade() {
        return imagenFacade;
    }

    public void setImagenFacade(ImagenFacade imagenFacade) {
        this.imagenFacade = imagenFacade;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public void setPagination(PaginationHelper pagination) {
        this.pagination = pagination;
    }

    public PoiFacade getPoiFacade() {
        return poiFacade;
    }

    public void setPoiFacade(PoiFacade poiFacade) {
        this.poiFacade = poiFacade;
    }

    public MapModel getPoiModel() {
        return poiModel;
    }

    public void setPoiModel(MapModel poiModel) {
        this.poiModel = poiModel;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<Imagen> getUploaded() {
        return uploaded;
    }

    public void setUploaded(List<Imagen> uploaded) {
        this.uploaded = uploaded;
    }

    public List<Imagen> getViewSelected() {
        return viewSelected;
    }

    public void setViewSelected(List<Imagen> viewSelected) {
        this.viewSelected = viewSelected;
    }

    /**
     * Todo lo relacionado con la Configuración de cuenta del usuario
     */
    public void loadProfilePicture() {
        setPicture(getUser().getCorrelativoImagen().getUrlImagen());

    }

    public void updateProfile() {

        Imagen eliminar = user.getCorrelativoImagen();

        if (foto != null && foto.getContentType().equals("image/jpeg")) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/Images/Users/").concat("\\");
            if (JsfUtil.saveImage(foto.getContents(),
                    path + foto.getFileName())) {
                boolean borra = JsfUtil.deleteImage(path
                        + user.getCorrelativoImagen().getUrlImagen().substring(13));
                if (!borra) {
                    //Logger :no se pudo borrar foto anterior
                }
                Imagen nueva = new Imagen();
                nueva.setTituloImagen(user.getIdUsuario());
                nueva.setDescripcionImagen(user.getIdUsuario());
                nueva.setUrlImagen("/Images/Users/" + foto.getFileName());
                getImagenFacade().create(nueva);
                user.setCorrelativoImagen(nueva);
                loadProfilePicture();

            } else {
                JsfUtil.addErrorMessage("Ocurrio un Error al Guardar Foto");
            }
        }
        try {
            getUsuarioFacade().edit(user);
            if (eliminar.getCorrelativoImagen().compareTo(new Long(19)) != 0) {
                getImagenFacade().remove(eliminar);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            foto = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }
}
