package open.vice.cheers.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun showLoading() {
        isLoading.postValue(true)
    }

    fun hideLoading() {
        isLoading.postValue(false)
    }
}