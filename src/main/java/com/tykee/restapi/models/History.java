package com.tykee.restapi.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
    name = "history",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"symbol_id", "start_ts_utc", "end_ts_utc"}
    )
)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_ts_utc")
    @NotNull
    @JsonProperty("start_ts_utc")
    private Long startTsUtc;

    @Column(name = "end_ts_utc")
    @NotNull
    @JsonProperty("end_ts_utc")
    private Long endTsUtc;

    @NotNull
    private int open;

    @NotNull
    private int high;

    @NotNull
    private int low;

    @NotNull
    private int close;

    @NotNull
    private int spread;

    @NotNull
    private int volume;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "symbol_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Symbol symbol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "period_id", nullable = false)
    @JsonIgnore
    private Period period;
}
