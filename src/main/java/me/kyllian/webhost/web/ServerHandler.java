package me.kyllian.webhost.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;

public class ServerHandler {

    private Server server;

    public void fire(File dataFolder, int port, String resourceBase, String[] welcomeFiles) throws Exception {
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(dataFolder.getPath().concat(resourceBase));
        context.setWelcomeFiles(welcomeFiles);

        ServletHolder holderPwd = new ServletHolder("default",
                DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed", "true");
        context.addServlet(holderPwd, "/");
        server.setHandler(context);
        server.start();
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception exception) {
            // Well, we can't magically not stop the server. So we just ignore this...
            // This just.. never happened ;)
        }
    }
}
