package jca.springframework.mapping;

public class ClassMethode {
    private String classControllerName;
    private String methodeControllerName;
    private MappingParameter mappingParameter;
    
    
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
