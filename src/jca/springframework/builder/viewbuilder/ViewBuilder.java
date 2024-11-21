package jca.springframework.builder.view;

import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.view.View;

public abstract class ViewBuilder {
    
    public abstract View buildView(Object obj , MappingAnnotation mappingAnnotation);
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
