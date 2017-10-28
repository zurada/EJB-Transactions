package edu.pjwstk.sri.lab2.dao;

import edu.pjwstk.sri.lab2.model.Product;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * DAO for Product
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductDao implements Serializable {
    @PersistenceContext(unitName = "sri2-persistence-unit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Product entity) {
        em.persist(entity);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void deleteById(Long id) {
        Product entity = em.find(Product.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Product update(Product entity) {
        return em.merge(entity);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Product> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Product> findAllQuery = em
                .createQuery(
                        "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category ORDER BY p.id",
                        Product.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
