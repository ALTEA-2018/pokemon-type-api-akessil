package com.miage.altea.tp.pokemon_type_api.service;

import com.miage.altea.tp.pokemon_type_api.bo.PokemonType;
import com.miage.altea.tp.pokemon_type_api.repository.PokemonTypeRepository;
import com.miage.altea.tp.pokemon_type_api.repository.TranslationRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class PokemonTypeServiceImpl implements PokemonTypeService {
    PokemonTypeRepository pokemonTypeRepository;

    TranslationRepository translationRepository;

    public PokemonTypeServiceImpl(PokemonTypeRepository pokemonTypeRepository, TranslationRepository translationRepository){
        this.pokemonTypeRepository = pokemonTypeRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public PokemonType getPokemonType(int id) {
        PokemonType pokemon = pokemonTypeRepository.findPokemonTypeById(id);
        return translate(pokemon);
    }

    @Override
    public List<PokemonType> getAllPokemonTypes(){
        return pokemonTypeRepository.findAllPokemonType().stream().map(p -> translate(p)).collect(Collectors.toList());
    }

    @Override
    public PokemonType getPokemonTypeByName(String name){
        PokemonType pokemon = pokemonTypeRepository.findPokemonTypeByName(name);
        return translate(pokemon);
    }

    @Override
    public List<PokemonType> getPokemonTypeBTypes(List<String> types) {
        return pokemonTypeRepository.findAllPokemonByTypes(types).stream().map(p -> translate(p)).collect(Collectors.toList());
    }

    public void setTranslationRepository(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    private PokemonType translate(PokemonType  pokemon){
        if(translationRepository == null) {
            return pokemon;
        }

        Locale locale = LocaleContextHolder.getLocale();
        final String nameTranslated = translationRepository.getPokemonName(pokemon.getId(), locale);
        pokemon.setName(nameTranslated);
        return pokemon;
    }
}