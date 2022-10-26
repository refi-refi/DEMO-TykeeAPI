package com.tykee.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
    name = "candles",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"symbol_id", "start_ts_utc", "end_ts_utc"}
    )
)
@NoArgsConstructor
@AllArgsConstructor
public class Candle extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Getter
    @Setter
    @Column(name = "start_ts_utc")
    private long startTs;

    @NotNull
    @Getter
    @Setter
    @Column(name = "end_ts_utc")
    private long endTs;

    @NotNull
    @Getter
    @Setter
    private int open;

    @NotNull
    @Getter
    @Setter
    private int high;

    @NotNull
    @Getter
    @Setter
    private int low;

    @NotNull
    @Getter
    @Setter
    private int close;

    @NotNull
    @Getter
    @Setter
    private int spread;

    @NotNull
    @Getter
    @Setter
    private int volume;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "symbol_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Getter
    @Setter
    private Symbol symbol;

}
