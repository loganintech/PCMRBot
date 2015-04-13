/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.util;

import java.net.URL;
import java.net.MalformedURLException;

public class URLInString {
    public static Boolean CheckForUrl(String args) {
        String [] parts = args.split("\\s+");

        for( String item : parts ) try {
  URL url = new URL(item);
    return true;
        } catch (MalformedURLException e) {
          
        }
    return false;
    }
}