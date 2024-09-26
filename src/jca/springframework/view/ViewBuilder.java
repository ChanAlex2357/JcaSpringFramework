package jca.springframework.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jca.springframework.annotations.MappingAnnotation;
import jca.springframework.annotations.RestApi;

public class ViewBuilder {
    final static Gson gson = new GsonBuilder().create();
    public static Gson getGson() {
        return gson;
    }
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
        View view= null;
        
        if (obj instanceof ModelAndView){
            ModelAndView modelAndView = (ModelAndView) obj;
            view = new RestApiView( getGson().toJson(modelAndView.getData()));
        }
        else {
            view = new RestApiView( getGson().toJson(obj) );            
        }

        return view;
    }
    static public View getViewOf(Object obj , MappingAnnotation mappingAnnotation){
        View resultView = null;
        if (mappingAnnotation.getAnnotation() instanceof RestApi) {
            resultView = getJsonView(obj);
        }
        else {
            resultView = getWebViewOf(obj);
        }
        return resultView;
    }
}
