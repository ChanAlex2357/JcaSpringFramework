package jca.springframework.controller;

public class Mapping {
    String classControllerName;
    String methodeControllerName;

    public Mapping(String classControllerName, String methodeControllerName) {
        setClassControllerName(classControllerName);
        setMethodeControllerName(methodeControllerName);
    }
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

    @Override
    public String toString() {
        return getClassControllerName() +" => "+getMethodeControllerName();
    }
}
