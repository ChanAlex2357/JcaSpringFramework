package jca.springframework.view;

import jca.springframework.annotations.MappingAnnotation;
import jca.springframework.annotations.RestApi;

public class ViewBuilder {
    /**
     * Retourner la view correspondat pour un type valide de l'objet
     * @param obj Object source de la view
     * @return view si l'obj est gerable en view
     * @return null si l'obj n'est pas gerable en tant que view
     */
    static public View getWebViewOf(Object obj){
        View view= null;

        if (obj instanceof ModelAndView){
            view = (View)obj;
        }
        else if (obj instanceof String) {
            view = new StringView( obj.toString() );            
        }

        return view;
    }
    static public View getJsonView( Object obj){

    }
    static public View getViewOf(Object obj , MappingAnnotation mappingAnnotation){
        View resultView =null;
        if (mappingAnnotation.getAnnotation() instanceof RestApi) {
            resultView = getJsonView(obj);
        }
        else{
            resultView = getWebViewOf(obj);
        }

    }
}
