package com.datahive.connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Properties;


@Component
public class SshConfiguration implements ServletContextInitializer {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public SshConfiguration() {
        try {
            Properties p = new Properties();
            p.load(getClass().getResourceAsStream("/application.properties"));
            //Ssh forwarding is used if the configuration file contains the ssh.forward.enabled attribute
            if(p.getProperty("ssh.forward.enabled")!=null){
                log.info("ssh forward is opened.");
                log.info("ssh init ……");
                Session session = new JSch().getSession(p.getProperty("ssh.forward.username"),p.getProperty("ssh.forward.host"),Integer.valueOf(p.getProperty("ssh.forward.port")));
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(p.getProperty("ssh.forward.password"));
                session.connect();
                session.setPortForwardingL(p.getProperty("ssh.forward.from_host"),Integer.valueOf(p.getProperty("ssh.forward.from_port")) ,p.getProperty("ssh.forward.to_host") ,Integer.valueOf(p.getProperty("ssh.forward.to_port")) );
            }else{
                log.info("ssh forward is closed.");
            }
        } catch (IOException e) {
            log.error("ssh IOException failed.", e);
        } catch (JSchException e) {
            log.error("ssh JSchException failed.", e);
        } catch (Exception e) {
            log.error("ssh settings is failed. skip!", e);
        }
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}