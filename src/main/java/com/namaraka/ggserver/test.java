/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.LocalDateTime;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author smallgod
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            Server server = new Server(8080);

            WebAppContext context = new WebAppContext();

            context.setContextPath("/");
            //context.setWar("src/main/ggserver/war/aopc.war");
            context.setResourceBase("/home/smallgod/NetBeansProjects/src/main/ggserver");
            context.setDescriptor("/home/smallgod/NetBeansProjects/src/main/ggserver/WEB-INF/web.xml");
            context.setDefaultsDescriptor("/home/smallgod/NetBeansProjects/src/main/ggserver/webdefault/webdefault.xml"); //copy from The location is JETTY_HOME/etc/webdefault.xml     
            context.setParentLoaderPriority(true);//make the ClassLoader behavior more akin to standard Java (as opposed to the reverse requirements for Servlets)

            server.setHandler(context);
            
            server.start();
            server.dumpStdErr();

            ApplicationPropertyLoader.loadInstance();
            
            System.out.println("<<<<<<<< The Joda-time is >>>>>> :: " + new LocalDateTime());

            server.join();

        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
