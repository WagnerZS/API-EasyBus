package br.edu.atitus.api_easybus.entities;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_favorite")
public class FavoriteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "id_point", nullable = false)
	private PointEntity point;

	// Getters e Setters
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public PointEntity getPoint() {
		return point;
	}

	public void setPoint(PointEntity point) {
		this.point = point;
	}
}
