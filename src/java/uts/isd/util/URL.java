/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author rhys
 */
public class URL {
    
    public static String Absolute(String uri, HttpServletRequest request)
    {
        String s = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + uri;
        return s;
    }
}
