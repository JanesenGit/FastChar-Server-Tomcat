package com.fastchar.server.tomcat;

import com.fastchar.core.FastChar;
import com.fastchar.server.ServerStartHandler;
import com.fastchar.utils.FastClassUtils;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class FastServerTomcat {


    public static FastServerTomcat getInstance() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Class<?> fromClass = FastClassUtils.findClass(stackTrace[2].getClassName());
        if(fromClass == null) {
            throw new NullPointerException("fromClass must not be null");
        }
        FastChar.getPath().setProjectJar(fromClass);
        return new FastServerTomcat().initServer();
    }

    private FastServerTomcat() {
    }

    private Tomcat tomcat;
    private final ServerStartHandler starterHandler = new ServerStartHandler();

    private FastServerTomcat initServer() {
        starterHandler.setStartRunnable(() -> {
            try {
                if (tomcat != null) {
                    tomcat.start();
                    //必须检查一下，否则在Tomcat9.0以上不会主动挂载Connector
                    checkThatConnectorsHaveStarted(tomcat);
                }
            } catch (LifecycleException e) {
                stop();
                throw new RuntimeException(e);
            }
        }).setStopRunnable(this::stop);
        return this;
    }


    public synchronized void start(FastTomcatConfig tomcatConfig) {
        try {
            if (tomcat != null) {
                return;
            }

            FastChar.getConstant().setEmbedServer(true);

            tomcat = new Tomcat();

            String baseDirectory = tomcatConfig.getBaseDirectory();
            tomcat.setBaseDir(baseDirectory);
            tomcatConfig.configureServer(tomcat.getServer());

            Connector connector = new Connector(tomcatConfig.getProtocol());
            tomcat.setConnector(connector);

            tomcatConfig.configConnector(connector);
            tomcatConfig.configService(tomcat.getService());
            tomcatConfig.configureEngine(tomcat.getEngine());
            tomcatConfig.configHost(tomcat.getHost());


            StandardContext context = new StandardContext();
            tomcatConfig.configContext(context);
            tomcat.getHost().addChild(context);

            this.starterHandler
                    .setPort(tomcatConfig.getPort())
                    .setHost(tomcatConfig.getHost())
                    .setContextPath(tomcatConfig.getContextPath())
                    .start();

        } catch (Exception e) {
            stop();
            throw new RuntimeException(e);
        }
    }


    public synchronized void stop() {
        try {
            if (this.tomcat != null) {
                this.tomcat.stop();
                this.tomcat.destroy();
                this.tomcat = null;
            }
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }


    private void checkThatConnectorsHaveStarted(Tomcat tomcat) {
        checkConnectorHasStarted(tomcat.getConnector());
        for (Connector connector : tomcat.getService().findConnectors()) {
            checkConnectorHasStarted(connector);
        }
    }

    private void checkConnectorHasStarted(Connector connector) {
        if (LifecycleState.FAILED.equals(connector.getState())) {
            throw new RuntimeException("启动Tomcat-" + connector.getPort() + "失败！");
        }
    }


}
