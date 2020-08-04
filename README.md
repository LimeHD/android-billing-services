# android-billing-services

## Подключение модуля к проекту

### 1. Добавь в репозиторий проекта субмодуль 
``` git
git submodule add https://github.com/LimeHD/android-billing-services.git
```
### 2. Импортируй модуль в проект

1. File -> New -> Import Module...
2. Укажи путь к модулю, нажми ОК.
3. Убери галочку с demo модуля.
4. Нажми Finish

### 3. Измени Gragle project
```git
buildscript {
  repositories {
    maven {
        url 'http://developer.huawei.com/repo/'
    }
  }
  
  dependencies {
    classpath 'com.huawei.agconnect:agcp:1.2.1.300'
  } 
}

allprojects {
  repositories {
    maven {
        url 'http://developer.huawei.com/repo/'
    }
  }
}
```

### 4. Измени Gragle app-module
```git
dependencies {
  implementation project(path: ':androidbillingmodule')
}

apply plugin: 'com.huawei.agconnect'
```

### 5. Измени settings.gradle
```git
include ':androidbillingmodule'
project(':androidbillingmodule').projectDir = new File(rootDir, 'android-billing-services/androidbillingmodule/')


