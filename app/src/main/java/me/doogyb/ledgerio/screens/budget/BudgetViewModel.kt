package me.doogyb.ledgerio.screens.budget

import me.doogyb.ledgerio.util.SharedPreferenceStringLiveData
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import me.doogyb.ledgerio.database.AppDatabase
import me.doogyb.ledgerio.domain.Amount
import me.doogyb.ledgerio.repository.LedgerRepository
import me.doogyb.ledgerio.util.SharedPreferenceFloatLiveData


const val TAG = "BudgetViewModel"

class BudgetViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val ledgerRepository = LedgerRepository(AppDatabase.getInstance(application))
    private val budgets = ledgerRepository.budgets
    private val spentToday : LiveData<Amount> = ledgerRepository.spentToday

    private val sharedPreferences : SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)

    private val budgetPreference = SharedPreferenceStringLiveData(sharedPreferences, "budget_preference", "0")
    val visStyle = SharedPreferenceStringLiveData(sharedPreferences, "budget_visual_style", "gauge")
//    val visStyle = sharedPreferences.getString("budget_visual_style", "none")


    // Cast the budgetPreference to an Amount object EditTextPreference is stored as String)
    val budgetTotal : LiveData<Amount> = Transformations.map(budgetPreference) {
        Amount.parseString(it)
    }


    private val intervalPreference = SharedPreferenceStringLiveData(sharedPreferences, "interval_preference", "1")

    // Parse intervalPreference to integer (EditTextPreference is stored as String)
    private val intervalInt : LiveData<Int> = Transformations.map(intervalPreference) {
        Integer.parseInt(it)
    }

    // First Budget given ascending unique IDs - this is the current state of the budget
    val currentBudget : LiveData<Amount> = Transformations.map(budgets) {
        if (it.isNotEmpty()) it[0] else Amount(0, 0)
    }
    // Remaining budget as ratio
    val remainingBudgetRatio : LiveData<Float> = Transformations.map(currentBudget) {
        it.toInt().toFloat() / (budgetTotal.value?.toInt() ?: 1)
    }

    // Set budget divided by preferred interval, computed and stored in SharedPreferences
    private val dailyLimitFloat = SharedPreferenceFloatLiveData(sharedPreferences, "daily_limit", 0.0F)

    val dailyLimitAmount = Transformations.map(dailyLimitFloat) {
        Amount(it)
    }
    // Amount you can spend in a day given Budget and Interval/Budget Period
    val dailyRemaining : LiveData<Amount> = Transformations.map(spentToday) {
        dailyLimitAmount.value?.minus(it)
    }


}
