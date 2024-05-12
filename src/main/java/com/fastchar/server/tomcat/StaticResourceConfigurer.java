package com.fastchar.server.tomcat;

import com.fastchar.server.StaticResourceJars;
import com.fastchar.server.tomcat.FastTomcatConfig;
import com.fastchar.utils.FastStringUtils;
import org.apache.catalina.*;

import java.net.URL;
import java.util.List;

public final class StaticResourceConfigurer implements LifecycleListener {

    private final Context context;

    private final FastTomcatConfig tomcatConfig;

    public StaticResourceConfigurer(FastTomcatConfig tomcatConfig,Context context) {
        this.tomcatConfig = tomcatConfig;
        this.context = context;
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
            addResourceJars(new StaticResourceJars().getUrls());

            for (URL resource : tomcatConfig.getResources()) {
                this.context.getResources().createWebResourceSet(WebResourceRoot.ResourceSetType.RESOURCE_JAR,
                        "/", resource, "/");
            }
        }
    }

    private void addResourceJars(List<URL> resourceJarUrls) {
        for (URL url : resourceJarUrls) {
            String path = url.getPath();
            if (path.endsWith(".jar") || path.endsWith(".jar!/")) {
                String jar = url.toString();
                if (!jar.startsWith("jar:")) {
                    // A jar file in the file system. Convert to Jar URL.
                    jar = "jar:" + jar + "!/";
                }
                addResourceSet(jar);
            } else {
                addResourceSet(url.toString());
            }
        }
    }

    private void addResourceSet(String resource) {
        try {
            if (isInsideNestedJar(resource)) {
                // It's a nested jar but we now don't want the suffix because Tomcat
                // is going to try and locate it as a root URL (not the resource
                // inside it)
                resource = resource.substring(0, resource.length() - 2);
            }
            URL url = new URL(resource);

            for (String internalPath : StaticResourceJars.INTERNAL_PATHS) {
                this.context.getResources().createWebResourceSet(WebResourceRoot.ResourceSetType.RESOURCE_JAR,
                        "/", url, "/" + FastStringUtils.stripStart(internalPath, "/"));
            }
        } catch (Exception ex) {
            // Ignore (probably not a directory)
        }
    }

    private boolean isInsideNestedJar(String dir) {
        return dir.indexOf("!/") < dir.lastIndexOf("!/");
    }

}