package jca.springframework.mapping;

public class VerbClassMethode {

    public VerbClassMethode(String verb , )
    public VerbClassMethode(String verb, ClassMethode classMethode) {
        this.verb = verb;
        this.classMethode = classMethode;
    }
    String verb;
    ClassMethode classMethode;
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }
    public ClassMethode getClassMethode() {
        return classMethode;
    }
    public void setClassMethode(ClassMethode classMethode) {
        this.classMethode = classMethode;
    }

    
    
}
