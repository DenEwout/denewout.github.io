---
layout: post
title:  "Preventing dependency issues when writing Maven plugins"
date:   2016-02-01 17:45:22 +0100
author: Ewout
---

I work on a project called [Miredot](https://www.miredot.com), which is a Maven plugin. Depending on what sort of plugin you're writing, 
your plugin might want to load some of the classes that are present in your project.

Some cursory googling will quickly bring you [here](http://maven.apache.org/guides/mini/guide-maven-classloading.html), which is 
Maven's guide to classloading. They explain how you can add dependencies to your plugin configuration and that
when the plugin is executed "the current thread's context classloader is set to the plugin classloader".

You also might have seen that last bit done manually using a code snippet that looks like this:
{% highlight java %}
    ClassLoader current = Thread.currentThread().getContextClassLoader();
    URLClassLoader urlClassLoader = new URLClassLoader(
        new URL[]{new File("folder/to/load/classes/from").toURL()}, current
    );
    Thread.currentThread().setContextClassLoader(urlClassLoader);
{% endhighlight %}

## Log4J troubles
The above methods work, but it doesn't take into account that the project classes, might have dependencies that collide with the 
dependencies of your plugin. An example of such a case would be when your plugin depends on Log4J, and it is loading a class
that depends on a different version of Log4J.

Usually classloaders always defer to their "parent" when trying to load a class. Log4J is a special case here, 
since it directly asks the current thread's classloader to load classes from the Log4J package. 
This causes trouble if you're extending your classpaths like explained above, because now you've introduced **multiple versions Log4J** on your classpath.

## Solution
The solution for this is quite simple. Don't set your "extended" classloader as the current thread's context classloader. 
Simply create a separate classloader instance and keep that instance for whenever you need to load a class of your project.

Doing so, you prevent some of the mixed dependency issues with the project it is running on.



