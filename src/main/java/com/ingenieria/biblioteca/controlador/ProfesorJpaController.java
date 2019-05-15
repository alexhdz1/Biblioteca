/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.controlador;

import com.ingenieria.biblioteca.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ingenieria.biblioteca.modelo.Espaciocultural;
import com.ingenieria.biblioteca.modelo.Profesor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexis
 */
public class ProfesorJpaController implements Serializable {

    public ProfesorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesor profesor) {
        if (profesor.getEspacioculturalCollection() == null) {
            profesor.setEspacioculturalCollection(new ArrayList<Espaciocultural>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Espaciocultural> attachedEspacioculturalCollection = new ArrayList<Espaciocultural>();
            for (Espaciocultural espacioculturalCollectionEspacioculturalToAttach : profesor.getEspacioculturalCollection()) {
                espacioculturalCollectionEspacioculturalToAttach = em.getReference(espacioculturalCollectionEspacioculturalToAttach.getClass(), espacioculturalCollectionEspacioculturalToAttach.getIdevento());
                attachedEspacioculturalCollection.add(espacioculturalCollectionEspacioculturalToAttach);
            }
            profesor.setEspacioculturalCollection(attachedEspacioculturalCollection);
            em.persist(profesor);
            for (Espaciocultural espacioculturalCollectionEspaciocultural : profesor.getEspacioculturalCollection()) {
                Profesor oldIdprofesorOfEspacioculturalCollectionEspaciocultural = espacioculturalCollectionEspaciocultural.getIdprofesor();
                espacioculturalCollectionEspaciocultural.setIdprofesor(profesor);
                espacioculturalCollectionEspaciocultural = em.merge(espacioculturalCollectionEspaciocultural);
                if (oldIdprofesorOfEspacioculturalCollectionEspaciocultural != null) {
                    oldIdprofesorOfEspacioculturalCollectionEspaciocultural.getEspacioculturalCollection().remove(espacioculturalCollectionEspaciocultural);
                    oldIdprofesorOfEspacioculturalCollectionEspaciocultural = em.merge(oldIdprofesorOfEspacioculturalCollectionEspaciocultural);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profesor profesor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor persistentProfesor = em.find(Profesor.class, profesor.getIdprofesor());
            Collection<Espaciocultural> espacioculturalCollectionOld = persistentProfesor.getEspacioculturalCollection();
            Collection<Espaciocultural> espacioculturalCollectionNew = profesor.getEspacioculturalCollection();
            Collection<Espaciocultural> attachedEspacioculturalCollectionNew = new ArrayList<Espaciocultural>();
            for (Espaciocultural espacioculturalCollectionNewEspacioculturalToAttach : espacioculturalCollectionNew) {
                espacioculturalCollectionNewEspacioculturalToAttach = em.getReference(espacioculturalCollectionNewEspacioculturalToAttach.getClass(), espacioculturalCollectionNewEspacioculturalToAttach.getIdevento());
                attachedEspacioculturalCollectionNew.add(espacioculturalCollectionNewEspacioculturalToAttach);
            }
            espacioculturalCollectionNew = attachedEspacioculturalCollectionNew;
            profesor.setEspacioculturalCollection(espacioculturalCollectionNew);
            profesor = em.merge(profesor);
            for (Espaciocultural espacioculturalCollectionOldEspaciocultural : espacioculturalCollectionOld) {
                if (!espacioculturalCollectionNew.contains(espacioculturalCollectionOldEspaciocultural)) {
                    espacioculturalCollectionOldEspaciocultural.setIdprofesor(null);
                    espacioculturalCollectionOldEspaciocultural = em.merge(espacioculturalCollectionOldEspaciocultural);
                }
            }
            for (Espaciocultural espacioculturalCollectionNewEspaciocultural : espacioculturalCollectionNew) {
                if (!espacioculturalCollectionOld.contains(espacioculturalCollectionNewEspaciocultural)) {
                    Profesor oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural = espacioculturalCollectionNewEspaciocultural.getIdprofesor();
                    espacioculturalCollectionNewEspaciocultural.setIdprofesor(profesor);
                    espacioculturalCollectionNewEspaciocultural = em.merge(espacioculturalCollectionNewEspaciocultural);
                    if (oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural != null && !oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural.equals(profesor)) {
                        oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural.getEspacioculturalCollection().remove(espacioculturalCollectionNewEspaciocultural);
                        oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural = em.merge(oldIdprofesorOfEspacioculturalCollectionNewEspaciocultural);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profesor.getIdprofesor();
                if (findProfesor(id) == null) {
                    throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor profesor;
            try {
                profesor = em.getReference(Profesor.class, id);
                profesor.getIdprofesor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.", enfe);
            }
            Collection<Espaciocultural> espacioculturalCollection = profesor.getEspacioculturalCollection();
            for (Espaciocultural espacioculturalCollectionEspaciocultural : espacioculturalCollection) {
                espacioculturalCollectionEspaciocultural.setIdprofesor(null);
                espacioculturalCollectionEspaciocultural = em.merge(espacioculturalCollectionEspaciocultural);
            }
            em.remove(profesor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profesor> findProfesorEntities() {
        return findProfesorEntities(true, -1, -1);
    }

    public List<Profesor> findProfesorEntities(int maxResults, int firstResult) {
        return findProfesorEntities(false, maxResults, firstResult);
    }

    private List<Profesor> findProfesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Profesor findProfesor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesor> rt = cq.from(Profesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        
    public void guardar(Profesor profesor){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(profesor);
	em.getTransaction().commit();
        em.close();
    }
   
    public void modificar(Profesor profesor){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(profesor);
	em.getTransaction().commit();
        em.close();
    }
    
    public void eliminar(Profesor profesor){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(profesor));
	em.getTransaction().commit();
        em.close();
    }
    
    
      public List<Profesor> findProfesor(Profesor mat){
	EntityManager em = getEntityManager();
	String jpl = "SELECT m FROM Profesor m";
	boolean creada = false;
	if(mat != null){
	    if(mat.getIdprofesor() != 0){
		creada = true;
		jpl = jpl + " WHERE m.idprofesor = " + Integer.toString(mat.getIdprofesor());
	    }
            
            if(!"".equals(mat.getNombre())){
		if(creada){
		    jpl = jpl + " AND m.nombre LIKE '%" + mat.getNombre() + "%'";
		} else {
		    creada = true;
		    jpl = jpl + " WHERE m.nombre LIKE '%" + mat.getNombre() + "%'";
		}
            }
            if(!"".equals(mat.getCorreo())){
		if(creada){
		    jpl = jpl + " AND m.correo LIKE '%" + mat.getCorreo() + "%'";
		} else {
		    creada = true;
		    jpl = jpl + " WHERE m.correo LIKE '%" + mat.getCorreo() + "%'";
		}
            }
            
            if(!"".equals(mat.getDepartamento())){
		if(creada){
		    jpl = jpl + " AND m.departamento LIKE '%" + mat.getDepartamento() + "%'";
		} else {
		    creada = true;
		    jpl = jpl + " WHERE m.departamento LIKE '%" + mat.getDepartamento() + "%'";
		}
            }
            
            if(!"".equals(mat.getTipoprof())){
		if(creada){
		    jpl = jpl + " AND m.tipoprof LIKE '%" + mat.getTipoprof() + "%'";
		} else {
		    creada = true;
		    jpl = jpl + " WHERE m.tipoprof LIKE '%" + mat.getTipoprof() + "%'";
		}
            }

                
            
	}
	Query query = em.createQuery(jpl);
	return query.getResultList();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
