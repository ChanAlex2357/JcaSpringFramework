package jca.springframework.builder.viewbuilder;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.mapping.VerbAction;
import jca.springframework.scanner.ValidationScanner;
import jca.springframework.view.View;

public abstract class ViewBuilder {
    
    public abstract View buildView(Object obj , VerbAction vba, ValidationScanner validationScanner) throws FrameworkException;
    public static ViewBuilder getBuilder(VerbAction vba){
        MappingAnnotation mappingAnnotation = vba.getMappingAnnotation();
        ViewBuilder resultView = null;
        if (mappingAnnotation.isApiMethode()) {
            resultView = new JsonViewBuilder();
        }
        else {
            resultView = new WebViewBuilder();
        }
        return resultView;
    }
}
