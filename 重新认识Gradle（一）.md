重新认识Gradle-安装篇

### 首先Gradle是什么？ ###

简要回答：Gradle只是一种构建工具，其他构建工具还有Ant，Maven

### 为什么要学习Gradle？ ###

简要回答：因为好用，因为构建速度更快。因为我学Android，我用Gradle。

### 
本节你会学习到什么？ ###

回答:1 .不同平台的Gradle安装    2.Gradle和Gradle Wrapper的区别



1.如何安装Gradle，从linux安装，从windows安装

写在安装之前：

	gradle能够运行在所有流行的系统上，mac，windows，linux，并且还需要安装
	jdk或者jre1.7的版本或者比这个更好的版本。检查jdk版本 可以使用
	‘java -version’ 如下所示

![](https://i.imgur.com/DkfVRmb.png)

#### 1.1 使用包管理器安装可以有如下安装方法（类似于python中 pip中安装包的方法一样）： ####

linux安装：[SDKMAN](http://sdkman.io/ "SDKMAN")工具安装  

	 $ sdk install gradle 4.9

macOS安装：[Homebrew](Homebrew "http://brew.sh/")

	$ brew install gradle

window安装：[Scoop](Scoop "http://scoop.sh/")

	$ scoop install gradle

window安装:[Chocolatey](Chocolatey "https://chocolatey.org/")

	$ choco install gradle

macOS安装:[MacPorts](MacPorts "https://www.macports.org/")

	$ sudo port install gradle

#### 1.2手动安装 ####

1.2.1 通过[download](download "https://gradle.org/releases")该链接进入Gradle下载链接，当前（8/21/2018）最新版本 为4.9，
下载的zip压缩文件有2种类型的，一种是Binary-only，一种是Complete版（建议下载此版本，里面包含文档和代码。）当你选择Binary-only
版本的时候，就只有通过在线浏览的方式查看文档和源码，如果需要切换回老版本的Gradle版本，
可以通过[releases page.](releases page. "https://gradle.org/releases/") 下载你所需要的Gradle版本即可。

1.2.2 下载完成以后需要解压才能使用。解压步骤就不详细讲解了。

	Linux和MacOs解压

	$ mkdir /opt/gradle
	$ unzip -d /opt/gradle gradle-4.9-bin.zip
	$ ls /opt/gradle/gradle-4.9
	LICENSE  NOTICE  bin  getting-started.html  init.d  lib  media

	配置环境变量
	
	$ export PATH=$PATH:/opt/gradle/gradle-4.9/bin

.

	window解压
	
	创建一个你自己的目录 比如 C:\Gradle 然后将下载的zip压缩包拖动到文件夹中进行解压，将文件解压到改文件夹中。
	
	最后设置你的Path的环境变量 比如  C:\Gradle\gradle-4.9\bin  （注意一定是到bin这一层目录）



手动安装完成以后，可以通过使用gradle -v 验证你的Gradle 是否安装成功（我这里是4.4.1的版本）

![](https://i.imgur.com/bvNzb5o.png)

#### 1.3 升级gradle。 ####

既然已经安装完成以后，那么Gradle也是一直在升级，为了跟上时代的步伐，我们也要与时俱进。那么升级可以用哪些方法呢，当然最简单的就是使用前面的方式，重新在下一份，重新更新环境变量即可。 当然，不止只有这种方式，Gradle还给我们提供了更新的方式。这里需要讲一下Gradle和GradleW的命令方式，看字面上的意思应该能够知道GradleW是经过了Gradle的一层包装。
看个图

![](https://i.imgur.com/Nvv4TzR.png)
	
这里为什么Gradle -v 和GradleW -v出现了2个不同的版本呢？ 一个4.4.1  一个4.9版本。这是为什么呢？

回答：gradle -v 执行的是我们在环境变量中配置的gradle属性。  gradlew是因为执行了一次gradle wrapper下载Gradle Wrapper之后则可以执行gradlew。然后又通过gradlew 升级了该包装，所以执行gradlew 则是独立于gradle的另一个对象。
所以，gradlew通常可以用来针对某一个项目来设定不同的版本，比如A项目用4.0的版本，b项目用4.4的版本，则不需要通过环境环境变量来回切换配置，就可以通过gradlew指定针对项目的不同的Gradle版本 （gradle 和gradlew命令是一样的）

	gradlew wrapper --gradle-version=4.9 --distribution-type=bin （all包含文档和源码，bin 不包含文档和源码）
	
	以上代码则是通过gradlew 指定wrapper的gradle版本，当下次执行gradlew task任务的时候，则会自动下载指定版本的gradle。 看下图：
![](https://i.imgur.com/NJ3tHpu.png)
	


到这里基本上关于gradle的安装就完成了。

### 2.关于Gradle Wrapper ###

下面先简单介绍一下[Gradle Wrapper](gradle_wrapper "https://docs.gradle.org/4.9/userguide/gradle_wrapper.html#sec:adding_wrapper")的工作流程


![](https://docs.gradle.org/current/userguide/img/wrapper-workflow.png)


**Gradle官方推荐我们使用Gradlew来构建。**

之前讲到了可以使用gradlew来指定gradle版本，那么怎么才能使用gradlew呢，直接使用可是不行的哦，必须要先执行gradle wrapper命令来安装wrapper

	> gradle wrapper
	> Task :wrapper

	BUILD SUCCESSFUL in 0s
	1 actionable task: 1 executed

如果需要统一所有人员都使用相同的gradle来构建项目，则使用gradlew是最好的办法，项目开始的开发中只需要将gradlew配置成功以后，讲gradlew相关的jar包和配置文件添加到版本控制如git，svn，其他项目开发成员则只需把对应文件拉取下来则可以立即开始构建。无需担心不同的开发者使用的gradle版本不一致的问题。

	在你自己项目目录下的路径
	
	gradle/wrapper/gradle-wrapper.properties

	项目中生成的相关属性在此配置文件
	
	distributionBase=GRADLE_USER_HOME //可以自己在环境变量中指定生成的目录 
	distributionPath=wrapper/dists	//分发的路径
	zipStoreBase=GRADLE_USER_HOME	//可以自己在环境变量中指定生成的目录 
	zipStorePath=wrapper/dists		//分发的路径
	distributionUrl=https\://services.gradle.org/distributions/gradle-2.10-all.zip //下载地址 （gradle-2.10-all.zip -all包含文档和源码，-bin 不包含文档和源码）

	比如想要切换不同的版本可以替换到上面的url链接。


以下这些命令可以帮助正常的gradlew 的配置参数

	--gradle-version 如上面的 切换gradle版本的时候可以使用到该命令  gradlew wrapper --gradle-version=4.9

	--distribution-type 分发类型 --distribution-type=bin 是bin 还是包含文档和源码的all类型
	
	--gradle-distribution-url 分发链接 主要是提供一个下载gradle的一个压缩包链接地址，可以提供依据下载好的本地的一个绝对路径定制，他讲会解压到对应已配置好的GRADLE_USER_HOME目录中。

使用Gradle Wrapper步骤

1.添加gradle wrapper 可以通过执行gradle wrapper命令，将会生成一个包装器。然后可以使用gradlew命令执行构建。

2.使用Gradle Wrapper ，可以通过使用gradlew clean  build 命令同gradle clean build命令一样。

3.升级Gradle Wrapper ，之前已经讲过 可以通过命令（gradlew wrapper --gradle-version 4.2.1 指定切换到4.2.1的版本）
升级完成后检查是否已经升级成功则可以通过使用gradlew -v 检查是否成功。


	> ./gradlew -v
	Downloading https://services.gradle.org/distributions/gradle-4.2.1-bin.zip
	...................................................................
	Unzipping /Users/claudia/.gradle/wrapper/dists/gradle-4.2.1-bin/dajvke9o8kmaxbu0kc5gcgeju/gradle-4.2.1-bin.zip to /Users/claudia/.gradle/wrapper/dists/gradle-4.2.1-bin/dajvke9o8kmaxbu0kc5gcgeju
	Set executable permissions for: /Users/claudia/.gradle/wrapper/dists/gradle-4.2.1-bin/dajvke9o8kmaxbu0kc5gcgeju/gradle-4.2.1/bin/gradle

	------------------------------------------------------------
	Gradle 4.2.1
	------------------------------------------------------------

	Build time:   2017-10-02 15:36:21 UTC
	Revision:     a88ebd6be7840c2e59ae4782eb0f27fbe3405ddf

	Groovy:       2.4.12
	Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
	JVM:          1.8.0_60 (Oracle Corporation 25.60-b23)
	OS:           Mac OS X 10.13.1 x86_64

在这里基本上Gradle的安装和Gradle和Gradle Wrapper就告一段落。
