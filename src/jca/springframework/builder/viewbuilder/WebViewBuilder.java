package jca.springframework.builder.viewbuilder;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.VerbAction;
import jca.springframework.view.ExportFileView;
import jca.springframework.view.ModelAndView;
import jca.springframework.view.RedirectView;
import jca.springframework.view.StringView;
import jca.springframework.view.View;
import jca.springframework.view.exception.InvalidReturnException;

public class WebViewBuilder extends ViewBuilder{

    /**
     * Instancier une vue vers une page web avec ModelAndView ou un String View
     * Retourner la view correspondat pour un type valide de l'objet
     * @param obj Object source de la view
     * @return view si l'obj est gerable en view
     * @return null si l'obj n'est pas gerable en tant que view
          * @throws FrameworkException 
          */
         @Override
    public View buildView(Object obj, VerbAction vba) throws FrameworkException {
        View view= null;
        System.out.println();
        if (obj instanceof ModelAndView){
            ModelAndView mv = (ModelAndView) obj;
            view = mv;
        }
        else if (obj instanceof ExportFileView) {
            ExportFileView fileView = (ExportFileView) obj;
            view = fileView;            
        }
        else if (obj instanceof RedirectView){
            RedirectView rv = (RedirectView) obj;
            view = rv;
        }
        else if (obj instanceof String) {
            view = new StringView( obj.toString() );            
        }
        else {
            throw new InvalidReturnException(vba,obj);
        }
        return view;
    }
    
}
