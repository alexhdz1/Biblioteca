/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "edificio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Edificio.findAll", query = "SELECT e FROM Edificio e")
    , @NamedQuery(name = "Edificio.findByIdedificio", query = "SELECT e FROM Edificio e WHERE e.idedificio = :idedificio")
    , @NamedQuery(name = "Edificio.findByNombreedificio", query = "SELECT e FROM Edificio e WHERE e.nombreedificio = :nombreedificio")})
public class Edificio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idedificio")
    private Integer idedificio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombreedificio")
    private String nombreedificio;
    @OneToMany(mappedBy = "idedificio")
    private Collection<Salacultural> salaculturalCollection;

    public Edificio() {
    }

    public Edificio(Integer idedificio) {
        this.idedificio = idedificio;
    }

    public Edificio(Integer idedificio, String nombreedificio) {
        this.idedificio = idedificio;
        this.nombreedificio = nombreedificio;
    }

    public Integer getIdedificio() {
        return idedificio;
    }

    public void setIdedificio(Integer idedificio) {
        this.idedificio = idedificio;
    }

    public String getNombreedificio() {
        return nombreedificio;
    }

    public void setNombreedificio(String nombreedificio) {
        this.nombreedificio = nombreedificio;
    }

    @XmlTransient
    public Collection<Salacultural> getSalaculturalCollection() {
        return salaculturalCollection;
    }

    public void setSalaculturalCollection(Collection<Salacultural> salaculturalCollection) {
        this.salaculturalCollection = salaculturalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idedificio != null ? idedificio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Edificio)) {
            return false;
        }
        Edificio other = (Edificio) object;
        if ((this.idedificio == null && other.idedificio != null) || (this.idedificio != null && !this.idedificio.equals(other.idedificio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingenieria.biblioteca.modelo.Edificio[ idedificio=" + idedificio + " ]";
    }
    
}
