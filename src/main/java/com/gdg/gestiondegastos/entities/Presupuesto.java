package com.gdg.gestiondegastos.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "presupuesto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double cantidadInicio;
    private Double cantidadFinal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinal;

    // Uniones de tablas

    // -----------COMPROBAR 2----------
    // (Recibe id de grupo)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
}