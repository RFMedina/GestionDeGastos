package com.gdg.gestiondegastos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TablaBSDto {
    
    private long total;
    private List rows;
}
