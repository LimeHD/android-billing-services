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
```

### 5. Измени settings.gradle
```git
include ':androidbillingmodule'
project(':androidbillingmodule').projectDir = new File(rootDir, 'android-billing-services/androidbillingmodule/')
```

## Работа с модулем
### Инициализация
#### Инициализация нескольких платежных систем
```java
      LimeBillingServices limeBillingServices = new LimeBillingServices(activity);
      Map<EnumPaymentService, ServiceSetupCallBack> setupCallBackMap = new HashMap<>();
      setupCallBackMap.put(EnumPaymentService.google, new GoogleSetupCallBacks() {
            @Override
            public void onBillingSetupFinishedSuccess() {
            }

            @Override
            public void onBillingSetupFinishedError(String message) {
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });
      setupCallBackMap.put(EnumPaymentService.huawei, new HuaweiSetupCallBacks() {
            @Override
            public void onHuaweiSetupFinishSuccess() {    
            }

            @Override
            public void onHuaweiSetupFinishError(@Nullable String message) {
            }
        });
        limeBillingServices.getControllerInitial().initServices(setupCallBackMap);
```

#### Инициализация только одной платежной системы
````java
      LimeBillingServices limeBillingServices = new LimeBillingServices(activity);
      limeBillingServices.getControllerInitial().initSingleService(EnumPaymentService.google, new GoogleSetupCallBacks() {
            @Override
            public void onBillingSetupFinishedSuccess() {
                
            }

            @Override
            public void onBillingSetupFinishedError(String message) {

            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });
````

### Запрос инвентаря
````java
        List<String> skuList = new ArrayList<>();
        skuList.add("pack");
        limeBillingServices.requestInventoryFrom(EnumPaymentService.google, skuList, new RequestInventoryListener() {
            @Override
            public void onSuccessRequestInventory(@NonNull Map<String, SkuDetailData> skuDetailsMap) {
                
            }

            @Override
            public void onErrorRequestInventory(@Nullable String error) {

            }
        });
````

### Запрос купленных подписок
````java
        limeBillingServices.requestPurchases(EnumPaymentService.google, new RequestPurchasesListener() {
            @Override
            public void onSuccessRequestPurchases(@NonNull Map<String, PurchaseData> purchaseDetailsMap) {
                
            }

            @Override
            public void onErrorRequestPurchases(@Nullable String message) {

            }
        });
````

### Покупка подписки
#### Покупка через гугл
Для отслеживания статуса совершения подписки необходимо 
````java
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.google, new GooglePurchaseCallBacks() {
            @Override
            public void onAcknowledgePurchaseStart() {
                
            }

            @Override
            public void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap) {

            }

            @Override
            public void onAcknowledgePurchaseError(String error) {

            }

            @Override
            public void onPurchaseUpdateError(String message) {

            }
        });
````
Покупка
````java
limeBillingServices.launchBuySubscription(EnumPaymentService.google, sku);
````
#### Покупка через Huawei
Для покупки через huawei необходимо, чтобы активити унаследовалась от HuaweiPayActivity
````java
public class MainActivity extends HuaweiPayActivity {
....
}
````
Для отслеживания статуса совершения подписки необходимо 
````java    
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.huawei, new HuaweiPurchaseCallBacks() {
            @Override
            public void onHuaweiPurchaseSuccess(@Nullable PurchaseData purchaseData, Map<String, PurchaseData> map) {
                
            }

            @Override
            public void onHuaweiPurchaseError(@Nullable String message) {

            }
        });
````
Покупка
````java
limeBillingServices.launchBuySubscription(EnumPaymentService.huawei, sku);
````
