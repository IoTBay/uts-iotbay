/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-26
 */
public class URL {
    
    /**
     * This function determines the absolute URL of a resource, when given
     * the path relative to the root of the site.
     * 
     * @param uri The URI of the resource relative to the root of this site's project
     * @param request The HTTP Request
     * @return A string containing an absolute URL for the resource given.
     */
    public static String Absolute(String uri, HttpServletRequest request)
    {
        String s = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + uri;
        return s;
    }
    
    /**
     * This function determines the absolute URL of an Image resource, when given
     * the path relative to the root images folder.
     * 
     * @param uri The URI of the resource relative to the root of this site's project
     * @param request The HTTP Request
     * @return A string containing an absolute URL for the resource given.
     */
    public static String Image(String uri, HttpServletRequest request)
    {
        return URL.Absolute("img/"+uri, request);
    }
    
    /**
     * This function will try to return the user back to their previous page.
     * 
     * If that cannot be determined, the user is sent to the main page.
     * 
     * @param request The HTTP Request
     * @param response The HTTP Response
     * @throws IOException 
     */
    public static void GoBack(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String location = (request.getHeader("referer") != null ? request.getHeader("referer") : URL.Absolute("", request));
        response.sendRedirect(location);
    }
}
