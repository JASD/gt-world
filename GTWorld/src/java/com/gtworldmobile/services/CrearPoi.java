/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworldmobile.services;

import com.gtworld.entity.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author rokEr
 */
@WebServlet(name = "CrearPoi", urlPatterns = {"/CrearPoi"})
@MultipartConfig
public class CrearPoi extends HttpServlet {

    @EJB
    private com.gtworld.facade.PoiFacade poiFacade;
    @EJB
    private com.gtworld.facade.UbicacionFacade ubicaFacade;
    @EJB
    private com.gtworld.facade.ImagenFacade imagenFacade;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrearPoi</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearPoi at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
        String esImagen=request.getHeader("imagen");
        if(esImagen.contains("si")){
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fieldname = item.getFieldName();
                    String filename = FilenameUtils.getName(item.getName());
                
                    if(filename.endsWith(".jpg")){
                        InputStream filecontent = item.getInputStream();
                        ImageUploader ImageSave = new ImageUploader();                    
                        String Path= getServletContext().getRealPath("/Images/POIs/").concat("\\").replace("build\\", "");
                        System.out.println(Path);
                        if(ImageSave.guardarImagen(Path,filecontent,filename)){
                            Poi idPoi=new Poi();
                            Imagen nuevaImagen = new Imagen();
                            try{
                                idPoi.setIdPoi(Long.parseLong(request.getHeader("IDPoi")));
                                nuevaImagen.setIdPoi(idPoi);
                                nuevaImagen.setTituloImagen(request.getHeader("Nombre"));
                                nuevaImagen.setDescripcionImagen("");
                                nuevaImagen.setUrlImagen("Images/POIs/" + filename);
                                imagenFacade.create(nuevaImagen);
                                response.addHeader("Respuesta", "Su foto ha sido guardada");
                            }catch (Exception e){
                                response.addHeader("Respuesta", "Error al guardar en BD");
                            }        
                        }else
                            response.addHeader("Respuesta", "Error al subir foto");
                    }else{
                        response.addHeader("Respuesta", "Archivo incorrecto");            
                    }
                }
            }
        }else{
            
            Poi nuevoPoi =new Poi();
            Usuario user =new Usuario();
            Ubicacion ubc = new Ubicacion();
            TipoPoi tp = new TipoPoi();
            Calendar calendar = new GregorianCalendar();
            
            user.setIdUsuario(request.getParameter("IDUser"));
            tp.setIdTipoPoi(Long.parseLong(request.getParameter("TipoPoi").toString()));
            
            ubc.setLatitudUbicacion(Double.parseDouble(request.getParameter("Latitud").toString()));
            ubc.setLongitudUbicacion(Double.parseDouble(request.getParameter("Longitud").toString()));
            ubc.setAltitudUbicacion(Double.parseDouble(request.getParameter("Altitud").toString()));
          try {
            ubicaFacade.create(ubc);
            
            nuevoPoi.setIdUbicacion(ubc);
            nuevoPoi.setIdTipoPoi(tp);
            nuevoPoi.setIdUsuario(user);
            nuevoPoi.setNombrePoi(request.getParameter("Nombre"));
            nuevoPoi.setDescripcionPoi(request.getParameter("Descripcion"));
            nuevoPoi.setUrlWebPoi(request.getParameter("URLweb"));
            nuevoPoi.setFechaCreacionPoi(calendar.getTime());
            nuevoPoi.setPrivacidadPoi(request.getParameter("Publico").toString().contains("1"));
            
            poiFacade.create(nuevoPoi);
            
            response.addHeader("RespuestaDat", "Datos guardados");
            response.addHeader("IDPoi", nuevoPoi.getIdPoi().toString());            
          } catch (Exception e){
              System.out.println(e.getMessage());
              response.addHeader("RespuestaDat", "Error al guardar los datos");
              response.addHeader("IDPoi", "null");
          }
            
        }
    } catch (FileUploadException e) {
        throw new ServletException("Cannot parse multipart request.", e);      
    }
 
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
