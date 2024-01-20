# PasswordManager

## Возможности

Данная библиотека имеет следующие возможности:

- Шифрование и расшифрование текстовых данных.
- Шифрование и расшифрование структур данных с логином и паролем.
- Сохранение и восстановление структур данных с логином и паролем в файловой системе.
- Поддержка многопоточного кодера.

## Как установить?

### Зависимости

- JDK 8 и выше
- Gradle 8.0.1 и выше

### Установка библиотеки

Для установки библиотеки необходимо изменить содержимое своего build.gradle скрипта:

```groovy

repositories {
    mavenCentral()
	maven { url 'https://jitpack.io' }
}

dependencies {
	// ваши зависимости
	// ...
	implementation 'com.github.Doomaykaka:PasswordManager:1.0.1'
}

```

## Использование библиотеки

Для использования менеджера сначала его необходимо проинициализировать.
Инициализацию менеджера достаточно осуществить один раз в приложении для использования менеджера.

```java

boolean needsLogs = false;
boolean needRawDataChecked = true;
boolean needMapStorage = false;
boolean needThreadEncoder = true;

Manager.initialize(needsLogs, needRawDataChecked, needMapStorage, needThreadEncoder);

```

Для смены алгоритма кодирования можно выбрать один из предоставленных в библиотеке.

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.MD2);

```

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.MD5);

```

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.SHA);

```

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.SHA256);

```

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.SHA384);

```

```java

Manager.getContext().getEncoder().setAlgorithm(EncoderAlgorithm.SHA512);

```

Для шифрования и расшифрования текстовых данных необходимо:

```java

String coded = Manager.getContext().getEncoder().encodeString("Hello", "123");

String decoded = Manager.getContext().getEncoder().decodeString(coded, "123");

```

В случае использования неправильного пароля для расшифрования, метод decodeString вернёт null.

Для шифрования структур данных с логином и паролем необходимо:

```java

IRecord newRecord = new DefaultRecord();
newRecord.setLogin("admin");
newRecord.setPassword("123");
newRecord.setInfo("info");

Manager.getContext().getStorage().create(newRecord);

IRawData newRaw = Manager.getContext().getEncoder().encodeStruct(Manager.getContext().getStorage(), "123");

```

Для расшифрования структур данных с логином и паролем необходимо:

```java

IStorage storage = Manager.getContext().getEncoder().decodeStruct(newRaw, "123");
IRecord decodeRecord = storage.getByIndex(0);

```

В случае использования неправильного пароля для расшифрования, метод decodeStruct вернёт null.

Для сохранения шифрованных структур данных в файловой системе необходимо:

```java

newRaw.save();

```

Для чтения шифрованных структур данных из файловой системе необходимо:

```java

newRaw.load();

```

## Документация

Документация: [link](https://doomaykaka.github.io/PasswordManager/)