package com.general.http;



import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.general.util.Util;


@Component
public class GenericoHttp {

    private final ClienteHttpService httpClient;
   
    @Autowired
    public GenericoHttp( ClienteHttpService httpClient) {
        this.httpClient = httpClient;
        
    }    

    public ResponseEntity<?> getService( String url, MultiValueMap<String, String> paramsApigee, Class<?> objectClass )
        throws Exception {

        URI uri = UriComponentsBuilder.fromUriString( url ).queryParams( paramsApigee ).build().toUri();

        return getService( uri, objectClass );
    }

    public ResponseEntity<?> getService( String url, String[] paramsApigee, Class<?> objectClass )
        throws Exception {

        URI uri = Util.buildUri( url, paramsApigee );

        return getService( uri, objectClass );
    }

    public ResponseEntity<?> getService( URI uri, Class<?> objectClass )
        throws Exception {

        ResponseEntity<?> response;

        response = ( ResponseEntity<?> ) this.httpClient.get( uri,
                                                              objectClass,
                                                              MediaType.APPLICATION_JSON                                                           );
       
        if( !ObjectUtils.isEmpty( response ) ) {
        }
        else {
        }
       
        return response;
    }

}