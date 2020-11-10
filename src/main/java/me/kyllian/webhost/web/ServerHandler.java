package me.kyllian.webhost.web;

import me.kyllian.webhost.WebhostPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerHandler {

    private WebhostPlugin plugin;
    private Server server;

    public ServerHandler(WebhostPlugin plugin) {
        this.plugin = plugin;
    }

    public void fire() throws Exception {
        server = new Server(plugin.getConfig().getInt("port"));
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(plugin.getDataFolder().getPath().concat(plugin.getConfig().getString("resource_base")));
        context.setWelcomeFiles(plugin.getConfig().getStringList("welcome_files").toArray(new String[0]));

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
