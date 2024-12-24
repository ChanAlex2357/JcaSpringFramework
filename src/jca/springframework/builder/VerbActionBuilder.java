package jca.springframework.builder;

import java.lang.reflect.Method;

import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.mapping.VerbAction;
import jca.springframework.scanner.exception.MultipleVerbException;

public class VerbActionBuilder {
    public VerbAction buildVerbAction(Class<?> controller , Method method) throws MultipleVerbException{
        MappingAnnotation mappingAnnotation = new MappingAnnotation(method);
        VerbAction mapping;
        ///  Creation de l'objet mapping controller -> method 
        mapping = new VerbAction(
            mappingAnnotation,   
            controller.getName(),       // Le nom du controller
            method                      // La methode a appeler
        );
        return mapping;
    } 
}
