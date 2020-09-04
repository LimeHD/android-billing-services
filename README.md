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
apply plugin: 'com.huawei.agconnect'
...
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
### Проверка существования сервисов
Проверка существования всех сервисов оплаты
````java
    limeBillingServices.getControllerVerify().verifyAllServices(new ExistenceServicesListener() {
            @Override
            public void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing) {
                
            }
        });
````
Проверка существования конкретного сервиса оплаты
```java
     limeBillingServices.getControllerVerify().verifyService(EnumPaymentService.google, new ExistenceServiceListener() {
            @Override
            public void callBackExistenceService(EnumPaymentService paymentService, boolean existing) {
                
            }
        });
```
### Инициализация
#### Инициализация нескольких платежных систем
Инициализация производится с помощью билдера SetupBillingInterfaces
```java
      setupBillingInterfaces = new SetupBillingInterfaces.Builder()
                .setHuaweiSetupCallBacks(new HuaweiSetupCallBacks() {
                    ...
                })
                .setGoogleSetupCallBacks(new GoogleSetupCallBacks() {
                    ...
                }).build();
        limeBillingServices.getControllerInitial().initServices(setupCallBackMap);
```

### Запрос инвентаря
````java
        List<String> skuList = new ArrayList<>();
        skuList.add("pack");
        limeBillingServices.requestInventoryFrom(EnumPaymentService.google, skuList, new RequestInventoryListener() {
            ...
        });
````

### Запрос купленных подписок
````java
        limeBillingServices.requestPurchases(EnumPaymentService.google, new RequestPurchasesListener() {
            ...
        });
````

### Покупка подписки
#### Покупка через гугл
Для отслеживания статуса совершения подписки необходимо 
````java
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.google, new GooglePurchaseCallBacks() {
            ...
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
Либо вызвать метод limeBillingServices.onActivityResult в onActivityResult активити

````java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        limeBillingServices.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
````

Для отслеживания статуса совершения подписки необходимо 
````java    
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.huawei, new HuaweiPurchaseCallBacks() {
            ...
        });
````
Покупка
````java
limeBillingServices.launchBuySubscription(EnumPaymentService.huawei, sku);
````


## Классы
### LimeBillingServices
Класс для работы с платежным модулем

#### getSkuDetailDataBySku(@NonNull EnumPaymentService service, @NonNull String sku)
Получение информации о подписке по идентификатору
sku - идентификатор подписки

#### getPurchaseDataBySku(@NonNull EnumPaymentService service, @NonNull String sku)
Получение информации о купленной подписке по идентификатору
sku - идентификатор подписки

#### setPurchaseCallBack(@NonNull EnumPaymentService service, @NonNull PurchaseCallBack purchaseCallBack)
Установка callBack-а на покупку подписки

#### requestPurchases(@NonNull EnumPaymentService service, @NonNull RequestPurchasesListener requestPurchasesListener)
Запрос к платежной системе за купленными подписками

#### requestInventoryFrom(@NonNull EnumPaymentService service, @NonNull List<String> skuList, @NonNull RequestInventoryListener requestInventoryListener)
Запрос информации о подписках
skuList - список идентификаторов подписок
  
#### launchBuySubscription(@NonNull EnumPaymentService service, @NonNull String sku)
Инициализация покупки подписки
sku - идентификатор подписки

#### onActivityResult(int requestCode, int resultCode, Intent data)
Только для Huawei оплаты
Необходимо вызвать в onActivityResult вашего activity. Сообщает Huawei оо успешности покупки

#### getControllerVerify()
Возвращает контроллер, который проверяет существования сервисов

#### getControllerInitial()
Возвращает контроллер, который управляет инициализацией сервисов

### SkuDetailData
Содержит информацию о подписке

### PurchaseData
Содержит информацию о купленной подписке

### ControllerInitialServices

#### initService(@NonNull SetupBillingInterfaces setupBillingInterfaces)
Инициализирует сервисы согласно билдеру

### ControllerVerifyServices

#### verifyAllServices(@NonNull ExistenceServicesListener existenceServicesListener)
Проверяет существование всех доступных сервисов

#### verifyService(EnumPaymentService enumPaymentService, @NonNull ExistenceServiceListener existenceServiceListener)
Проверяет существование сервиса


## Интерфейсы

### PurchaseCallBack
промежуточный callBack не стоит использовать

### GooglePurchaseCallBacks

#### onAcknowledgePurchaseStart()
Срабатывает при старте подтверждения покупки

#### onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap)
Срабатывает при успешной покупке подписки
Map<String, PurchaseData> purchaseDataMap - для доступа к данным используются идентификаторы подписок
purchaseData - данные о купленной подписке

#### onAcknowledgePurchaseError(String error, EnumPurchaseState enumPurchaseState)
Срабатывает при неудачном подтверждении подписки

#### onPurchaseUpdateError(String message, EnumPurchaseState enumPurchaseState)
Срабатывает при неудачном обновлении подписки полсе ее покупки

### HuaweiPurchaseCallBacks

#### onHuaweiPurchaseSuccess(@Nullable PurchaseData purchaseData, Map<String, PurchaseData> map)
Срабатывает при успешной покупке подписки
Map<String, PurchaseData> purchaseDataMap - для доступа к данным используются идентификаторы подписок
purchaseData - данные о купленной подписке

#### onHuaweiPurchaseError(@Nullable String message, @NonNull EnumPurchaseState enumPurchaseState)
Сребатывает при неудачной покупке подписки

### RequestPurchasesListener

#### onSuccessRequestPurchases(@NonNull Map<String, PurchaseData> purchaseDetailsMap)
Срабатывает при успешном запросе купленных подписок
Map<String, PurchaseData> purchaseDetailsMap - для доступа к данным используются идентификаторы подписок

#### onErrorRequestPurchases(@Nullable String message)
Срабатывает при неуспешном запросе купленных подписок

### RequestInventoryListener

#### onSuccessRequestInventory(@NonNull Map<String, SkuDetailData> skuDetailsMap)
Срабатывает при успешном запросе информации о подписках
Map<String, SkuDetailData> skuDetailsMap - для доступа к данным используются идентификаторы подписок

#### void onErrorRequestInventory(@Nullable String error)
Срабатывает при неуспешном запросе информации о подписках

### ExistenceServiceListener
#### callBackExistenceService(EnumPaymentService paymentService, boolean existing)
Возвращается после проверки существования сервиса

### ExistenceServicesListener
#### callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing)
Возвращается после проверки существования всех сервисов
