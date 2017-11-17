/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.cir;

import java.io.Serializable;
 

/**
 *
 * @author jeSer
 */
public class HorariosQx implements Serializable{
    private short nCve;
    private String sDescipcion;
    private Quirofanos oQx;
    
    public HorariosQx(){}
    
    public HorariosQx(short nCve, String sDesc, Quirofanos obj){
        this.nCve = nCve;
        this.sDescipcion = sDesc;
        this.oQx = obj;
    }
}
