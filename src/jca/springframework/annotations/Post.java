package jca.springframework.annotations;

public @interface Post {
    public String url() default "/index";    
}
