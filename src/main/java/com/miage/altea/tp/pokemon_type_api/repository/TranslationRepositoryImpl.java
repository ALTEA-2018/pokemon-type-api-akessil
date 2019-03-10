package com.miage.altea.tp.pokemon_type_api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.altea.tp.pokemon_type_api.bo.Translation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class TranslationRepositoryImpl implements TranslationRepository {

    private Map<Locale, List<Translation>> translations;

    private List<Translation> defaultTranslations;

    public TranslationRepositoryImpl() {
        try {
            var objectMapper = new ObjectMapper();

            var frenchTranslationStream = new ClassPathResource("translations-fr.json").getInputStream();
            var frenchTranslationsArray = objectMapper.readValue(frenchTranslationStream, Translation[].class);

            var englishTranslationStream = new ClassPathResource("translations-en.json").getInputStream();
            var englishTranslationsArray = objectMapper.readValue(englishTranslationStream, Translation[].class);

            this.translations = Map.of(
                    Locale.FRENCH, List.of(frenchTranslationsArray),
                    Locale.ENGLISH, List.of(englishTranslationsArray)
            );

            this.defaultTranslations = List.of(englishTranslationsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPokemonName(int id, Locale locale) {
        if(locale == null) {
            return getDefaultTranslation(id);
        }

        List<Translation> localeTranslations = translations.get(locale);
        if(localeTranslations == null) {
            return getDefaultTranslation(id);
        }

        for(Translation t : localeTranslations) {
            if(id == t.getId()) {
                return t.getName();
            }
        }
        return "XXX";

    }

    private String getDefaultTranslation(int id) {
        for (Translation t : defaultTranslations) {
            if (id == t.getId()) {
                return t.getName();
            }
        }
        return "XXX";
    }
}

