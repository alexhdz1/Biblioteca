/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.controlador;

import com.ingenieria.biblioteca.controlador.exceptions.NonexistentEntityException;
import com.ingenieria.biblioteca.modelo.Edificio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ingenieria.biblioteca.modelo.Salacultural;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alexis
 */
public class EdificioJpaController implements Serializable {

    public EdificioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Edificio edificio) {
        if (edificio.getSalaculturalCollection() == null) {
            edificio.setSalaculturalCollection(new ArrayList<Salacultural>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Salacultural> attachedSalaculturalCollection = new ArrayList<Salacultural>();
            for (Salacultural salaculturalCollectionSalaculturalToAttach : edificio.getSalaculturalCollection()) {
                salaculturalCollectionSalaculturalToAttach = em.getReference(salaculturalCollectionSalaculturalToAttach.getClass(), salaculturalCollectionSalaculturalToAttach.getIdsala());
                attachedSalaculturalCollection.add(salaculturalCollectionSalaculturalToAttach);
            }
            edificio.setSalaculturalCollection(attachedSalaculturalCollection);
            em.persist(edificio);
            for (Salacultural salaculturalCollectionSalacultural : edificio.getSalaculturalCollection()) {
                Edificio oldIdedificioOfSalaculturalCollectionSalacultural = salaculturalCollectionSalacultural.getIdedificio();
                salaculturalCollectionSalacultural.setIdedificio(edificio);
                salaculturalCollectionSalacultural = em.merge(salaculturalCollectionSalacultural);
                if (oldIdedificioOfSalaculturalCollectionSalacultural != null) {
                    oldIdedificioOfSalaculturalCollectionSalacultural.getSalaculturalCollection().remove(salaculturalCollectionSalacultural);
                    oldIdedificioOfSalaculturalCollectionSalacultural = em.merge(oldIdedificioOfSalaculturalCollectionSalacultural);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Edificio edificio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificio persistentEdificio = em.find(Edificio.class, edificio.getIdedificio());
            Collection<Salacultural> salaculturalCollectionOld = persistentEdificio.getSalaculturalCollection();
            Collection<Salacultural> salaculturalCollectionNew = edificio.getSalaculturalCollection();
            Collection<Salacultural> attachedSalaculturalCollectionNew = new ArrayList<Salacultural>();
            for (Salacultural salaculturalCollectionNewSalaculturalToAttach : salaculturalCollectionNew) {
                salaculturalCollectionNewSalaculturalToAttach = em.getReference(salaculturalCollectionNewSalaculturalToAttach.getClass(), salaculturalCollectionNewSalaculturalToAttach.getIdsala());
                attachedSalaculturalCollectionNew.add(salaculturalCollectionNewSalaculturalToAttach);
            }
            salaculturalCollectionNew = attachedSalaculturalCollectionNew;
            edificio.setSalaculturalCollection(salaculturalCollectionNew);
            edificio = em.merge(edificio);
            for (Salacultural salaculturalCollectionOldSalacultural : salaculturalCollectionOld) {
                if (!salaculturalCollectionNew.contains(salaculturalCollectionOldSalacultural)) {
                    salaculturalCollectionOldSalacultural.setIdedificio(null);
                    salaculturalCollectionOldSalacultural = em.merge(salaculturalCollectionOldSalacultural);
                }
            }
            for (Salacultural salaculturalCollectionNewSalacultural : salaculturalCollectionNew) {
                if (!salaculturalCollectionOld.contains(salaculturalCollectionNewSalacultural)) {
                    Edificio oldIdedificioOfSalaculturalCollectionNewSalacultural = salaculturalCollectionNewSalacultural.getIdedificio();
                    salaculturalCollectionNewSalacultural.setIdedificio(edificio);
                    salaculturalCollectionNewSalacultural = em.merge(salaculturalCollectionNewSalacultural);
                    if (oldIdedificioOfSalaculturalCollectionNewSalacultural != null && !oldIdedificioOfSalaculturalCollectionNewSalacultural.equals(edificio)) {
                        oldIdedificioOfSalaculturalCollectionNewSalacultural.getSalaculturalCollection().remove(salaculturalCollectionNewSalacultural);
                        oldIdedificioOfSalaculturalCollectionNewSalacultural = em.merge(oldIdedificioOfSalaculturalCollectionNewSalacultural);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = edificio.getIdedificio();
                if (findEdificio(id) == null) {
                    throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.");
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
            Edificio edificio;
            try {
                edificio = em.getReference(Edificio.class, id);
                edificio.getIdedificio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.", enfe);
            }
            Collection<Salacultural> salaculturalCollection = edificio.getSalaculturalCollection();
            for (Salacultural salaculturalCollectionSalacultural : salaculturalCollection) {
                salaculturalCollectionSalacultural.setIdedificio(null);
                salaculturalCollectionSalacultural = em.merge(salaculturalCollectionSalacultural);
            }
            em.remove(edificio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Edificio> findEdificioEntities() {
        return findEdificioEntities(true, -1, -1);
    }

    public List<Edificio> findEdificioEntities(int maxResults, int firstResult) {
        return findEdificioEntities(false, maxResults, firstResult);
    }

    private List<Edificio> findEdificioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Edificio.class));
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

    public Edificio findEdificio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Edificio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEdificioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Edificio> rt = cq.from(Edificio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
    public void guardar(Edificio e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(e);
	em.getTransaction().commit();
        em.close();
    }
   
    public void modificar(Edificio e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(e);
	em.getTransaction().commit();
        em.close();
    }
    
    public void eliminar(Edificio e){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(e));
	em.getTransaction().commit();
        em.close();
    }
    
    
}
