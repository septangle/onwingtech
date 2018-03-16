package com.onwing;

import java.io.File;


import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;


public class Main {
	private final static String webappDirLocation = "src/main/webapp/";
	private static Tomcat tomcat = null;

	public static void main(String[] args) {

		tomcat = new Tomcat();
		tomcat.setPort(8080);

		try {
			StandardContext ctx = (StandardContext) tomcat.addWebapp("/onwing-web",
					new File(webappDirLocation).getAbsolutePath());
			System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

			// Declare an alternative location for your "WEB-INF/classes" dir
			// Servlet 3.0 annotation will work
			/*
			File additionWebInfClasses = new File("target/classes");
			WebResourceRoot resources = new StandardRoot(ctx);
			resources.addPreResources(
					new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
			ctx.setResources(resources);
			*/
			tomcat.getConnector().setURIEncoding("utf-8");//等同于tomcat server.xml中配置URIEncoding属性
			tomcat.start();
			tomcat.getServer().await();
		} catch (Exception e) {
			System.out.println("Tomcat could not be started.");
			e.printStackTrace();
		}
	}
}
