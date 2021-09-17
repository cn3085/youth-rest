package org.youth.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youth.api.entity.SettingEntity;

public interface SettingRepository extends JpaRepository<SettingEntity, Long>{

	Optional<SettingEntity> findFirstByOrderBySettingIdDesc();

}
