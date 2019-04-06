/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.web;

import com.ingenieria.biblioteca.controlador.SalaculturalJpaController;
import com.ingenieria.biblioteca.modelo.Edificio;
import com.ingenieria.biblioteca.modelo.PersistenceUtil;
import com.ingenieria.biblioteca.modelo.Salacultural;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import com.ingenieria.biblioteca.controlador.EdificioJpaController;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author alexis
 */
@ManagedBean
@RequestScoped
public class SalaculturalController {

    SalaculturalJpaController jpa;
    Salacultural salacultural;
    List<Salacultural> lista;
    EdificioJpaController jpa2;
    List<Edificio> edificios;
    Edificio edificio;
    int tmpid;

    /**
     * Creates a new instance of SalaController
     */
    public SalaculturalController() {
        jpa = new SalaculturalJpaController(PersistenceUtil.getEntityManagerFactory());
        salacultural = new Salacultural();
        lista = jpa.findSalaculturalEntities();
        jpa2 = new EdificioJpaController(PersistenceUtil.getEntityManagerFactory());
        edificios = jpa2.findEdificioEntities();
        edificio = new Edificio();
        tmpid = 0;
    }

    public SalaculturalJpaController getJpa() {
        return jpa;
    }

    public void setJpa(SalaculturalJpaController jpa) {
        this.jpa = jpa;
    }

    public Salacultural getSalacultural() {
        return salacultural;
    }

    public void setSalacultural(Salacultural salacultural) {
        this.salacultural = salacultural;
    }

    public List<Salacultural> getLista() {
        return lista;
    }

    public int getTmpid() {
        return tmpid;
    }

    public void setTmpid(int tmpid) {
        this.tmpid = tmpid;
    }

    public void setLista(List<Salacultural> lista) {
        this.lista = lista;
    }

    public void guardar() {
        
        System.err.println("=======================Hola=============================");
        
        if (existeEdificio(tmpid)) {
            salacultural.setIdedificio(jpa2.findEdificio(tmpid));
            jpa.guardar(salacultural);
            System.err.println("Hola");
        } else {
            muestraMensaje("El edificio no exitse con el identifiador: " + tmpid);

        }

        lista = jpa.findSalaculturalEntities();
    }

    public boolean existeEdificio(int id) {

        for (Edificio e : edificios) {
            if (e.getIdedificio().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void muestraMensaje(String mensaje) {
        FacesMessage mensajeFace = new FacesMessage(mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(mensajeFace);
    }
    
    public List<String> nombresSalas(){
        List<String> a = new ArrayList<>();
        for(Salacultural s: lista){
            a.add(s.getNombresala());
        }
        return a;
    }
    
    public List<Integer> idSalas(){
        List<Integer> a = new ArrayList<>();
        for(Salacultural s: lista){
            a.add(s.getIdsala());
        }
        return a;
    }
        
    
}
