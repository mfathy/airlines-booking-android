package me.mfathy.airlinesbook.ui.base.widgets

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 *
 * AbstractPagination scroll listener to handle pagination events.
 */
abstract class AbstractPagination(layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private val layoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
    private val visibleThreshold = 5
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            loading = true
        }
    }

    fun getCurrentPageNumber() = currentPage

    abstract fun onLoadMore(currentPage: Int, totalItemCount: Int, view: View)
}
