package smartcast.uz.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import smartcast.uz.domain.BaseEntity;
import smartcast.uz.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;
    private final Specification<T> isNotDeletedSpecification = (root, query, cb) -> cb.equal(root.get("deleted"), false);

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findByIdAndDeletedFalse(ID id) {
        T entity = findById(id).orElse(null);
        return (entity != null && !entity.getDeleted()) ? Optional.of(entity): Optional.empty();
    }

    @Override
    @Transactional
    public T trash(ID id) {
        T entity = findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(true);
            save(entity);
        }
        return entity;
    }


    @Override
    public List<T> findAllNotDeleted() {
        return findAll(isNotDeletedSpecification);
    }

    @Override
    public List<T> findAllNotDeleted(Pageable pageable) {
        return findAll(isNotDeletedSpecification, pageable).getContent();
    }

    @Override
    @Transactional
    public T saveAndRefresh(T entity) {
        T savedEntity = save(entity);
        entityManager.refresh(savedEntity);
        return savedEntity;
    }
}
