package com.funckyhacker.capofiler.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Environment
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.funckyhacker.capofiler.R
import com.funckyhacker.capofiler.databinding.ActivityMainBinding
import com.funckyhacker.capofiler.event.ClickItemEvent
import com.funckyhacker.capofiler.util.FileUtils
import com.funckyhacker.capofiler.view.adapter.MainLinearAdapter
import com.funckyhacker.capofiler.view.search.SearchActivity
import dagger.android.AndroidInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

@RuntimePermissions
class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val LAYOUT_LIST = 0
        const val LAYOUT_GRID = 1
        private const val REQUEST = 1
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val dividerItemDecoration: DividerItemDecoration by lazy {
        DividerItemDecoration(this, linearLayoutManager.orientation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolBar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeButtonEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        viewModel.init(this)
        setLinearLayoutManager()
        initDrawer()
    }

    override fun onResume() {
        super.onResume()
        enableAccessStorageWithPermissionCheck()
        viewModel.setData(viewModel.rootFiles)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onBackPressed() {
        if (viewModel.pageSize == 0) {
            super.onBackPressed()
            return
        }
        viewModel.popItem()
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.switch_layout -> {
                val switchMenu = menu.getItem(0)
                if (viewModel.layoutType == LAYOUT_LIST) {
                    switchMenu.icon = ContextCompat.getDrawable(this, R.drawable.ic_view_list_black_24dp)
                    setGridLayoutManager()
                    return true
                }
                switchMenu.icon = ContextCompat.getDrawable(this, R.drawable.ic_view_module_black_24dp)
                setLinearLayoutManager()
                return true
            }
            R.id.menu_sort_by_name -> {
                viewModel.setData(FileUtils.getSortedListByName(viewModel.rootFiles))
                return true
            }
            R.id.menu_sort_by_date -> {
                viewModel.setData(FileUtils.getSortedListByDate(viewModel.rootFiles))
                return true
            }
            R.id.menu_search -> {
                startActivityForResult(SearchActivity.createIntent(this), REQUEST)
                return true
            }
            else -> return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST || resultCode != RESULT_OK || data == null || data.extras == null) {
            Timber.w("Check onActivityResult")
            return
        }
        val file = data.extras.getSerializable(SearchActivity.EXTRA_FILE) as File
        viewModel.setFilesToList(file.absolutePath)
        viewModel.setData(Arrays.asList(*file.listFiles()))
    }

    @Subscribe
    fun onClickItemEvent(event: ClickItemEvent) {
        if (event.file.isDirectory) {
            viewModel.setFilesToList(event.file)
            return
        }
        val apkURI = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", event.file)
        viewModel.sendIntent(contentResolver, event.file, apkURI)
    }

    private fun initDrawer() {
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //Timber.d("onDrawerSlide");
            }

            override fun onDrawerOpened(drawerView: View) {
                Timber.d("onDrawerOpened")


            }

            override fun onDrawerClosed(drawerView: View) {
                Timber.d("onDrawerClosed")

            }

            override fun onDrawerStateChanged(newState: Int) {
                Timber.d("onDrawerStateChanged")

            }
        })

        binding.navView.setNavigationItemSelectedListener { item ->
            // set item as selected to persist highlight
            item.isChecked = true
            // close drawer when item is tapped
            binding.drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_download -> viewModel.setFilesToList(Environment.DIRECTORY_DOWNLOADS)
                R.id.nav_picture -> viewModel.setFilesToList(Environment.DIRECTORY_PICTURES)
                R.id.nav_audio -> viewModel.setFilesToList(Environment.DIRECTORY_MUSIC)
                R.id.nav_video -> viewModel.setFilesToList(Environment.DIRECTORY_MOVIES)
            }

            true
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun enableAccessStorage() {

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStorageDenied() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show()
    }


    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStorageNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show()
    }

    override fun setAdapter(adapter: MainLinearAdapter) {
        binding.listView.adapter = adapter
    }

    override fun startActivity(intent: Intent) {
        try {
            super.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showSnackBar("Couldn't show the preview for this file.")
        }

    }

    override fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    override fun showErrorDialog(@StringRes messageId: Int) {
        MaterialDialog.Builder(this)
                .title(messageId)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .build()
    }

    private fun setLinearLayoutManager() {
        viewModel.layoutType = LAYOUT_LIST
        binding.listView.layoutManager = linearLayoutManager
        binding.listView.adapter = viewModel.linearAdapter
        binding.listView.addItemDecoration(dividerItemDecoration)
    }

    private fun setGridLayoutManager() {
        viewModel.layoutType = LAYOUT_GRID
        binding.listView.layoutManager = GridLayoutManager(this, 3)
        binding.listView.adapter = viewModel.gridAdapter
        binding.listView.removeItemDecoration(dividerItemDecoration)
    }
}
