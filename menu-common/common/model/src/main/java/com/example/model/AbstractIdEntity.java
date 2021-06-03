package com.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@MappedSuperclass
@NoArgsConstructor
@Access(value = AccessType.FIELD)
public abstract class AbstractIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    protected Boolean isNull() {
        return id == null;
    }
}
