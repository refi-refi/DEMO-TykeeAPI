package com.tykee.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "symbols")
@NoArgsConstructor
@AllArgsConstructor
public class Symbol extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Size(max = 6)
    @Column(unique = true)
    @Getter
    @Setter
    private String name;

    @NotNull
    @Getter
    @Setter
    private int digits;
}
