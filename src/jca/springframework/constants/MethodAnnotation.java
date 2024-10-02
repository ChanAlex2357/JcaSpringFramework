package jca.springframework.constants;

import jca.springframework.annotations.method.Get;
import jca.springframework.annotations.method.Post;
import jca.springframework.annotations.method.RestApi;
import jca.springframework.annotations.method.Url;

public class MethodAnnotation {
    private static final Class<Get>     GET_ANNOTATION_CLASS        =   Get.class;
    private static final Class<RestApi> REST_API_ANNOTATION_CLASS   =   RestApi.class;
    private static final Class<Url>     URL_ANNOTATION_CLASS        =   Url.class;
    private static final Class<Post>    POST_ANNOTATION_CLASS       =   Post.class;
/// Getters
    public static Class<Get> GET()         {return GET_ANNOTATION_CLASS;}
    public static Class<RestApi> REST_API() {return REST_API_ANNOTATION_CLASS;}
    public static Class<Url> URL()         {return URL_ANNOTATION_CLASS;}      
    public static Class<Post> POST() {return POST_ANNOTATION_CLASS;}
}
