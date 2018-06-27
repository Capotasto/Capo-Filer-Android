# Capo Filer

A simple, easy and ligth file management app for Android

## Screen Shot

|Top|Drawer|Icons|
|---|---|---|
|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112842.png "")|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112853.png "")|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112656.png "")|

## 

## Third party library
- RxJava
- RxAndroid
- RxLifecycle
- Dagger2
- Timber
- Stetho
- Material Dialog
- Commons IO
- Event Bus
- Glide
- Permission Dispatcher


### You must need

Before building app, create a `signingConfigs/release.gradle` into project root.
Then add below code and set your keystore information.

```
signingConfigs {
  release {
    storeFile file('<./your-release-key.jks>')
    storePassword 'your-store-pass'
    keyAlias 'your-alias-name'
    keyPassword 'your-store-pass'
  }
}
```

## Authors

* **Norio Egi** - *Initial work* - [Capotasto](https://github.com/Capotasto)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
