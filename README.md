# EasyPay Android SDK

EasyPay library offers an access to the EasyPay API for a seamless integration with Android applications.

[General Easy Pay Developer Documentation](https://easypaysoftware.com/en/home)

## Installation

### Requirements

* Android 6.0 (API level 23) and above
* Gradle 8.2 and above
* Android Gradle Plugin 8.2.1
* Kotlin 1.9.22 and above

### Configuration - Gradle / Maven dependency

Add `easypay` to your dependencies in the `build.gradle` file.

```
dependencies {
    implementation 'com.easypaysolutions:easypay:1.0.0'
}
```

## Get started

1. Prerequisites - get API key, HMAC secret and optional Sentry DSN from EasyPay.

2. Configure the EasyPay class at the very beginning of the application lifecycle, e.g. in the main Application class (in the onCreate() method).
```
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EasyPay.init(applicationContext, "YOUR_API_KEY", "YOUR_HMAC_SECRET", "SENTRY_DSN")
    }
}
```

3. Please keep in mind that during EasyPay initialization, the RSA certificate download process begins. Proceeding with any call before downloading has finished will result with an exception (RSA_CERTIFICATE_NOT_FETCHED). You can check the download status by accessing the following enum:
```
EasyPayConfiguration.getInstance().getRsaCertificateFetchingStatus()
```

## Public methods in the EasyPay SDK
[Easy Pay API Documentation](https://easypaypi.com/APIDocsDev/)
### 1. Charge Credit Card (CreditCardSale_Manual)

This method processes a credit card when the credit card details are entered manually. Details include the card number, expiration date, CVV, card holder name and address.
```
ChargeCreditCard().chargeCreditCard(params: ChargeCreditCardBodyParams): NetworkResource<ChargeCreditCardResult>
```
#### Request parameters

* ChargeCreditCardBodyParams
    * encryptedCardNumber: SecureData<String>
    * creditCardInfo: CreditCardInfoParam
    * accountHolder: AccountHolderDataParam
    * endCustomer: EndCustomerDataParam?
    * amounts: AmountsParam
    * purchaseItems: PurchaseItemsParam
    * merchantId: Int

#### Response result

* ChargeCreditCardResult
    * [Fields listed in the API documentation]

### 2. List Annual Consents (ConsentAnnual_Query)

A query that returns annual consent details. Depending on the query sent, a single consent or multiple consents may be returned.
```
ListAnnualConsents().listAnnualConsents(params: ListAnnualConsentsBodyParams): NetworkResource<ListAnnualConsentsResult>
```
#### Request parameters

* ListAnnualConsentsBodyParams
    * merchantId: Int?
    * customerReferenceId: String?
    * rpguid: String?
Either customerReferenceId or rpguid must be provided to get the list of consents of a specific customer.

#### Response result

* ListAnnualConsentsResult
    * [Fields listed in the API documentation]

### 3. Create Annual Consent (ConsentAnnual_Create_MAN)

This method creates an annual consent by sending the credit card details, which includes: card number, expiration date, CVV, and card holder contact data. It is not created by swiping the card through a reader device.
```
CreateAnnualConsent().createAnnualConsent(params: CreateAnnualConsentBodyParams): NetworkResource<CreateAnnualConsentResult>
```
#### Request parameters

* CreateAnnualConsentBodyParams
    * encryptedCardNumber: SecureData<String>
    * creditCardInfo: CreditCardInfoParam
    * accountHolder: AccountHolderDataParam
    * endCustomer: EndCustomerDataParam?
    * consentCreator: ConsentCreatorParam

#### Response result

* CreateAnnualConsentResult
    * [Fields listed in the API documentation]

### 4. Cancel Annual Consent (ConsentAnnual_Cancel)

Cancels an annual consent. Credit card data is removed from the system after the cancellation is complete.
```
CancelAnnualConsent().cancelAnnualConsent(params: CancelAnnualConsentBodyParams): NetworkResource<CancelAnnualConsentResult>
```

#### Request parameters

* CancelAnnualConsentBodyParams
    * consentId: Int

#### Response result

* CancelAnnualConsentResult
    * [Fields listed in the API documentation]

### 5. Process Payment Annual Consent (ConsentAnnual_ProcPayment)

This method uses the credit card stored on file to process a payment for an existing consent.

```
ProcessPaymentAnnual().processPaymentAnnual(params: ProcessPaymentAnnualBodyParams): NetworkResource<ProcessPaymentAnnualResult>
```

#### Request parameters

*  ProcessPaymentAnnualBodyParams
    * consentId: Int

#### Response result

*  ProcessPaymentAnnualResult
    * [Fields listed in the API documentation]

## SecureTextField

The SDK contains a component called SecureTextField which ensures a safe input of number of numbers for credit card. It is a subclass of TextInputEditText which enables freedom of styling as needed.

SecureTextField supports only XML layout configuration:
```
<com.easypaysolutions.utils.secured.SecureTextField
    ... />
```

To get the SecureData from the SecureTextField, use the following property:
```
val secureData = secureTextField.secureData
```
Data is already encrypted and can be used in the API calls directly without any additional encryption.

## How to properly consume the API response

All requests are suspended functions, so they should be called from a coroutine scope. The result of the request is wrapped in a NetworkResource object, which can be handled in the following way:
```
viewModelScope.launch {
    // Example of suspended function call
    val result = ChargeCreditCard().chargeCreditCard(params)
    when (result) {
        is NetworkResource.Status.SUCCESS -> {
            // Handle success
        }
        is NetworkResource.Status.ERROR -> {
            // Handle error
        }
        is NetworkResource.Status.DECLINED -> {
            // Handle loading
        }
    }
}
```
More information about consuming the API response can be found in the [EasyPay REST API documentation](https://easypaysoftware.com/en/rest-api#how-to-properly-consume-the-api-response).

## Possible Exceptions

### EasyPaySdkException
Exceptions that are thrown by the SDK.

| Exception name                         | Suggested solution                                                                                                                                           |
|----------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| EASY_PAY_CONFIGURATION_NOT_INITIALIZED | Check if ```EasyPay.init(...)``` method has been called.                                                                                                     |
| MISSED_SESSION_KEY                     | Check if correct SESSION_KEY has been provided in the ```EasyPay.init(...)``` method.                                                                        |
| MISSED_HMAC_SECRET                     | Check if correct HMAC_SECRET has been provided in the ```EasyPay.init(...)``` method.                                                                        |
| RSA_CERTIFICATE_NOT_FETCHED            | RSA certificate might not be fetched yet. Check the status by calling the ```EasyPayConfiguration.getInstance().getRsaCertificateFetchingStatus()``` method. |
| RSA_CERTIFICATE_FETCH_FAILED           | Contact EasyPay.                                                                                                                                             |
| RSA_CERTIFICATE_PARSING_ERROR          | Contact EasyPay.                                                                                                                                             |

### EasyPayApiException
Exceptions that are thrown by the EasyPay API.

## Semantic Versioning

Semantic versioning follows a three-part version number: MAJOR.MINOR.PATCH.

Increment the:
- MAJOR version when you make incompatible API changes,
- MINOR version when you add functionality in a backwards-compatible manner, and
- PATCH version when you make backwards-compatible bug fixes.

## Feature flags

### Rooted device detection

To enable rooted device detection feature, call the following method before calling the EasyPay.init(...) method:
```
EasyPayFeatureFlagManager.setRootedDeviceDetectionEnabled(true)
```