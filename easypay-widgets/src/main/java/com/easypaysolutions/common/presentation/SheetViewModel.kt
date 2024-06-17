package com.easypaysolutions.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easypaysolutions.api.responses.annual_consent.AnnualConsent
import com.easypaysolutions.common.presentation.add_new_card.AddNewCardHelper
import com.easypaysolutions.common.presentation.add_new_card.AddNewCardViewData
import com.easypaysolutions.customer_sheet.CustomerSheet
import com.easypaysolutions.customer_sheet.utils.CustomerSheetResult
import com.easypaysolutions.networking.NetworkResource
import com.easypaysolutions.payment_sheet.PaymentSheet
import com.easypaysolutions.payment_sheet.utils.PaymentSheetResult
import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsent
import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsentBodyParams
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsent
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentBodyParams
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsents
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsBodyParams
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnual
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnualParams
import com.easypaysolutions.repositories.charge_cc.ChargeCreditCard
import com.easypaysolutions.repositories.charge_cc.ChargeCreditCardBodyParams
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class SheetViewModel private constructor(
    private var selectedCardId: Int? = null,
    private val listAnnualConsentsParams: ListAnnualConsentsBodyParams,
    private val listAnnualConsents: ListAnnualConsents,
    private val createAnnualConsent: CreateAnnualConsent,
    private val cancelAnnualConsent: CancelAnnualConsent,
    private val chargeCreditCard: ChargeCreditCard,
    private val processPaymentAnnual: ProcessPaymentAnnual,
    private val workContext: CoroutineContext,
) : ViewModel() {

    constructor(
        config: PaymentSheet.Configuration,
        listAnnualConsents: ListAnnualConsents,
        createAnnualConsent: CreateAnnualConsent,
        cancelAnnualConsent: CancelAnnualConsent,
        chargeCreditCard: ChargeCreditCard,
        processPaymentAnnual: ProcessPaymentAnnual,
        workContext: CoroutineContext,
    ) : this(
        config.preselectedCardId,
        ListAnnualConsentsBodyParams(
            config.consentCreator.merchantId,
            config.consentCreator.customerReferenceId
        ),
        listAnnualConsents,
        createAnnualConsent,
        cancelAnnualConsent,
        chargeCreditCard,
        processPaymentAnnual,
        workContext,
    ) {
        paymentSheetConfig = config
    }

    constructor(
        config: CustomerSheet.Configuration,
        listAnnualConsents: ListAnnualConsents,
        createAnnualConsent: CreateAnnualConsent,
        cancelAnnualConsent: CancelAnnualConsent,
        chargeCreditCard: ChargeCreditCard,
        processPaymentAnnual: ProcessPaymentAnnual,
        workContext: CoroutineContext,
    ) : this(
        config.preselectedCardId,
        ListAnnualConsentsBodyParams(
            config.consentCreator.merchantId,
            config.consentCreator.customerReferenceId
        ),
        listAnnualConsents,
        createAnnualConsent,
        cancelAnnualConsent,
        chargeCreditCard,
        processPaymentAnnual,
        workContext,
    ) {
        customerSheetConfig = config
    }

    private var paymentSheetConfig: PaymentSheet.Configuration? = null
    private var customerSheetConfig: CustomerSheet.Configuration? = null

    val selectedCard: AnnualConsent?
        get() = selectedCardId?.let { selectedCardId ->
            (paymentMethods.value as? PaymentMethodsUiState.Success)?.methods?.firstOrNull { it.id == selectedCardId }
        }

    private val _addNewCardResult = MutableSharedFlow<AddNewCardUiState>()
    val addNewCardResult: SharedFlow<AddNewCardUiState> = _addNewCardResult

    private val _deleteCardResult = MutableSharedFlow<DeleteCardUiState>()
    val deleteCardResult: SharedFlow<DeleteCardUiState> = _deleteCardResult

    private val _payWithNewCardResult = MutableSharedFlow<PayWithNewCardUiState>()
    val payWithNewCardResult: SharedFlow<PayWithNewCardUiState> = _payWithNewCardResult

    private val _payWithSavedCardResult =
        MutableStateFlow<PayWithSavedCardUiState>(PayWithSavedCardUiState.Idle)
    val payWithSavedCardResult: StateFlow<PayWithSavedCardUiState> = _payWithSavedCardResult

    private val _paymentMethods =
        MutableStateFlow<PaymentMethodsUiState>(PaymentMethodsUiState.Loading)
    val paymentMethods: StateFlow<PaymentMethodsUiState> = _paymentMethods

    private val _customerSheetResult = MutableSharedFlow<CustomerSheetResult>()
    val customerSheetResult: SharedFlow<CustomerSheetResult> = _customerSheetResult

    private val _paymentSheetResult = MutableSharedFlow<PaymentSheetResult>()
    val paymentSheetResult: SharedFlow<PaymentSheetResult> = _paymentSheetResult

    private val _openNewCardSheet = MutableStateFlow<OpenNewCardSheetUiState>(OpenNewCardSheetUiState.Idle)
    val openNewCardSheet: StateFlow<OpenNewCardSheetUiState> = _openNewCardSheet

    private val _errorState = MutableSharedFlow<Throwable>()
    val errorState: SharedFlow<Throwable> = _errorState

    init {
        fetchAnnualConsents()
    }

    //region Actions

    fun onCardSelected(selectedCard: AnnualConsent?) {
        selectedCardId = selectedCard?.id
    }

    fun deleteSelectedCard() {
        selectedCard?.let {
            deleteCard(it)
        }
    }

    fun openNewCardSheet() {
        viewModelScope.launch(workContext) {
            _openNewCardSheet.emit(OpenNewCardSheetUiState.Open)
        }
    }

    fun closeNewCardSheet(withRefresh: Boolean = false) {
        viewModelScope.launch(workContext) {
            _openNewCardSheet.emit(OpenNewCardSheetUiState.Close)
            if (withRefresh) {
                fetchAnnualConsents()
            }
        }
    }

    fun payWithNewCard(viewData: AddNewCardViewData) {
        val config = paymentSheetConfig ?: return
        val params = AddNewCardHelper.toChargeCreditCardBodyParams(
            viewData = viewData,
            endCustomer = config.endCustomer,
            accountHolder = config.accountHolder,
            amounts = config.amounts,
            purchaseItems = config.purchaseItems,
            consentCreator = config.consentCreator,
        )
        payWithNewCard(config, params, viewData)
    }

    fun addNewCard(viewData: AddNewCardViewData) {
        val config = customerSheetConfig ?: return
        val params = AddNewCardHelper.toCreateAnnualConsentBodyParams(
            viewData = viewData,
            endCustomer = config.endCustomer,
            accountHolder = config.accountHolder,
            consentCreator = config.consentCreator,
        )
        addNewCard(params)
    }

    fun payWithSavedCard(it: AnnualConsent) {
        val config = paymentSheetConfig ?: return
        val params = ProcessPaymentAnnualParams(
            it.id,
            config.amounts.totalAmount,
        )
        payWithSavedCard(params)
    }

    //endregion

    //region Completions

    fun completeWithResult(result: CustomerSheetResult) {
        viewModelScope.launch(workContext) {
            _customerSheetResult.emit(result)
        }
    }

    fun completeWithResult(result: PaymentSheetResult) {
        viewModelScope.launch(workContext) {
            _paymentSheetResult.emit(result)
        }
    }

    fun completeWithError(error: Throwable) {
        viewModelScope.launch(workContext) {
            _errorState.emit(error)
        }
    }

    //endregion

    //region Helpers

    private fun deleteCard(card: AnnualConsent) {
        viewModelScope.launch(workContext) {
            _deleteCardResult.emit(DeleteCardUiState.Loading)
            val params = CancelAnnualConsentBodyParams(card.id)
            val result = cancelAnnualConsent.cancelAnnualConsent(params)

            when (result.status) {
                NetworkResource.Status.SUCCESS -> {
                    result.data?.let {
                        _deleteCardResult.emit(DeleteCardUiState.Success(it))
                        onCardSelected(null)
                        fetchAnnualConsents()
                    } ?: _deleteCardResult.emit(DeleteCardUiState.Error(Exception()))
                }

                NetworkResource.Status.DECLINED,
                NetworkResource.Status.ERROR,
                -> {
                    _deleteCardResult.emit(DeleteCardUiState.Error(result.error ?: Exception()))
                }
            }
        }
    }

    private fun addNewCard(config: PaymentSheet.Configuration, viewData: AddNewCardViewData) {
        val params = AddNewCardHelper.toCreateAnnualConsentBodyParams(
            viewData = viewData,
            endCustomer = config.endCustomer,
            accountHolder = config.accountHolder,
            consentCreator = config.consentCreator,
        )
        addNewCard(params)
    }

    private fun payWithNewCard(
        config: PaymentSheet.Configuration,
        params: ChargeCreditCardBodyParams,
        viewData: AddNewCardViewData,
    ) {
        viewModelScope.launch(workContext) {
            _payWithNewCardResult.emit(PayWithNewCardUiState.Loading)
            val result = chargeCreditCard.chargeCreditCard(params)

            when (result.status) {
                NetworkResource.Status.SUCCESS -> {
                    result.data?.let {
                        if (viewData.shouldSaveCard) {
                            _payWithNewCardResult.emit(PayWithNewCardUiState.Idle)
                            addNewCard(config, viewData)
                        } else {
                            _payWithNewCardResult.emit(PayWithNewCardUiState.Success(it))
                        }
                    } ?: _payWithNewCardResult.emit(PayWithNewCardUiState.Error(Exception()))
                }

                NetworkResource.Status.ERROR -> {
                    _payWithNewCardResult.emit(
                        PayWithNewCardUiState.Error(
                            result.error ?: Exception()
                        )
                    )
                }

                NetworkResource.Status.DECLINED -> {
                    result.data?.let {
                        _payWithNewCardResult.emit(PayWithNewCardUiState.Declined(it))
                    } ?: _payWithNewCardResult.emit(PayWithNewCardUiState.Error(Exception()))
                }
            }
        }
    }

    private fun addNewCard(params: CreateAnnualConsentBodyParams) {
        viewModelScope.launch(workContext) {
            _addNewCardResult.emit(AddNewCardUiState.Loading)
            val result = createAnnualConsent.createAnnualConsent(params)

            when (result.status) {
                NetworkResource.Status.SUCCESS -> {
                    result.data?.let {
                        _addNewCardResult.emit(AddNewCardUiState.Success(it))
                    } ?: _addNewCardResult.emit(AddNewCardUiState.Error(Exception()))
                }

                NetworkResource.Status.DECLINED,
                NetworkResource.Status.ERROR,
                -> {
                    _addNewCardResult.emit(AddNewCardUiState.Error(result.error ?: Exception()))
                }
            }
        }
    }

    private fun payWithSavedCard(params: ProcessPaymentAnnualParams) {
        viewModelScope.launch(workContext) {
            _payWithSavedCardResult.emit(PayWithSavedCardUiState.Loading)
            val result = processPaymentAnnual.processPaymentAnnual(params)

            when (result.status) {
                NetworkResource.Status.SUCCESS -> {
                    result.data?.let {
                        _payWithSavedCardResult.emit(PayWithSavedCardUiState.Success(it))
                    } ?: _payWithSavedCardResult.emit(PayWithSavedCardUiState.Error(Exception()))
                }

                NetworkResource.Status.ERROR -> {
                    _payWithSavedCardResult.emit(
                        PayWithSavedCardUiState.Error(
                            result.error ?: Exception()
                        )
                    )
                }

                NetworkResource.Status.DECLINED -> {
                    result.data?.let {
                        _payWithSavedCardResult.emit(PayWithSavedCardUiState.Declined(it))
                    } ?: _payWithSavedCardResult.emit(PayWithSavedCardUiState.Error(Exception()))
                }
            }
        }
    }

    //endregion

    //region Data fetching

    private fun fetchAnnualConsents() {
        viewModelScope.launch(workContext) {
            _paymentMethods.emit(PaymentMethodsUiState.Loading)
            val result = listAnnualConsents.listAnnualConsents(listAnnualConsentsParams)
            when (result.status) {
                NetworkResource.Status.SUCCESS -> {
                    result.data?.let {
                        _paymentMethods.emit(PaymentMethodsUiState.Success(it.consents))
                    }
                }

                NetworkResource.Status.ERROR -> {
                    result.error?.let {
                        _paymentMethods.emit(PaymentMethodsUiState.Error(it))
                    }
                }

                NetworkResource.Status.DECLINED -> {}
            }
        }
    }

    //endregion

}