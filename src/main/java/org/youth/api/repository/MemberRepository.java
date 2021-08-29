package org.youth.api.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.youth.api.entity.MemberEntity;

@Repository
public interface MemberRepository extends SoftDeleteRepositoryAdapter<MemberEntity, Long>, MemberCustomRepository {

	Optional<MemberEntity> findByMyPhoneNumber(String phoneNumber);

}
