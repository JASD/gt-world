/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworldmobile.services;

import java.io.*;

/**
 *
 * @author rokEr
 */
public class ImageUploader {
    
    File image=null;
    
    public ImageUploader(){
        
    }
    
    public boolean guardarImagen(String Path,InputStream entrada,String NombreImagen){
        try{
	   image=new File(Path+NombreImagen);//Aqui le dan el nombre y/o con la ruta del archivo salida
	   OutputStream salida=new FileOutputStream(image);
	   byte[] buf =new byte[1024];//Actualizado me olvide del 1024
           int len;
	   while((len=entrada.read(buf))>0){
	      salida.write(buf,0,len);
	   }
	   salida.close();
	   entrada.close();
	   System.out.println("Se realizo la conversion con exito");
           return true;
	}catch(IOException e){
	   System.out.println("Se produjo el error : "+e.toString());
           return false;
	}
    }
}
