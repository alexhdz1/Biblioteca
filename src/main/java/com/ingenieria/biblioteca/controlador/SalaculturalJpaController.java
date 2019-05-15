/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.controlador;

import com.ingenieria.biblioteca.controlador.exceptions.IllegalOrphanException;
import com.ingenieria.biblioteca.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ingenieria.biblioteca.modelo.Edificio;
import com.ingenieria.biblioteca.modelo.Espaciocultural;
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
public class SalaculturalJpaController implements Serializable {

    public SalaculturalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salacultural salacultural) {
        if (salacultural.getEspacioculturalCollection() == null) {
            salacultural.setEspacioculturalCollection(new ArrayList<Espaciocultural>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificio idedificio = salacultural.getIdedificio();
            if (idedificio != null) {
                idedificio = em.getReference(idedificio.getClass(), idedificio.getIdedificio());
                salacultural.setIdedificio(idedificio);
            }
            Collection<Espaciocultural> attachedEspacioculturalCollection = new ArrayList<Espaciocultural>();
            for (Espaciocultural espacioculturalCollectionEspacioculturalToAttach : salacultural.getEspacioculturalCollection()) {
                espacioculturalCollectionEspacioculturalToAttach = em.getReference(espacioculturalCollectionEspacioculturalToAttach.getClass(), espacioculturalCollectionEspacioculturalToAttach.getIdevento());
                attachedEspacioculturalCollection.add(espacioculturalCollectionEspacioculturalToAttach);
            }
            salacultural.setEspacioculturalCollection(attachedEspacioculturalCollection);
            em.persist(salacultural);
            if (idedificio != null) {
                idedificio.getSalaculturalCollection().add(salacultural);
                idedificio = em.merge(idedificio);
            }
            for (Espaciocultural espacioculturalCollectionEspaciocultural : salacultural.getEspacioculturalCollection()) {
                Salacultural oldIdsalaOfEspacioculturalCollectionEspaciocultural = espacioculturalCollectionEspaciocultural.getIdsala();
                espacioculturalCollectionEspaciocultural.setIdsala(salacultural);
                espacioculturalCollectionEspaciocultural = em.merge(espacioculturalCollectionEspaciocultural);
                if (oldIdsalaOfEspacioculturalCollectionEspaciocultural != null) {
                    oldIdsalaOfEspacioculturalCollectionEspaciocultural.getEspacioculturalCollection().remove(espacioculturalCollectionEspaciocultural);
                    oldIdsalaOfEspacioculturalCollectionEspaciocultural = em.merge(oldIdsalaOfEspacioculturalCollectionEspaciocultural);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salacultural salacultural) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salacultural persistentSalacultural = em.find(Salacultural.class, salacultural.getIdsala());
            Edificio idedificioOld = persistentSalacultural.getIdedificio();
            Edificio idedificioNew = salacultural.getIdedificio();
            Collection<Espaciocultural> espacioculturalCollectionOld = persistentSalacultural.getEspacioculturalCollection();
            Collection<Espaciocultural> espacioculturalCollectionNew = salacultural.getEspacioculturalCollection();
            List<String> illegalOrphanMessages = null;
            for (Espaciocultural espacioculturalCollectionOldEspaciocultural : espacioculturalCollectionOld) {
                if (!espacioculturalCollectionNew.contains(espacioculturalCollectionOldEspaciocultural)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Espaciocultural " + espacioculturalCollectionOldEspaciocultural + " since its idsala field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idedificioNew != null) {
                idedificioNew = em.getReference(idedificioNew.getClass(), idedificioNew.getIdedificio());
                salacultural.setIdedificio(idedificioNew);
            }
            Collection<Espaciocultural> attachedEspacioculturalCollectionNew = new ArrayList<Espaciocultural>();
            for (Espaciocultural espacioculturalCollectionNewEspacioculturalToAttach : espacioculturalCollectionNew) {
                espacioculturalCollectionNewEspacioculturalToAttach = em.getReference(espacioculturalCollectionNewEspacioculturalToAttach.getClass(), espacioculturalCollectionNewEspacioculturalToAttach.getIdevento());
                attachedEspacioculturalCollectionNew.add(espacioculturalCollectionNewEspacioculturalToAttach);
            }
            espacioculturalCollectionNew = attachedEspacioculturalCollectionNew;
            salacultural.setEspacioculturalCollection(espacioculturalCollectionNew);
            salacultural = em.merge(salacultural);
            if (idedificioOld != null && !idedificioOld.equals(idedificioNew)) {
                idedificioOld.getSalaculturalCollection().remove(salacultural);
                idedificioOld = em.merge(idedificioOld);
            }
            if (idedificioNew != null && !idedificioNew.equals(idedificioOld)) {
                idedificioNew.getSalaculturalCollection().add(salacultural);
                idedificioNew = em.merge(idedificioNew);
            }
            for (Espaciocultural espacioculturalCollectionNewEspaciocultural : espacioculturalCollectionNew) {
                if (!espacioculturalCollectionOld.contains(espacioculturalCollectionNewEspaciocultural)) {
                    Salacultural oldIdsalaOfEspacioculturalCollectionNewEspaciocultural = espacioculturalCollectionNewEspaciocultural.getIdsala();
                    espacioculturalCollectionNewEspaciocultural.setIdsala(salacultural);
                    espacioculturalCollectionNewEspaciocultural = em.merge(espacioculturalCollectionNewEspaciocultural);
                    if (oldIdsalaOfEspacioculturalCollectionNewEspaciocultural != null && !oldIdsalaOfEspacioculturalCollectionNewEspaciocultural.equals(salacultural)) {
                        oldIdsalaOfEspacioculturalCollectionNewEspaciocultural.getEspacioculturalCollection().remove(espacioculturalCollectionNewEspaciocultural);
                        oldIdsalaOfEspacioculturalCollectionNewEspaciocultural = em.merge(oldIdsalaOfEspacioculturalCollectionNewEspaciocultural);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = salacultural.getIdsala();
                if (findSalacultural(id) == null) {
                    throw new NonexistentEntityException("The salacultural with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salacultural salacultural;
            try {
                salacultural = em.getReference(Salacultural.class, id);
                salacultural.getIdsala();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salacultural with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Espaciocultural> espacioculturalCollectionOrphanCheck = salacultural.getEspacioculturalCollection();
            for (Espaciocultural espacioculturalCollectionOrphanCheckEspaciocultural : espacioculturalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salacultural (" + salacultural + ") cannot be destroyed since the Espaciocultural " + espacioculturalCollectionOrphanCheckEspaciocultural + " in its espacioculturalCollection field has a non-nullable idsala field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Edificio idedificio = salacultural.getIdedificio();
            if (idedificio != null) {
                idedificio.getSalaculturalCollection().remove(salacultural);
                idedificio = em.merge(idedificio);
            }
            em.remove(salacultural);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salacultural> findSalaculturalEntities() {
        return findSalaculturalEntities(true, -1, -1);
    }

    public List<Salacultural> findSalaculturalEntities(int maxResults, int firstResult) {
        return findSalaculturalEntities(false, maxResults, firstResult);
    }

    private List<Salacultural> findSalaculturalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salacultural.class));
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

    public Salacultural findSalacultural(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salacultural.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalaculturalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salacultural> rt = cq.from(Salacultural.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
        public void guardar(Salacultural s){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(s);
	em.getTransaction().commit();
        em.close();
    }
   
    public void modificar(Salacultural s){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(s);
	em.getTransaction().commit();
        em.close();
    }
    
    public void eliminar(Salacultural s){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(s));
	em.getTransaction().commit();
        em.close();
    }
    
}
