package com.sensedia.payment.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "VARCHAR(36)", nullable = false)
  private UUID id;

  @Column
  private String document;

  @Column
  private String password;
  
  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private Integer expirationDay;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
  private List<DebitEntity> debits;

}
