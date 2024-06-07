package jca.springframework.view;

public class ViewBuilder {
    /**
     * Retourner la view correspondat pour un type valide de l'objet
     * @param obj Object source de la view
     * @return view si l'obj est gerable en view
     * @return null si l'obj n'est pas gerable en tant que view
     */
    static public View getViewOf(Object obj){
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
