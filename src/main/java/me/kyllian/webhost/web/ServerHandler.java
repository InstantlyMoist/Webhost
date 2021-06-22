package me.kyllian.webhost.web;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class ServerHandler {

    private Server server;

    public void fireServer(boolean https, String password, File keyStore, File dataFolder, int port, String resourceBase, String[] welcomeFiles) throws Exception{
        if (https) fireHttps(password, keyStore, dataFolder, port, resourceBase, welcomeFiles);
        else fire(dataFolder, port, resourceBase, welcomeFiles);
    }

    public void fireHttps(String password, File keyStore, File dataFolder, int port, String resourceBase, String[] welcomeFiles) throws Exception{
        if (!keyStore.exists()) throw new FileNotFoundException(keyStore.getAbsolutePath());

        Server server = new Server();

        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSecureScheme("https");
        httpConfiguration.setSecurePort(443);
        httpConfiguration.setOutputBufferSize(32768);

        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(httpConfiguration));
        http.setPort(port);

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keyStore.getAbsolutePath());
        sslContextFactory.setKeyStorePassword(password);
        sslContextFactory.setKeyManagerPassword(password);

        HttpConfiguration httpsConfiguration = new HttpConfiguration(httpConfiguration);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(2000);
        src.setStsIncludeSubDomains(true);
        httpsConfiguration.addCustomizer(src);

        ServerConnector https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(httpsConfiguration));

        https.setPort(443);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(dataFolder.getPath().concat(resourceBase));
        context.setWelcomeFiles(welcomeFiles);

        ServletHolder holderPwd = new ServletHolder("default",
                DefaultServlet.class);

        holderPwd.setInitParameter("dirAllowed", "true");
        context.addServlet(holderPwd, "/");

        server.setConnectors(new Connector[] { http, https });

        server.setHandler(context);

        server.start();
        server.join();
    }

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
