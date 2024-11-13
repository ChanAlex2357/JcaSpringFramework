package jca.springframework.scanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.exception.FrameworkException;
import jca.springframework.parameterbuilder.ObjectParameterBuilder;
import jca.springframework.parameterbuilder.PartParameterBuilder;
import jca.springframework.parameterbuilder.PrimitiveParameterBuilder;
import jca.springframework.session.WebSessionParser;
import jca.springframework.utils.PartUtils;

public class RequestScanner {
    private PartParameterBuilder partBuilder;
    private ObjectParameterBuilder objectBuilder;
    private PrimitiveParameterBuilder primitiveBuilder;

    public RequestScanner (){
        setPartBuilder(new PartParameterBuilder());
        setObjectBuilder(new ObjectParameterBuilder());
        setPrimitiveBuilder(new PrimitiveParameterBuilder());
    }
    
    public Object getParameterValue(Parameter parameter,HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException{
        Object value = null;
        if (PrimitiveScanner.isPrimitifType(parameter)) {
            value = getPrimitiveBuilder().getPrmitiveParameterValue(parameter, request);
        }
        else if (SessionScanner.isSessionParameter(parameter)) {
            // Cree une webSession a partir de httpServlet
            value = WebSessionParser.HttpSessionToWebSession(request);
        }
        else if (PartUtils.isPartParameter(parameter)) {
            value = getPartBuilder().getPartParameterValue(parameter,request);
        }
        else {
            value = getObjectBuilder(). getObjectParameterValue(parameter, request);
        }
        return value;
    }

    public void setPartBuilder(PartParameterBuilder partBuilder) {
        this.partBuilder = partBuilder;
    }
    public ObjectParameterBuilder getObjectBuilder() {
        return objectBuilder;
    }
    public void setObjectBuilder(ObjectParameterBuilder objectBuilder) {
        this.objectBuilder = objectBuilder;
    }
    public PrimitiveParameterBuilder getPrimitiveBuilder() {
        return primitiveBuilder;
    }
    public void setPrimitiveBuilder(PrimitiveParameterBuilder primitiveBuilder) {
        this.primitiveBuilder = primitiveBuilder;
    }
    public PartParameterBuilder getPartBuilder() {
        return partBuilder;
    }
}
