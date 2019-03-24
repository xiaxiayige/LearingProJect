# GradlePlugin制作插件流程
 
## 1.开始 

创建一个Library项目，并且删除build.gradle中原来的数据

## 2.处理Library的build.gradle文件 

	1.添加groovy插件 apply plugin: 'groovy'
	2.添加maven插件  apply plugin: 'maven
	3.添加groovy和gradleAPI的依赖关系:

		dependencies {
	    implementation gradleApi()
	    implementation localGroovy()
		}
	4.因为我们要制作成插件的方式，所以添加maven仓库的支持

	repositories {
    	mavenCentral()
	}
	
	5.然后配置一些我们在Maven上的一些基础配置信息。


	repositories {
    
		mavenCentral()
	
	}

	uploadArchives {
    repositories {
        mavenDeployer {
            //提交到远程服务器：
            // repository(url: "http://127.0.0.1:8081/repository/maven-releases/") {
            //      authentication(userName: 'admin', password: 'admin123')
            // }
           	//配置group:artifactId:version 信息
            pom.project {
                version '1.0.0'
                artifactId 'testPlugin'
                groupId 'com.xiaxiayige.plugin'
                packaging 'aar'
                description 'update version 1.0.0'
            }
 		   //配置本地的Maven地址设置为E:/Maven
            repository(url: uri('E:/repo'))
        }
    }
}		

## 3.到Library目录下，删除Main目录下的所有文件夹及文件,然后在main录下进行操作

	1.创建一个groovy的目录(也可以创建java目录，表示你的插件使用java编写的，但是记得注意在配置plugin支持的时候要引用java版本的gradle插件)，然后在目录下创建你自己的包名，然后创建一个自己的Plugin类，需要以groovy结尾，如：xxxxx.groovy
	
	2.然后在main目录下依次创建目录 resources/META-INF/gradle-plugins

	3.然后在gradle-plugins目录下创建文件 com.xxx.plugin.properties (插件名.properties)
	
	4.然后在com.xxx.plugin.properties中填写
		
		implementation-class = com.xiaxiayige.plugin.TestPlugin (插件类全路径)

## 4.然后在编写我们的TestPlugin.groovy文件

	class TestPlugin implements Plugin<Project> {

    void apply(Project project) {
        println("==============================> this is TestPlugin ")
    }

	}

## 5.上传插件，找到右侧的（一般在右侧）Gradle目录,然后找到Libray目录，然后找到tasks，然后找到	upload目录，然后找到uploadAchieves,然后双击执行,这也是Maven里面提供的一个方法，帮助我们打包生成相关依赖包。

![](https://raw.githubusercontent.com/xiaxiayige/LearingProJect/master/img/aidl_7.png)

## 6.引用插件.
	
	1.首先到我们跟项目中的build.gradle文件中添加代码。
		1.1 添加指定我们本地的maven地址
		1.2 添加classPath

		buildscript {
    		repositories {
			//1.1 步骤
	        maven{
                //指定本地的数据仓库 如果是远程的 指定远程仓库链接即可
	            url uri('E:/repo')
	        }
	        google()
	        jcenter()
    	}
    	dependencies {
        classpath 'com.android.tools.build:gradle:3.3.1'

		//1.2步骤 classPath = 上面定义的(group:artifactId:version)
        classpath 'com.xiaxiayige.plugin:testPlugin:1.0.0'
        
    }
	}
	
	2.然后在我们的App项目模块中添加引入插件。

	//插件名称就是 定义的这个 com.xxx.plugin.properties文件的文件名
	
	apply plugin: 'com.xxx.plugin'

## 7.验证结果
	
	1.点击Make-Project（Windows中快捷键 = Ctrl+9,就是那个榔头一样的图标）以后就可以验证结果。

![](https://raw.githubusercontent.com/xiaxiayige/LearingProJect/master/img/aidl_6.png)

ok，到这里，基本验证通过。接下来我们再做更多操作吧。


## 8.参考文章:

	https://blog.csdn.net/yulong0809/article/details/77752098

## 9.另外

	另外可以参考 https://github.com/JakeWharton/butterknife这个项目中插件的那一部分的目录结构和写法，可以发现基本上就是上面讲的那样定义声明的。也不怕忘记了 哈哈。

## Demo地址

[https://github.com/xiaxiayige/LearingProJect/tree/master/GradlePluginDemo](https://github.com/xiaxiayige/LearingProJect/tree/master/GradlePluginDemo)
