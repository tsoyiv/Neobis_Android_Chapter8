package com.example.my_app_eight.view_models.recycler_view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class MyItemsRecViewModel : ViewModel() {
    val liveNewsData = MutableLiveData<ArrayList<MyItemsRecViewModel>>()
    private var recyclerItemsModel = ArrayList<MyItemsRecViewModel>()

    init {
        checkData()
    }

    private fun checkData() {
        recyclerItemsModel.clear()
        liveNewsData.value = recyclerItemsModel
    }
}