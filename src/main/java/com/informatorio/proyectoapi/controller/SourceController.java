package com.informatorio.proyectoapi.controller;

import com.informatorio.proyectoapi.domain.Source;
import com.informatorio.proyectoapi.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SourceController {

    private SourceRepository sourceRepository;

    @Autowired
    public SourceController(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @PostMapping(value = "/source")
    public Source createSource(@RequestBody Source source){
        return sourceRepository.save(source);
    }

    /*@GetMapping(value = "/source")
    public List<Source> getSources(){
        return sourceRepository.findAll();
    }*/

    @GetMapping(value = "/source")
    public List<Source> getSourcesfafgdfasd(@RequestParam(required = false) String palabra){
        if(palabra != null){
            return sourceRepository.findAll().stream().filter(x -> x.getName().toLowerCase().contains(palabra.toLowerCase())).collect(Collectors.toList());
        }else{
            return sourceRepository.findAll();
        }
    }

    @GetMapping(value = "/source/{id_source}")
    public Source getSourcesById(@PathVariable Long id_source){
        return sourceRepository.findById(id_source).orElse(null);
    }

    @DeleteMapping(value = "/source/{id_source}")
    public void deleteSourceById(@PathVariable Long id_source){
        sourceRepository.deleteById(id_source);
    }
}
