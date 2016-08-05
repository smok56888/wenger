package com.smok.wenger.util;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

/**
 * Created by smok.laf on 2016/8/5 16:19
 */
public class MultiModeConfigurer extends PropertySourcesPlaceholderConfigurer {
  private static final Properties properties;

  static {
    String argName = "profile";
    String devConf = "dev";
    String profile = System.getProperty(argName);
    if (profile == null) {
      profile = devConf;
    }

    System.setProperty(argName, profile);
    System.out.println("----------------------------------------------");
    System.out.println("You are using the configuration in folder " + profile);
    System.out.println("----------------------------------------------");

    try {
      Thread.sleep(2000L);
    } catch (InterruptedException var4) {
      var4.printStackTrace();
    }

    properties = new Properties();
  }

  public MultiModeConfigurer() {
  }

  protected void convertProperties(Properties props) {
    super.convertProperties(props);
    properties.putAll(props);
  }


  public static String getPropertyByKey(String key) {
    return properties.getProperty(key);
  }
}
