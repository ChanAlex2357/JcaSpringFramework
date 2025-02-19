package jca.springframework.builder.viewbuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.MappingAnnotation;
import jca.springframework.mapping.VerbAction;
import jca.springframework.scanner.ValidationScanner;
import jca.springframework.view.ModelAndView;
import jca.springframework.view.RestApiView;
import jca.springframework.view.View;

public class JsonViewBuilder extends ViewBuilder {
    final static Gson gson = new GsonBuilder().create();
    public static Gson getGson() {
        return gson;
    }
    @Override
    public View buildView(Object obj, VerbAction vba,ValidationScanner validationScanner) throws FrameworkException {
        View view= null;
        if (obj instanceof ModelAndView){
            ModelAndView modelAndView = (ModelAndView) obj;
            view = modelAndView.getAsView(validationScanner);
            if (view == null) {
                view = new RestApiView( getGson().toJson(modelAndView.getData()));
            }
        }
        else {
            view = new RestApiView( getGson().toJson(obj) );            
        }
        return view;
    }
    
}
