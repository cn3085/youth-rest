package org.youth.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.youth.api.code.DataState;
import org.youth.api.entity.BaseDataEntity;

@NoRepositoryBean
public interface SoftDeleteRepositoryAdapter<T extends BaseDataEntity, ID extends Serializable> extends JpaRepository<T, ID>{

	default void delete(T entity) {
		entity.setDataState(DataState.D);
	}
}
