package open.vice.cheers.functionalities.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import open.vice.cheers.core.BaseViewModel
import open.vice.cheers.network.PunkServiceConstants
import open.vice.cheers.repositories.home.HomeRepository
import open.vice.cheers.repositories.model.Beer
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeComponentViewModel @Inject constructor(
    application: Application,
    private val homeRepository: HomeRepository
): BaseViewModel(application) {
    val isScrollLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var hintText: String = ""
    private val currentPage: MutableLiveData<Int> = MutableLiveData(PunkServiceConstants.MIN_PAGE_VALUE)
    private val beerList: MutableLiveData<MutableList<Beer>> = MutableLiveData<MutableList<Beer>>(mutableListOf())
    val filteredBeerList: MutableLiveData<List<Beer>> = MutableLiveData<List<Beer>>(listOf())

    private val startDate: MutableLiveData<LocalDate?> = MutableLiveData()
    private val endDate: MutableLiveData<LocalDate?> = MutableLiveData()

    init {
        getBeersByCurrentPage(pageToUse = currentPage.value!!)
    }

    private fun getBeersByCurrentPage(scrollLoader: Boolean = false, pageToUse: Int) {
        viewModelScope.launch {
            if(scrollLoader) {
                showScrollLoader(true)
            } else {
                showLoading()
            }

            val beerResponse = homeRepository.getBeers(pageToUse)

            if(!beerResponse.hasError()) {
                beerList.value?.addAll(beerResponse.getResponseData())

                beerList.postValue(beerList.value)
                filteredBeerList.postValue(beerList.value)
            }

            if(scrollLoader) {
                showScrollLoader(false)
            } else {
                hideLoading()
            }
        }
    }

    private fun showScrollLoader(value: Boolean) {
        isScrollLoading.postValue(value)
    }

    fun onScrollEnd() {
        if(hintText.isEmpty()) {
            val pageToUse = currentPage.value?.inc() ?: 0
            currentPage.postValue(pageToUse)

            if(startDate.value != null || endDate.value != null) {
                onFilterByDateApplied(pageToUse)
            } else {
                getBeersByCurrentPage(scrollLoader = true, pageToUse)
            }
        }
    }

    fun onSearchEnd(text: String) {
        hintText = text

        if(beerList.value != null) {
            if(text.isEmpty()) {
                filteredBeerList.postValue(beerList.value)
            } else {
                val beerFilteredByName = beerList.value!!.filter { it.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault())) }
                filteredBeerList.postValue(beerFilteredByName)
            }
        }
    }

    fun onStartDateChanged(date: LocalDate) {
        startDate.postValue(date)
    }

    fun onEndDateChanged(date: LocalDate) {
        endDate.postValue(date)
    }

    fun onFilterByDateApplied(pageToUse: Int? = null) {
        var page: Int? = pageToUse
        val resetPage = pageToUse == null

        if(resetPage) {
            beerList.postValue(mutableListOf())
            filteredBeerList.postValue(listOf())

            currentPage.postValue(PunkServiceConstants.MIN_PAGE_VALUE)
            page = PunkServiceConstants.MIN_PAGE_VALUE
        }

        val start = startDate.value
        val end = endDate.value

        viewModelScope.launch {
            if(resetPage) {
                showLoading()
            } else {
                showScrollLoader(true)
            }

            val beerResponse = homeRepository.getBeersByDate(page, start, end)

            if(!beerResponse.hasError()) {
                beerList.value?.addAll(beerResponse.getResponseData())

                beerList.postValue(beerList.value)
                filteredBeerList.postValue(beerList.value)
            }

            if(resetPage) {
                hideLoading()
            } else {
                showScrollLoader(false)
            }

        }
    }
}