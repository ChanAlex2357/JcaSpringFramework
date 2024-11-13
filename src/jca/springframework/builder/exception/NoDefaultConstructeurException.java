package jca.springframework.builder.exception;

import jca.springframework.exception.FrameworkException;

public class NoDefaultConstructeurException extends  FrameworkException {
    Class<?> sourceType;
    public Class<?> getSourceType() {
        return sourceType;
    }
    public void setSourceType(Class<?> sourceType ) {
        this.sourceType = sourceType;

    }
    public NoDefaultConstructeurException( Class<?> source ,Exception err){
        super(null,err);
        setSourceType(source);
    }
    @Override
    public String getMessage(){
        return "La class <b>"+this.getSourceType()+"</b> doit posseder un con";
    }
}
