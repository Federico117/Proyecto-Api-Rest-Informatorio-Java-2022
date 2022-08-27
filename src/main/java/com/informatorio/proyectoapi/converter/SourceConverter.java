package com.informatorio.proyectoapi.converter;

import com.informatorio.proyectoapi.domain.Source;
import com.informatorio.proyectoapi.dto.SourceDto;
import org.springframework.stereotype.Component;

@Component
public class SourceConverter {

    public SourceDto toDto(Source source){
        SourceDto sourceDto = new SourceDto(source.getId(), source.getName(), source.getCode(), source.getCreatedAt());
        return sourceDto;
    }
}
