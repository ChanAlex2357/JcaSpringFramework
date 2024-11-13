package jca.springframework.builder;

import java.lang.reflect.Method;

import jca.springframework.controller.exception.DuplicateUrlException;
import jca.springframework.mapping.AdminUrlMapping;
import jca.springframework.mapping.Mapping;
import jca.springframework.mapping.VerbAction;
import jca.springframework.scanner.exception.MultipleVerbException;

public class MappingBuilder {
    private AdminUrlMapping adminUrlMapping;
    private VerbActionBuilder verbActionBuilder;
    public MappingBuilder (AdminUrlMapping adminUrlMapping){
        setAdminUrlMapping(adminUrlMapping);
        setVerbActionBuilder(new VerbActionBuilder());
    }
    
    public Mapping buildMapping(Class<?> controller , Method method) throws MultipleVerbException, DuplicateUrlException{
        // Creation du VerbAction
        VerbAction verbAction = verbActionBuilder.buildVerbAction(controller , method);
        if (verbAction.getUrl() == null ) {
            return null;
        }
        // Ajouter le VerbAction au mapping correspondant
        return getAdminUrlMapping().addMappingVerb(verbAction);
    }
    public AdminUrlMapping getAdminUrlMapping() {
        return adminUrlMapping;
    }
    public void setAdminUrlMapping(AdminUrlMapping adminUrlMapping) {
        if (adminUrlMapping == null) {
            adminUrlMapping = new AdminUrlMapping();
        }
        this.adminUrlMapping = adminUrlMapping;
    }
    public VerbActionBuilder getVerbActionBuilder() {
        return verbActionBuilder;
    }
    public void setVerbActionBuilder(VerbActionBuilder verbActionBuilder) {
        this.verbActionBuilder = verbActionBuilder;
    }
}
