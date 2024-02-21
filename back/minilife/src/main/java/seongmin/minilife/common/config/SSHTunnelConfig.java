package seongmin.minilife.common.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

import static java.lang.System.exit;

@Slf4j
@Component
@Validated
@Setter
public class SSHTunnelConfig {

    @Value("${ssh.host}")
    private String host;
    @Value("${ssh.port}")
    private int port;
    @Value("${ssh.pem}")
    private String privateKey;
    @Value("${ssh.user}")
    private String sshUser;
    @Value("${ssh.database-port}")
    private int databasePort;

    private Session session;

    public void closeSSH() {
        if (session != null && session.isConnected())
            session.disconnect();
    }

    public Integer connectSSH() {

        Integer forwardedPort = null;

        try {
            log.info("user: {}", sshUser);
            log.info("host: {}", host);
            log.info("port: {}", port);
            log.info("databasePort: {}", databasePort);
            log.info("pem: {}", privateKey);
            log.info("{}@{}:{}:{} with privateKey", sshUser, host, port, databasePort);

            log.info("Start SSH Tunneling");

            JSch jSch = new JSch();

            jSch.addIdentity(privateKey);
            session = jSch.getSession(sshUser, host, port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            log.info("Complete Creating SSH Session");

            log.info("Start Connection SSH Connection");
            session.connect(); // ssh 연결
            log.info("Start Forwarding");
            forwardedPort = session.setPortForwardingL(33306, "localhost", databasePort);
            log.info("Successfully to Connect Database");

        } catch (JSchException e) {
            log.error("SSH Tunneling Error");
            this.closeSSH();
            e.printStackTrace();
            exit(1);
        }

        return forwardedPort;
    }

}
