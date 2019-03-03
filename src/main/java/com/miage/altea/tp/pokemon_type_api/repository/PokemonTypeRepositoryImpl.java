package com.miage.altea.tp.pokemon_type_api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.altea.tp.pokemon_type_api.bo.PokemonType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class PokemonTypeRepositoryImpl implements PokemonTypeRepository {

    private List<PokemonType> pokemons = new ArrayList<>();

    class SortbyId implements Comparator<PokemonType>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(PokemonType p1, PokemonType p2)
        {
            return p1.getId() - p2.getId();
        }
    }

    public PokemonTypeRepositoryImpl() {
        try {
            var pokemonsStream = new ClassPathResource("pokemons.json").getInputStream();

            var objectMapper = new ObjectMapper();
            var pokemonsArray = objectMapper.readValue(pokemonsStream, PokemonType[].class);
            Arrays.sort(pokemonsArray, new SortbyId());
            this.pokemons = Arrays.asList(pokemonsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PokemonType findPokemonTypeById(int id) {
        System.out.println("Loading Pokemon information for Pokemon id " + id);

        for(PokemonType pokemonType: pokemons){
            if(id == pokemonType.getId()){
                return pokemonType;
            }
        }
        return null;
    }

    @Override
    public PokemonType findPokemonTypeByName(String name) {
        System.out.println("Loading Pokemon information for Pokemon name " + name);
        if(name == null){
            return null;
        }

        for(PokemonType pokemonType: pokemons){
            if(name.equals(pokemonType.getName())){
                return pokemonType;
            }
        }
        return null;
    }

    @Override
    public List<PokemonType> findAllPokemonType() {
        return pokemons;
    }

    @Override
    public List<PokemonType> findAllPokemonByTypes(List<String> types) {
        List<PokemonType> result = new ArrayList<>();

        if(types == null || types.isEmpty()){
            return null;
        }

        for(PokemonType pokemonType: pokemons){
            boolean contains=true;
            for(String type : types){
                if(pokemonType.getTypes()==null || ! pokemonType.getTypes().contains(type)){
                    contains = false;
                }
            }

            if(contains){
                result.add(pokemonType);
            }
        }

        return result;
    }


}
