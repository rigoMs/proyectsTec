/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author GIL
 */
public class Destino implements Serializable
        {
    private AccesoDatos oAD;
    private int destino;
    private String nombre;
    private String calle;
   private String colonia;
   private String estado;
   private String clavemunicipio;
    List<String> arrRet;
    List<Destino> lLista;
   public Destino (int destino,String nombre,String calle,String colonia,String estado,String clavemunicipio){
       this.destino=destino;
       this.nombre=nombre;
       this.calle=calle;
       this.colonia=colonia;
       this.estado=estado;
       this.clavemunicipio=clavemunicipio;
      
   }
 public Destino(){
        arrRet=null;
        lLista=null;
        
    }
    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClavemunicipio() {
        return clavemunicipio;
    }

    public void setClavemunicipio(String clavemunicipio) {
        this.clavemunicipio = clavemunicipio;
    }
    public Destino[]buscarDestinos()throws Exception{
         Destino arrRet[]=null,oDes=null;
            ArrayList rst=null;
            ArrayList<Destino>vObj=null;
            String sQuery="";
            int i=0,nTam=0;
                sQuery="SELECT *FROM buscaDestino();";
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if (rst != null) {
			vObj = new ArrayList<Destino>();
			for (i = 0; i < rst.size(); i++) {
                            oDes = new Destino();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDes.setNombre((String)vRowTemp.get(0));
                             
                            vObj.add(oDes);
			}
                    nTam = vObj.size();
                    arrRet = new Destino[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
		
            
        }
    
    
    
    
    
}
