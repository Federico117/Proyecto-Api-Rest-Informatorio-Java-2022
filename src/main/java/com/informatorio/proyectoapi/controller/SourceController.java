package com.informatorio.proyectoapi.controller;

import com.informatorio.proyectoapi.converter.SourceConverter;
import com.informatorio.proyectoapi.domain.Source;
import com.informatorio.proyectoapi.dto.SourceDto;
import com.informatorio.proyectoapi.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SourceController {

    private SourceRepository sourceRepository;
    private SourceConverter sourceConverter;

    @Autowired
    public SourceController(SourceRepository sourceRepository, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }

    @PostMapping(value = "/source")
    public SourceDto createSource(@RequestBody Source source){
        return sourceConverter.toDto(sourceRepository.save(source));
    }

    /*@GetMapping(value = "/source")
    public List<Source> getSources(){
        return sourceRepository.findAll();
    }*/

    @GetMapping(value = "/source")
    public List<SourceDto> getAllSourcesByArg(@RequestParam(required = false) String palabra){
        if(palabra != null){
            return sourceRepository.findAll().stream().filter(x -> x.getName().toLowerCase().contains(palabra.toLowerCase())).map(x->sourceConverter.toDto(x)).collect(Collectors.toList());
        }else{
            return sourceRepository.findAll().stream().map(y->sourceConverter.toDto(y)).collect(Collectors.toList());
        }
    }

    @GetMapping(value = "/source/{id_source}")
    public SourceDto getSourcesById(@PathVariable Long id_source){
        return sourceConverter.toDto(sourceRepository.findById(id_source).orElse(null));
    }

    @DeleteMapping(value = "/source/{id_source}")
    public void deleteSourceById(@PathVariable Long id_source){
        sourceRepository.deleteById(id_source);
    }

    @PutMapping(value = "/source")
    public SourceDto modifiSource(@RequestBody Source source){
        Source sourceToModif = sourceRepository.findById(source.getId()).orElse(null);
        if(source.getName() != null){
            sourceToModif.setName(source.getName());
        }
        if(source.getCode() != null){
            sourceToModif.setCode(source.getCode());
        }
        if(source.getCreatedAt() != null){
            source.setCreatedAt(source.getCreatedAt());
        }
        return sourceConverter.toDto(sourceRepository.save(sourceToModif));
    }
}
