package smartcast.uz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import smartcast.uz.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    Optional<T> findByIdAndDeletedFalse(ID id);

    T trash(ID id);


    List<T> findAllNotDeleted();

    List<T> findAllNotDeleted(Pageable pageable);

    T saveAndRefresh(T entity);
}
