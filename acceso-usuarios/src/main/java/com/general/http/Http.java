package com.general.http;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



/**
 * Implementa las peticiones HTPP con los verbos GET, POST, PUT, DELETE
 *
 * @author Ingeneo
 *
 */

@Component
public class Http implements ClienteHttpService {


	private final Logger log = LoggerFactory.getLogger(Http.class);

	

    @Override
    public ResponseEntity<?> get( URI uri, Class<?> object, MediaType mediaType )
        throws Exception {
        return execute( uri, HttpMethod.GET, null, object, mediaType,  true );
    }

    @Override
    public ResponseEntity<?> delete( URI uri, Class<?> object, MediaType mediaType)
        throws Exception {
        return execute( uri, HttpMethod.DELETE, null, object, mediaType,  true );
    }

    @Override
    public ResponseEntity<?> post( URI uri, Object data, Class<?> objectClass, MediaType mediaType )
        throws Exception {
        return execute( uri, HttpMethod.POST, data, objectClass, mediaType,  true );
    }

    @Override
    public ResponseEntity<?> put( URI uri, Object data, Class<?> objectClass, MediaType mediaType )
        throws Exception {
        return execute( uri, HttpMethod.PUT, data, objectClass, mediaType, true );
    }

 

    private ResponseEntity<?> execute( URI uri, HttpMethod httpMethod, Object requestParam, Class<?> objectClass, MediaType mediaType,  Boolean activeSSL )
        throws Exception {

        HttpHeaders headers = new HttpHeaders();

     

        if( mediaType != null ) {
            headers.setContentType( mediaType );
        }

        HttpEntity<?> request;

        if( requestParam == null ) {
            request = new HttpEntity<>( headers );
        }
        else {
            request = new HttpEntity<>( requestParam, headers );
        }

        ResponseEntity<?> response;

        Long initTime;
        Long endTime;
        Long totalTime;

        initTime = System.currentTimeMillis();

        RestTemplate restTemplate;

        RestTemplateBuilder builder = new RestTemplateBuilder();

        Integer timeoutHttp = 1;

        String gp = null;

        if( gp != null ) {
            timeoutHttp = Integer.parseInt(gp );
        }

        restTemplate = builder
            .setReadTimeout( Duration.ofMinutes( timeoutHttp ) )
            .setConnectTimeout( Duration.ofMinutes( timeoutHttp ) )
            .build();

        if( !activeSSL ) {
            restTemplate.setRequestFactory( this.requestFactory() );
        }

        response = restTemplate.exchange( uri, httpMethod, request, objectClass );

        endTime = System.currentTimeMillis();

        totalTime = endTime - initTime;

       log.info( "El tiempo de respuesta de " + uri + " es de " + totalTime + " ms" );

        return response;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory()
        throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial( null, acceptingTrustStrategy )
            .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory( sslContext );

        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory( csf )
            .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient( httpClient );

        return requestFactory;
    }

}