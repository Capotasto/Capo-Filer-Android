package com.funckyhacker.fileexplorer.view.search

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.funckyhacker.fileexplorer.R
import com.funckyhacker.fileexplorer.databinding.ActivitySearchBinding
import com.funckyhacker.fileexplorer.event.ClickItemEvent
import dagger.android.AndroidInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : RevealActivity(), SearchView {

    companion object {
        const val EXTRA_FILE = "ex_file"

        fun createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    @Inject lateinit var viewModel: SearchViewModel
    private var binding: ActivitySearchBinding? = null
    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        subscription = CompositeSubscription()
        viewModel.init()
        initSearchView()
        initListView()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun getRevealCenterPoint(): RevealActivity.Point {
        return RevealActivity.Point(0.90f, 0.05f)
    }

    @Subscribe
    fun onClickItemEvent(event: ClickItemEvent) {
        val intent = Intent()
        intent.putExtra(EXTRA_FILE, event.file)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initListView() {
        binding!!.listView.layoutManager = LinearLayoutManager(this)
        binding!!.listView.adapter = viewModel.linearAdapter
    }

    private fun initSearchView() {
        binding!!.searchView.performClick()
        binding!!.searchView.setIconifiedByDefault(false)
        binding!!.searchView.isFocusable = true
        binding!!.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.i("onQueryTextSubmit: %s", query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.i("onQueryTextChange: %s", newText)
                subscription!!.add(viewModel.search(newText)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { r -> viewModel.setData(r) },
                                { e -> Timber.w("Search Error: %s", e.message) },
                                { Timber.d("Search Done") }
                        )
                )
                return false
            }
        })
    }


}
