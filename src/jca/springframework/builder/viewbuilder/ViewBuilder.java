package jca.springframework.builder.viewbuilder;

import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.scanner.ValidationScanner;
import jca.springframework.view.View;

public abstract class ViewBuilder {
    
    public abstract View buildView(Object obj , MappingAnnotation mappingAnnotation , ValidationScanner validationScanner);
    public static ViewBuilder getBuilder(MappingAnnotation mappingAnnotation){
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
