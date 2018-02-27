# JCenterPlug [![](https://github.com/kayoSun/resource/blob/master/svgs/download3.0.0-alpha.svg)]() [![](https://github.com/kayoSun/resource/blob/master/svgs/apachelicense.svg)](LICENSE.txt)

## 用于帮助开发者上传代码库到 JCENTER

## 如何使用
  在主工程下的build.gradle 添加如下：
  '''groovy
  buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.kayo.jcenter:jcenter-plug:3.0.0-alpha'
    }
  }
  '''
  
  在要发布代码库的build.gradle 添加如下：
  '''groovy
  apply plugin: 'jcenter-plug'
  publish { // 新增
    bintrayUser=userName
    bintrayKey=userKey
    dryRun=false
    repoName=userRepo//仓库名称
    userOrg = userName // 注册bintray时的username
    groupId = plugId // 项目包名
    artifactId = plugName// 项目名
    publishVersion = plugVersion // 发布版本号
    desc = plugDesc // 项目描述,可选项
    website = siteUrl // 项目站点,可选项
  }
  '''
  完成后 在在你的 gradle tasks 中出现 bintrayUpload 任务
  最后在终端中输入以下命令
  '''bash
  ./gradlew clean build bintrayUpload
  '''
  ## gradle版本注意事项
| jcener-plug version | Java Projects | Android Projects |
|---------------------|---------------|------------------|
| 3.0.0-alpha         | Gradle 4.0+   | Gradle 4.1+      |

  ## 感谢
  bintray-release 项目
  最后欢迎提出您的宝贵意见、建议，祝大家工作愉快！
