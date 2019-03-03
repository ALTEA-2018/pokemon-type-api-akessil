package com.miage.altea.tp.pokemon_type_api.controller;

import com.miage.altea.tp.pokemon_type_api.bo.PokemonType;
import com.miage.altea.tp.pokemon_type_api.service.PokemonTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pokemon-types")
@RestController
public class PokemonTypeController {

    PokemonTypeService service;

    public PokemonTypeController(PokemonTypeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    PokemonType getPokemonTypeFromId(@PathVariable int id){
        return service.getPokemonType(id);
    }

    @GetMapping(value = "/", params = {"name"})
    PokemonType getPokemonTypeFromName(@RequestParam("name") String name){
        return service.getPokemonTypeByName(name);
    }

    @GetMapping(value = "/", params = {"types"})
    List<PokemonType> getPokemonTypeFromName(@RequestParam("types") List<String> types){
        return service.getPokemonTypeBTypes(types);
    }

    @GetMapping("/")
    public List<PokemonType> getAllPokemonTypes() {
        return service.getAllPokemonTypes();
    }
}
