/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexis
 */
@Entity
@Table(name = "salacultural")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salacultural.findAll", query = "SELECT s FROM Salacultural s")
    , @NamedQuery(name = "Salacultural.findByIdsala", query = "SELECT s FROM Salacultural s WHERE s.idsala = :idsala")
    , @NamedQuery(name = "Salacultural.findByNombresala", query = "SELECT s FROM Salacultural s WHERE s.nombresala = :nombresala")})
public class Salacultural implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsala")
    private Integer idsala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombresala")
    private String nombresala;
    @JoinColumn(name = "idedificio", referencedColumnName = "idedificio")
    @ManyToOne
    private Edificio idedificio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idsala")
    private Collection<Espaciocultural> espacioculturalCollection;

    public Salacultural() {
    }

    public Salacultural(Integer idsala) {
        this.idsala = idsala;
    }

    public Salacultural(Integer idsala, String nombresala) {
        this.idsala = idsala;
        this.nombresala = nombresala;
    }

    public Integer getIdsala() {
        return idsala;
    }
    

    public void setIdsala(Integer idsala) {
        this.idsala = idsala;
    }

    public String getNombresala() {
        return nombresala;
    }

    public void setNombresala(String nombresala) {
        this.nombresala = nombresala;
    }

    public Edificio getIdedificio() {
        return idedificio;
        
    }
    
    public String getidEdificio(Edificio e){
        return e.getNombreedificio();
    }
    

    public void setIdedificio(Edificio idedificio) {
        this.idedificio = idedificio;
    }

    @XmlTransient
    public Collection<Espaciocultural> getEspacioculturalCollection() {
        return espacioculturalCollection;
    }

    public void setEspacioculturalCollection(Collection<Espaciocultural> espacioculturalCollection) {
        this.espacioculturalCollection = espacioculturalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsala != null ? idsala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salacultural)) {
            return false;
        }
        Salacultural other = (Salacultural) object;
        if ((this.idsala == null && other.idsala != null) || (this.idsala != null && !this.idsala.equals(other.idsala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingenieria.biblioteca.modelo.Salacultural[ idsala=" + idsala + " ]";
    }
    
}
