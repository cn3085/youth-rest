package org.youth.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tb_booking")
@Where(clause = "data_state = 'A'")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BookingEntity extends BaseDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
	private String roomName;
	private String userName;
	
}
