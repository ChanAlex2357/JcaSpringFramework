package jca.springframework;

import java.util.ArrayList;
import java.util.List;

public class PackageScanner {
    /**
     * La fonction fincClasses retrouve les classes d'un package specifier en parametre
     * @param package_name le nom du package a scanner
     * @return la liste des classes portant le package
     */
  static public List<Class<?>> findClasses(String package_name){
    List<Class<?>> classes = new ArrayList<Class<?>>();

    return classes;
  }
  /**
   * La fonction findClasseesNames retrouve le nom des classes
   * qui portent le package specifier en parametre
   * Elle retouve d'abord la liste des classes avec findClasses 
   * pour ensuite recuperer le nom de chacune d'entre elles 
   * @param package_name le nom du package a scanner
   * @return la liste des noms des classes
   */
  static public List<String> findClassesNames(String package_name){
    List<String> classes_names =  new ArrayList<String>();
    
    /// recuperer la liste des classes
    List<Class<?>> classes = PackageScanner.findClasses(package_name);
    /// Recuperer le nom de chaque classe
    for (Class<?> class1 : classes) {
        classes_names.add( class1.getName() );
    }

    return classes_names;
  }  
}
