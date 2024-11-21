package jca.springframework.builder.view;

import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.view.ModelAndView;
import jca.springframework.view.StringView;
import jca.springframework.view.View;

public class WebViewBuilder extends ViewBuilder{

    /**
     * Instancier une vue vers une page web avec ModelAndView ou un String View
     * Retourner la view correspondat pour un type valide de l'objet
     * @param obj Object source de la view
     * @return view si l'obj est gerable en view
     * @return null si l'obj n'est pas gerable en tant que view
     */
    @Override
    public View buildView(Object obj, MappingAnnotation mappingAnnotation) {
        View view= null;
        if (obj instanceof ModelAndView){
            view = (View)obj;
        }
        else if (obj instanceof String) {
            view = new StringView( obj.toString() );            
        }
        return view;
    }
    
}
