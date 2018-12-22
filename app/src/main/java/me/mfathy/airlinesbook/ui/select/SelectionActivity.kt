package me.mfathy.airlinesbook.ui.select

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_selection.*
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.exception.ErrorMessageFactory
import me.mfathy.airlinesbook.injection.ViewModelFactory
import me.mfathy.airlinesbook.ui.state.Resource
import me.mfathy.airlinesbook.ui.state.ResourceState
import java.util.*
import javax.inject.Inject

/**
 * SelectionActivity shows a list of airports.
 */
class SelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var selectionViewModel: SelectionViewModel

    private lateinit var mSelectionAdapter: SelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        AndroidInjection.inject(this)

        selectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SelectionViewModel::class.java)

        mSelectionAdapter = SelectionAdapter { onAirportClick(it) }

        rVAirports.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rVAirports.addItemDecoration(itemDecor)
        rVAirports.adapter = mSelectionAdapter
    }

    override fun onStart() {
        super.onStart()

        //  observe changes in articles data.
        selectionViewModel.getAirportsLiveData().observe(this,
                Observer<Resource<List<AirportEntity>>> {
                    it?.let { resource ->
                        handleAirportsResult(resource)
                    }
                })

        //  Fetch Airports
        val lang = Locale.getDefault().language
        selectionViewModel.fetchAirports(lang, 100, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleAirportsResult(resource: Resource<List<AirportEntity>>) {
        when {
            resource.status == ResourceState.SUCCESS -> resource.data?.let { airports ->
                mSelectionAdapter.mAirports = airports
                mSelectionAdapter.notifyDataSetChanged()

                //  UI
                rVAirports.visibility = View.VISIBLE
                pBAirportsLoading.visibility = View.INVISIBLE
                txtVError.visibility = View.INVISIBLE
            }
            resource.status == ResourceState.LOADING -> {
                //  UI
                rVAirports.visibility = View.INVISIBLE
                pBAirportsLoading.visibility = View.VISIBLE
                txtVError.visibility = View.INVISIBLE
            }
            resource.status == ResourceState.ERROR -> {
                resource.error?.let {
                    txtVError.text = ErrorMessageFactory.create(this, it)

                    //  UI
                    rVAirports.visibility = View.INVISIBLE
                    pBAirportsLoading.visibility = View.INVISIBLE
                    txtVError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onAirportClick(airport: AirportEntity) {
        val intent = Intent()
        intent.putExtra(SELECTED_AIRPORT_RESULT_KEY, airport)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SelectionActivity::class.java)
        }

        const val PICK_ORIGIN_AIRPORT_REQUEST = 1
        const val PICK_DESTINATION_AIRPORT_REQUEST = 2
        const val SELECTED_AIRPORT_RESULT_KEY = "SELECTED_AIRPORT_RESULT_KEY"
    }
}