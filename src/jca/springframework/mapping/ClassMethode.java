package jca.springframework.mapping;

import java.lang.reflect.Method;

public class ClassMethode {
    private String classControllerName;
    private String methodeControllerName;
    private MappingParameter mappingParameter;

    public ClassMethode(String classControllerName, Method methodeController) {
        this.classControllerName = classControllerName;
        this.methodeControllerName = methodeController.getName();
        this.mappingParameter = new MappingParameter(methodeController);
    }
    // Getteurs AND Setteurs
    public String getClassControllerName() {
        return classControllerName;
    }
    public void setClassControllerName(String classControllerName) {
        this.classControllerName = classControllerName;
    }
    public String getMethodeControllerName() {
        return methodeControllerName;
    }
    public void setMethodeControllerName(String methodeControllerName) {
        this.methodeControllerName = methodeControllerName;
    }
    public MappingParameter getMappingParameter() {
        return mappingParameter;
    }
    public void setMappingParameter(MappingParameter mappingParameter) {
        this.mappingParameter = mappingParameter;
    } 
}
