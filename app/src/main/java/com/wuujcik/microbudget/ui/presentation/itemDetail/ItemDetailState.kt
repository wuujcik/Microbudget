package com.wuujcik.microbudget.ui.presentation.itemDetail

import com.wuujcik.microbudget.ui.base.TextFieldState
import com.wuujcik.microbudget.ui.base.textFieldStateSaver

// PURPOSE OF SPENDING
class PurposeState :
    TextFieldState(validator = ::isPurposeValid, errorFor = ::purposeValidationError)

val PurposeStateSaver = textFieldStateSaver(PurposeState())

private fun purposeValidationError(purpose: String?): String {
    return "Invalid purpose: $purpose"
}

private fun isPurposeValid(purpose: String?): Boolean {
    return !purpose.isNullOrEmpty() && purpose.length < 35
}


// AMOUNT
class AmountState : TextFieldState(validator = ::isAmountValid, errorFor = ::amountValidationError)

val AmountStateSaver = textFieldStateSaver(AmountState())

private fun amountValidationError(amount: String?): String {
    return "Invalid amount: $amount"
}

private fun isAmountValid(amount: String?): Boolean {
    return try {
        val doubleAmount = amount?.toDouble()
        doubleAmount != null
    } catch (e: Exception) {
        false
    }
}