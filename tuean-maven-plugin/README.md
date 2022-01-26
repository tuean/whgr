## tuean-maven-plugin
a maven plugin which providers some function.


### usage scenarios
At the start of beginning, it would be better to use [mybatis/generator](https://github.com/mybatis/generator)

But when want to modify some sql or mapper methods, the official plugin would rewrite our java file、 apppend our xml file

which means something we need would be loss or must be manually modified.

It's boaring when you do this for times and times.

This plugin would be a better solution for this situation.


### codeGenerator
generate mybatis files:

1. entity java file
2. mapper java file
3. mapper xml file


### usage
make config file in resource, the path is **tuean/codeGenerator.yaml**
```text
└── resources
    ├── application.properties
    ├── mapper
    │   └── TodoInfoMapper.xml
    └── tuean
        └── codeGenerator.yaml
```


codeGenerator.yaml example for mysql:

```yaml
db:
  jar_path: {path}/mysql-connector-java.jar
  driver_class: com.mysql.cj.jdbc.Driver
  user: {user}
  pwd: {password}
  database: {name}
  url: {jdbcUrl}

mapper:
  workdir: src/main/java
  entity: com.tuean.whgr.entity.db
  dao: com.tuean.whgr.dao
  xml: mapper

generator:
  menus:
    entity: entity class name
    dao: mapper class name 
    xml: mapper file name

```


add pom:
```xml
<dependency>
    <groupId>io.github.tuean</groupId>
    <artifactId>tuean-maven-plugin</artifactId>
    <version>0.0.2</version>
</dependency>
```

exccute maven goal:
```shell
mvn tuean:codeGenerator
```

stdout example:
```text
/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/bin/java -Dmaven.multiModuleProjectDirectory=/Users/tuean/IdeaProjects -Dmaven.home=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3 -Dclassworlds.conf=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/m2.conf -Dmaven.ext.class.path=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven-event-listener.jar -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=64989:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds.license:/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version=2020.3.3 -s /Users/tuean/.m2/settings.xml -Dmaven.repo.local=/Users/tuean/.m2/repository -DskipTests=true tuean:codeGenerator
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< com.tuean:whgr-backend >-----------------------
[INFO] Building whgr-backend 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- tuean-maven-plugin:0.0.2:codeGenerator (default-cli) @ whgr-backend ---
[info] work dir is : /Users/tuean/IdeaProjects/whgr/whgr-backend/pom.xml
[info] MavenProject: com.tuean:whgr-backend:0.0.1-SNAPSHOT @ /Users/tuean/IdeaProjects/whgr/whgr-backend/pom.xml
[info] CodeGenerateConfig{db=org.tuean.entity.ConfigDb@58a120b0, mapper=ConfigMapper{workdir='src/main/java', entity='com.tuean.whgr.entity.db', dao='com.tuean.whgr.dao', xml='mapper'}, generator={menus=ConfigGenerator{entity='Menus', dao='MenusMapper', xml='MenusMapper'}}}
[info] JavaClass{packageInfo='com.tuean.whgr.entity.db', importList=null, classType='class', className='Menus', methodList=[JavaMethod{methodName='getId', isStatic=false, isFinal=false, voidFlag=false, interfaceMethod=false, returnClass=class java.lang.Long, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=null, methodBody=[return this.id;]}, JavaMethod{methodName='setId', isStatic=false, isFinal=false, voidFlag=true, interfaceMethod=false, returnClass=null, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=[JavaMethodArgs{index=0, argName='id', argClass=class java.lang.Long, argClassStr='null', annotation=null}], methodBody=[this.id = id;]}, JavaMethod{methodName='getMenuName', isStatic=false, isFinal=false, voidFlag=false, interfaceMethod=false, returnClass=class java.lang.String, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=null, methodBody=[return this.menuName;]}, JavaMethod{methodName='setMenuName', isStatic=false, isFinal=false, voidFlag=true, interfaceMethod=false, returnClass=null, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=[JavaMethodArgs{index=0, argName='menuName', argClass=class java.lang.String, argClassStr='null', annotation=null}], methodBody=[this.menuName = menuName;]}, JavaMethod{methodName='getMenuType', isStatic=false, isFinal=false, voidFlag=false, interfaceMethod=false, returnClass=class java.lang.Integer, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=null, methodBody=[return this.menuType;]}, JavaMethod{methodName='setMenuType', isStatic=false, isFinal=false, voidFlag=true, interfaceMethod=false, returnClass=null, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=[JavaMethodArgs{index=0, argName='menuType', argClass=class java.lang.Integer, argClassStr='null', annotation=null}], methodBody=[this.menuType = menuType;]}, JavaMethod{methodName='getMenuUrl', isStatic=false, isFinal=false, voidFlag=false, interfaceMethod=false, returnClass=class java.lang.String, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=null, methodBody=[return this.menuUrl;]}, JavaMethod{methodName='setMenuUrl', isStatic=false, isFinal=false, voidFlag=true, interfaceMethod=false, returnClass=null, returnClassStr='null', javaVisible=JavaVisible{visibleRange='public'}, args=[JavaMethodArgs{index=0, argName='menuUrl', argClass=class java.lang.String, argClassStr='null', annotation=null}], methodBody=[this.menuUrl = menuUrl;]}], fieldList=[JavaField{javaVisible=JavaVisible{visibleRange='private'}, isStatic=false, isFinal=false, fieldClazz=class java.lang.Long, fieldName='id'}, JavaField{javaVisible=JavaVisible{visibleRange='private'}, isStatic=false, isFinal=false, fieldClazz=class java.lang.String, fieldName='menuName'}, JavaField{javaVisible=JavaVisible{visibleRange='private'}, isStatic=false, isFinal=false, fieldClazz=class java.lang.Integer, fieldName='menuType'}, JavaField{javaVisible=JavaVisible{visibleRange='private'}, isStatic=false, isFinal=false, fieldClazz=class java.lang.String, fieldName='menuUrl'}], locationPath='null'}
[info]     int insert(Menus menus);
[info]     int selectByPrimaryKey(Long id);
[info]     int updateByPrimaryKey(Menus menus);
[info]     int updateByPrimaryKeySelective(Menus menus);
[info]     int deleteByPrimaryKey(Long id);
[INFO] Reflections took 71 ms to scan 1 urls, producing 6 keys and 16 values
[INFO] Reflections took 12 ms to scan 1 urls, producing 6 keys and 16 values
[INFO] Reflections took 6 ms to scan 1 urls, producing 6 keys and 16 values
[INFO] Reflections took 5 ms to scan 1 urls, producing 6 keys and 16 values
[INFO] Reflections took 5 ms to scan 1 urls, producing 6 keys and 16 values
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.726 s
[INFO] Finished at: 2022-01-19T11:09:28+08:00
[INFO] ------------------------------------------------------------------------

Process finished with exit code 0

```


### output 
This plugin providers 5 default methods (defined in org.tuean.enums.InitMethod.java):
1. insert
2. selectByPrimaryKey
3. updateByPrimaryKey
4. deleteByPrimaryKey
5. updateByPrimaryKeySelective

If you don't write method names or sql ids in above 5 names, it would fill in the result.

Also if you have written, the template code would be replaced by your own code.



### changelog
* version 0.0.2 (2022-01-19)
  
  the first available version 
