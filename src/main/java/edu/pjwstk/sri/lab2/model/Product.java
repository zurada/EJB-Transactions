package edu.pjwstk.sri.lab2.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "PRODUCT")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	private Integer version = 0;

	@NotEmpty
	private String name;
	
	@NotNull
	private Integer stock;

	private Date created;

	private Date updated;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID", nullable = false)
	private Category category;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}
}