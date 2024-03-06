package com.fm.easypay.utils.secured

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.fm.easypay.networking.rsa.RsaHelper
import org.koin.java.KoinJavaComponent

class SecureTextField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : AppCompatEditText(context, attrs, defStyleAttr), SecureWidget<String> {

    private val rsaHelper: RsaHelper by KoinJavaComponent.inject(RsaHelper::class.java)

    override var data: SecureData<String> = SecureData("")

    companion object {
        private const val SECURE_INPUT_TYPE =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
    }

    init {
        inputType = SECURE_INPUT_TYPE
    }

    override fun getText(): Editable? {
        return try {
            Editable.Factory.getInstance().newEditable(data.data)
        } catch (e: NullPointerException) {
            null
        }
    }

    override fun getEditableText(): Editable {
        return try {
            Editable.Factory.getInstance().newEditable(data.data)
        } catch (e: NullPointerException) {
            Editable.Factory.getInstance().newEditable("")
        }
    }

    override fun setInputType(type: Int) {
        super.setInputType(SECURE_INPUT_TYPE)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        if (text.isNullOrEmpty()) return
        val encryptedData = rsaHelper.encrypt(text.toString())
        data = SecureData(encryptedData)
    }
}