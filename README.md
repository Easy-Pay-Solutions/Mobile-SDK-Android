# EasyPay Android SDK

EasyPay library offers access to the EasyPay API for seamless integration with Android applications.

### Requirements

* Android 6.0 (API level 23) and above
* Gradle 8.2 and above
* Android Gradle Plugin 8.2.1

### Get started

1. Prerequisites - get HMAC secret and API key from EasyPay.

2. Configure the EasyPay class at the very beginning of the application lifecycle, e.g. in the main Application class (in the onCreate() method).
```
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EasyPay.init(applicationContext, "YOUR_API_KEY", "YOUR_HMAC_SECRET")
    }
}
```

3. Please mind that during EasyPay initialization, the RSA certificate download process begins. Proceeding with any call before downloading has finished will result with an exception (RSA_CERTIFICATE_NOT_FETCHED). You can check the download status by accessing the following enum:
```
EasyPayConfiguration.getInstance().getRsaCertificateFetchingStatus()
```

### Semantic Versioning

Semantic versioning follows a three-part version number: MAJOR.MINOR.PATCH.

Increment the:
- MAJOR version when you make incompatible API changes,
- MINOR version when you add functionality in a backwards-compatible manner, and
- PATCH version when you make backwards-compatible bug fixes.