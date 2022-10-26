package com.tykee.restapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "periods")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Period extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period_id", unique = true)
    @NotNull
    @JsonProperty("period_id")
    private Long periodId;

    @Column(unique = true)
    @Size(max = 5)
    @NotNull
    private String name;

    @NotNull
    private int minute;

    @NotNull
    private int hour;

    @NotNull
    private int day;

    @NotNull
    private int month;

    @NotNull
    @Size(max = 10)
    private String interval;

}
