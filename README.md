# Capo Filer

A simple, easy and ligth file management app for Android

## Screen Shot

|Top|Drawer|Icons|
|---|---|---|
|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112842.png "")|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112853.png "")|![TOP](https://raw.githubusercontent.com/Capotasto/File-Explorer-Android/images/images/device-2018-06-16-112656.png "")|

## Getting Started

Get this projcet and give it a shot!

```
git clone https://github.com/Capotasto/File-Explorer-Android.git
```

And open Android Studio, open this project and run !

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

## Running the tests

There are not test in this project yet!

## Authors

* **Norio Egi** - *Initial work* - [Capotasto](https://github.com/Capotasto)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
