/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.controlador;

import com.ingenieria.biblioteca.controlador.exceptions.NonexistentEntityException;
import com.ingenieria.biblioteca.modelo.Espaciocultural;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ingenieria.biblioteca.modelo.Profesor;
import com.ingenieria.biblioteca.modelo.Salacultural;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexis
 */
public class EspacioculturalJpaController implements Serializable {

    public EspacioculturalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Espaciocultural espaciocultural) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor idprofesor = espaciocultural.getIdprofesor();
            if (idprofesor != null) {
                idprofesor = em.getReference(idprofesor.getClass(), idprofesor.getIdprofesor());
                espaciocultural.setIdprofesor(idprofesor);
            }
            Salacultural idsala = espaciocultural.getIdsala();
            if (idsala != null) {
                idsala = em.getReference(idsala.getClass(), idsala.getIdsala());
                espaciocultural.setIdsala(idsala);
            }
            em.persist(espaciocultural);
            if (idprofesor != null) {
                idprofesor.getEspacioculturalCollection().add(espaciocultural);
                idprofesor = em.merge(idprofesor);
            }
            if (idsala != null) {
                idsala.getEspacioculturalCollection().add(espaciocultural);
                idsala = em.merge(idsala);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Espaciocultural espaciocultural) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Espaciocultural persistentEspaciocultural = em.find(Espaciocultural.class, espaciocultural.getIdevento());
            Profesor idprofesorOld = persistentEspaciocultural.getIdprofesor();
            Profesor idprofesorNew = espaciocultural.getIdprofesor();
            Salacultural idsalaOld = persistentEspaciocultural.getIdsala();
            Salacultural idsalaNew = espaciocultural.getIdsala();
            if (idprofesorNew != null) {
                idprofesorNew = em.getReference(idprofesorNew.getClass(), idprofesorNew.getIdprofesor());
                espaciocultural.setIdprofesor(idprofesorNew);
            }
            if (idsalaNew != null) {
                idsalaNew = em.getReference(idsalaNew.getClass(), idsalaNew.getIdsala());
                espaciocultural.setIdsala(idsalaNew);
            }
            espaciocultural = em.merge(espaciocultural);
            if (idprofesorOld != null && !idprofesorOld.equals(idprofesorNew)) {
                idprofesorOld.getEspacioculturalCollection().remove(espaciocultural);
                idprofesorOld = em.merge(idprofesorOld);
            }
            if (idprofesorNew != null && !idprofesorNew.equals(idprofesorOld)) {
                idprofesorNew.getEspacioculturalCollection().add(espaciocultural);
                idprofesorNew = em.merge(idprofesorNew);
            }
            if (idsalaOld != null && !idsalaOld.equals(idsalaNew)) {
                idsalaOld.getEspacioculturalCollection().remove(espaciocultural);
                idsalaOld = em.merge(idsalaOld);
            }
            if (idsalaNew != null && !idsalaNew.equals(idsalaOld)) {
                idsalaNew.getEspacioculturalCollection().add(espaciocultural);
                idsalaNew = em.merge(idsalaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = espaciocultural.getIdevento();
                if (findEspaciocultural(id) == null) {
                    throw new NonexistentEntityException("The espaciocultural with id " + id + " no longer exists.");
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
            Espaciocultural espaciocultural;
            try {
                espaciocultural = em.getReference(Espaciocultural.class, id);
                espaciocultural.getIdevento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The espaciocultural with id " + id + " no longer exists.", enfe);
            }
            Profesor idprofesor = espaciocultural.getIdprofesor();
            if (idprofesor != null) {
                idprofesor.getEspacioculturalCollection().remove(espaciocultural);
                idprofesor = em.merge(idprofesor);
            }
            Salacultural idsala = espaciocultural.getIdsala();
            if (idsala != null) {
                idsala.getEspacioculturalCollection().remove(espaciocultural);
                idsala = em.merge(idsala);
            }
            em.remove(espaciocultural);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Espaciocultural> findEspacioculturalEntities() {
        return findEspacioculturalEntities(true, -1, -1);
    }

    public List<Espaciocultural> findEspacioculturalEntities(int maxResults, int firstResult) {
        return findEspacioculturalEntities(false, maxResults, firstResult);
    }

    private List<Espaciocultural> findEspacioculturalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Espaciocultural.class));
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

    public Espaciocultural findEspaciocultural(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Espaciocultural.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspacioculturalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Espaciocultural> rt = cq.from(Espaciocultural.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
       public void guardar(Espaciocultural e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(e);
	em.getTransaction().commit();
        em.close();
    }
   
    public void modificar(Espaciocultural e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(e);
	em.getTransaction().commit();
        em.close();
    }
    
    public void eliminar(Espaciocultural e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(e));
	em.getTransaction().commit();
        em.close();
    }
    
    
    
    public List<Espaciocultural> findEspaciocultural(Espaciocultural mat){
	EntityManager em = getEntityManager();
	String jpl = "SELECT m FROM Espaciocultural m";
	boolean creada = false;
	if(mat != null){
	    if(mat.getIdevento()!= 0){
		creada = true;
		jpl = jpl + " WHERE m.idevento = " + Integer.toString(mat.getIdevento());
	    }
            
            if(!"".equals(mat.getNombreevento())){
		if(creada){
		    jpl = jpl + " AND m.nombreevento LIKE '%" + mat.getNombreevento()+ "%'";
		} else {
		    creada = true;
		    jpl = jpl + " WHERE m.nombreevento LIKE '%" + mat.getNombreevento() + "%'";
		}
            }
       


            
	}
	Query query = em.createQuery(jpl);
	return query.getResultList();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
