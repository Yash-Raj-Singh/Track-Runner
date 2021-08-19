package com.example.trackrunner.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackrunner.db.Run
import com.example.trackrunner.other.SortType
import com.example.trackrunner.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository : MainRepository
) : ViewModel() {

    private val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runsSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runsSortedByAvgSpeed = mainRepository.getAllRunsSortedByAvgSpeed()
    private val runsSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurned()
    private val runsSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runsSortedByDate){result ->
            if(sortType == SortType.DATE){
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsSortedByTimeInMillis){result ->
            if(sortType == SortType.RUNNING_TIME){
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsSortedByAvgSpeed){result ->
            if(sortType == SortType.AVG_SPEED){
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsSortedByCaloriesBurned){result ->
            if(sortType == SortType.CALORIES_BURNED){
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsSortedByDistance){result ->
            if(sortType == SortType.DISTANCE){
                result?.let { runs.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType){
        SortType.DATE ->runsSortedByDate.value?.let { runs.value = it }
        SortType.DISTANCE ->runsSortedByDistance.value?.let { runs.value = it }
        SortType.RUNNING_TIME ->runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.CALORIES_BURNED ->runsSortedByCaloriesBurned.value?.let { runs.value = it }
        SortType.AVG_SPEED ->runsSortedByAvgSpeed.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run : Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

}