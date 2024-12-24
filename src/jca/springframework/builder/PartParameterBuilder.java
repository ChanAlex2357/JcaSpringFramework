package jca.springframework.builder;

import java.io.IOException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.FileMapping;

public class PartParameterBuilder extends ParameterBuilder{
    public Object getPartParameterValue(Parameter parameter , HttpServletRequest request) throws IOException, ServletException, FrameworkException {
        String parameterName = buildParameterName(parameter);
        Part part = request.getPart(parameterName);
        FileMapping fileMapping = new FileMapping(part);
        return fileMapping;
    }
}