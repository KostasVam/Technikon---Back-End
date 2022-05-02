package com.technikon.final_project_ed.repository.impl;

import com.technikon.final_project_ed.repository.CRUDRepository;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
public abstract class CRUDRepositoryImpl<T> implements CRUDRepository<T> {

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
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            log.info(e.getMessage());
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
        Optional<T> tOpt = findById(id);
        if (tOpt.isEmpty()) {
            return Optional.empty();
        }
        T tObj = tOpt.get();
        copyValues(t, tObj);
        return save(tObj);
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
                em.remove(persistentInstance);
                userTransaction.commit();
            } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                log.info(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }
}
