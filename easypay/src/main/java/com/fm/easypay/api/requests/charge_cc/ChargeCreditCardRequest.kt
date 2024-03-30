package com.fm.easypay.api.requests.charge_cc

import com.fm.easypay.api.requests.base.ApiRequest
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class ChargeCreditCardRequest(
    override val userDataPresent: Boolean,
    override val body: ChargeCreditCardBodyDto,
) : ApiRequest<ChargeCreditCardBodyDto>(userDataPresent, body)

internal data class ChargeCreditCardBodyDto(
    @SerializedName("ccCardInfo")
    val creditCardInfo: CreditCardInfoDto,

    @SerializedName("AcctHolder")
    val accountHolder: PersonalDataDto,

    // EndCustomer is optional, but API doesn't allow null values - requires empty object.
    @SerializedName("EndCustomer")
    val endCustomer: Any,

    @SerializedName("Amounts")
    val amounts: AmountsDto,

    @SerializedName("PurchItems")
    val purchaseItems: PurchaseItemsDto,

    @SerializedName("MerchID")
    val merchantId: Int,
)

internal data class CreditCardInfoDto(
    @SerializedName("AccountNumber")
    val accountNumber: String,

    @SerializedName("ExpMonth")
    val expMonth: Int,

    @SerializedName("ExpYear")
    val expYear: Int,

    @SerializedName("CSV")
    val csv: String,
)

internal data class PersonalDataDto(
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
    val billingAddress: BillingAddressDto,

    @SerializedName("Email")
    val email: String? = null,

    @SerializedName("Phone")
    val phone: String? = null,
)

internal data class BillingAddressDto(
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

internal data class AmountsDto(
    @SerializedName("TotalAmt")
    val totalAmount: BigDecimal,

    @SerializedName("SalesTax")
    val salesTax: BigDecimal,

    @SerializedName("Surcharge")
    val surcharge: BigDecimal,

    @SerializedName("Tip")
    val tip: Double,

    @SerializedName("CashBack")
    val cashback: Double,

    @SerializedName("ClinicAmount")
    val clinicAmount: Double,

    @SerializedName("VisionAmount")
    val visionAmount: Double,

    @SerializedName("PrescriptionAmount")
    val prescriptionAmount: Double,

    @SerializedName("DentalAmount")
    val dentalAmount: Double,

    @SerializedName("TotalMedicalAmount")
    val totalMedicalAmount: Double,
)

internal data class PurchaseItemsDto(
    @SerializedName("ServiceDescrip")
    val serviceDescription: String? = null,

    @SerializedName("ClientRefID")
    val clientRefId: String? = null,

    @SerializedName("RPGUID")
    val rpguid: String? = null,
)