package com.technikon.final_project_ed.repository.impl;

import com.technikon.final_project_ed.repository.CRUDRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;

/**
 * CRUDRepositoryImpl class implements all the methods declared in the
 * CRUDRepository interface
 *
 * @author Kostas Vamvakousis
 * @param <T>
 */
@Slf4j
public abstract class CRUDRepositoryImpl<T> implements CRUDRepository<T>, Serializable {

    @PersistenceContext(unitName = "TechnikonPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public Optional<T> save(T t) {
        try {
            userTransaction.begin();
            em.persist(t);
            userTransaction.commit();
            return Optional.of(t);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return Optional.empty();
    }

    /**
     *
     * @param id
     * @return
     */
    //@Transactional
    @Override
    public Optional<T> findById(long id) {
        T t = em.find(getClassType(), id);
        //   T t = (T)em.createQuery("select p from " + getClassName() +" p where id = "+id).getSingleResult();

        return t != null ? Optional.of(t) : Optional.empty();
    }

    public abstract Class<T> getClassType();

    public abstract String getClassName();

    @Override
    public List<T> findAll() {
        return em.createQuery("from " + getClassName()).getResultList();
    }

//??
    @Override
    public Optional<T> update(long id, T t) {
        try {
            userTransaction.begin();
            T t0 = em.find(getClassType(), id);
            if (t0 == null) {
                userTransaction.commit();
                return Optional.empty();
            }
            copyValues(t0, t);
//            em.persist(t0);
            em.persist(em.contains(t0) ? t0 : em.merge(t0));
            userTransaction.commit();
            return Optional.of(t0);
        } catch (Exception e) {
            log.info(e.toString());
            return Optional.empty();
        }
    }

    public abstract void copyValues(T tSource, T tTarget);

    /**
     * Deleting a persistent instance
     *
     * @param id
     * @return success
     */
    @Override
    public boolean delete(long id) {
        T persistentInstance = em.find(getClassType(), id);
        if (persistentInstance != null) {
            try {
                userTransaction.begin();
                em.remove(em.contains(persistentInstance) ? persistentInstance : em.merge(persistentInstance));
                userTransaction.commit();
            } catch (Exception e) {
                log.error(e.toString());
                return false;
            }
            return true;
        }
        return false;
    }
}
