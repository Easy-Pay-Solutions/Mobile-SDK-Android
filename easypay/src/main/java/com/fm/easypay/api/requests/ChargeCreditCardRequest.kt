package com.fm.easypay.api.requests

import com.fm.easypay.api.requests.base.ApiRequest
import com.google.gson.annotations.SerializedName

internal data class ChargeCreditCardRequest(
    override val userDataPresent: Boolean,
    override val body: ChargeCreditCardBody,
) : ApiRequest<ChargeCreditCardBody>(userDataPresent, body)

data class ChargeCreditCardBody(
    @SerializedName("ccCardInfo")
    val creditCardInfo: CreditCardInfo,

    @SerializedName("AcctHolder")
    val accountHolder: PersonalData,

    @SerializedName("EndCustomer")
    val endCustomer: PersonalData,

    @SerializedName("Amounts")
    val amounts: Amounts,

    @SerializedName("PurchItems")
    val purchaseItems: PurchaseItems,

    @SerializedName("MerchID")
    val merchantId: Int,
)

data class CreditCardInfo(
    @SerializedName("AccountNumber")
    val accountNumber: String,

    @SerializedName("ExpMonth")
    val expMonth: Int,

    @SerializedName("ExpYear")
    val expYear: Int,

    @SerializedName("CSV")
    val csv: String,
)

data class PersonalData(
    @SerializedName("Firstname")
    val firstName: String,

    @SerializedName("Lastname")
    val lastName: String,

    @SerializedName("Company")
    val company: String? = null,

    @SerializedName("Title")
    val title: String? = null,

    @SerializedName("Url")
    val url: String? = null,

    @SerializedName("BillIngAdress")
    val billingAddress: BillingAddress,

    @SerializedName("Email")
    val email: String? = null,

    @SerializedName("Phone")
    val phone: String? = null,
)

data class BillingAddress(
    @SerializedName("Address1")
    val address1: String,

    @SerializedName("Address2")
    val address2: String? = null,

    @SerializedName("City")
    val city: String? = null,

    @SerializedName("State")
    val state: String? = null,

    @SerializedName("ZIP")
    val zip: String,

    @SerializedName("Country")
    val country: String? = null,
)

data class Amounts(
    @SerializedName("TotalAmt")
    val totalAmount: Double,

    @SerializedName("SalesTax")
    val salesTax: Double? = null,

    @SerializedName("Surcharge")
    val surcharge: Double? = null,

    @SerializedName("Tip")
    val tip: Double? = null,

    @SerializedName("CashBack")
    val cashback: Double? = null,

    @SerializedName("ClinicAmount")
    val clinicAmount: Double? = null,

    @SerializedName("VisionAmount")
    val visionAmount: Double? = null,

    @SerializedName("PrescriptionAmount")
    val prescriptionAmount: Double? = null,

    @SerializedName("DentalAmount")
    val dentalAmount: Double? = null,

    @SerializedName("TotalMedicalAmount")
    val totalMedicalAmount: Double? = null,
)

data class PurchaseItems(
    @SerializedName("ServiceDescrip")
    val serviceDescription: String? = null,

    @SerializedName("ClientRefID")
    val clientRefId: String? = null,

    @SerializedName("RPGUID")
    val rpguid: String? = null,
)