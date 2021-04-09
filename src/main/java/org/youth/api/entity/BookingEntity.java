package org.youth.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tb_booking")
public class BookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
	private String roomName;
	private String userName;
	
	@Builder
	public BookingEntity(long roomId, String roomName, String userName) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.userName = userName;
	}
}
