/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexis
 */
@Entity
@Table(name = "espaciocultural")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Espaciocultural.findAll", query = "SELECT e FROM Espaciocultural e")
    , @NamedQuery(name = "Espaciocultural.findByIdevento", query = "SELECT e FROM Espaciocultural e WHERE e.idevento = :idevento")
    , @NamedQuery(name = "Espaciocultural.findByFecha", query = "SELECT e FROM Espaciocultural e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Espaciocultural.findByNombreevento", query = "SELECT e FROM Espaciocultural e WHERE e.nombreevento = :nombreevento")
    , @NamedQuery(name = "Espaciocultural.findByHorainicio", query = "SELECT e FROM Espaciocultural e WHERE e.horainicio = :horainicio")
    , @NamedQuery(name = "Espaciocultural.findByHorafinal", query = "SELECT e FROM Espaciocultural e WHERE e.horafinal = :horafinal")
    , @NamedQuery(name = "Espaciocultural.findByReservado", query = "SELECT e FROM Espaciocultural e WHERE e.reservado = :reservado")})
public class Espaciocultural implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevento")
    private Integer idevento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombreevento")
    private String nombreevento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horafinal")
    @Temporal(TemporalType.TIME)
    private Date horafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reservado")
    private boolean reservado;
    @JoinColumn(name = "idprofesor", referencedColumnName = "idprofesor")
    @ManyToOne
    private Profesor idprofesor;
    @JoinColumn(name = "idsala", referencedColumnName = "idsala")
    @ManyToOne(optional = false)
    private Salacultural idsala;

    public Espaciocultural() {
    }

    public Espaciocultural(Integer idevento) {
        this.idevento = idevento;
    }

    public Espaciocultural(Integer idevento, Date fecha, String nombreevento, Date horainicio, Date horafinal, boolean reservado) {
        this.idevento = idevento;
        this.fecha = fecha;
        this.nombreevento = nombreevento;
        this.horainicio = horainicio;
        this.horafinal = horafinal;
        this.reservado = reservado;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreevento() {
        return nombreevento;
    }

    public void setNombreevento(String nombreevento) {
        this.nombreevento = nombreevento;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Date horafinal) {
        this.horafinal = horafinal;
    }

    public boolean getReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public Profesor getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(Profesor idprofesor) {
        this.idprofesor = idprofesor;
    }

    public Salacultural getIdsala() {
        return idsala;
    }

    public void setIdsala(Salacultural idsala) {
        this.idsala = idsala;
    }
    
       public String getidSala(Salacultural s){
        return s.getNombresala();
    }
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idevento != null ? idevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Espaciocultural)) {
            return false;
        }
        Espaciocultural other = (Espaciocultural) object;
        if ((this.idevento == null && other.idevento != null) || (this.idevento != null && !this.idevento.equals(other.idevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingenieria.biblioteca.modelo.Espaciocultural[ idevento=" + idevento + " ]";
    }
    
     public String formatDate(Date d){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        
        return formatDate.format(d);
        
    }
 
    public String formaTime(Date d){
        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss");
        return formatTime.format(d);
        
}
    
    
    
    
    
    
    
    
}
