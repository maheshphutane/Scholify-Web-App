package com.hello_world_sprinboot.scholify.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity{
    @Id
    private  String day;
    private String reason;
    @Enumerated(EnumType.STRING)
    private Type type;
    public enum Type{
        FEDERAL,FESTIVAL
    }
}
