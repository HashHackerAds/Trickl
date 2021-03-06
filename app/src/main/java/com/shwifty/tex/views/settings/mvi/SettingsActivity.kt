package com.shwifty.tex.views.settings.mvi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.schiwfty.kotlinfilebrowser.FileBrowserActivity
import com.schiwfty.torrentwrapper.confluence.Confluence
import com.shwifty.tex.MyApplication
import com.shwifty.tex.R
import com.shwifty.tex.Trickl
import com.shwifty.tex.utils.createObservableFrom
import com.shwifty.tex.utils.setVisible
import com.shwifty.tex.utils.validateOnActivityResult
import com.shwifty.tex.views.base.mvi.BaseMviActivity
import com.shwifty.tex.views.settings.di.DaggerSettingsComponent
import io.reactivex.Emitter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import javax.inject.Inject


class SettingsActivity : BaseMviActivity<SettingsViewState, SettingsIntents>() {
    private val RC_SELECT_FILE = 303

    @Inject
    lateinit var interactor: SettingsContract.Interactor

    private lateinit var newWorkingDirecotyrEmiter: Emitter<SettingsIntents.NewWorkingDirectorySelected>

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    init {
        DaggerSettingsComponent.builder().repositoryComponent(Trickl.repositoryComponent).build().inject(this)
        super.setup(interactor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(settingsToolbar)
        supportActionBar?.title = getString(R.string.settings_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settingsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        workingDirectoryRootLayout.setOnClickListener {
            Trickl.dialogManager.showChangeWorkingDirectoryRestartRequired(this, {
                FileBrowserActivity.startActivity(this, RC_SELECT_FILE, true)
            })
        }
        super.attachIntents(intents())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data.validateOnActivityResult(requestCode, RC_SELECT_FILE, resultCode, Activity.RESULT_OK, {
            val file = it.getSerializable(FileBrowserActivity.ARG_FILE_RESULT) as File
            Trickl.dialogManager.showChangeWorkingDirectoryDialog(this, Confluence.workingDir, file, {
                previousDirectory, newDirectory ->
                newWorkingDirecotyrEmiter.onNext(SettingsIntents.NewWorkingDirectorySelected(this, previousDirectory, newDirectory, true))
            }, { previousDirectory, newDirectory ->
                newWorkingDirecotyrEmiter.onNext(SettingsIntents.NewWorkingDirectorySelected(this, previousDirectory, newDirectory, false))
            })
        })
    }

    private fun initialIntent(): Observable<SettingsIntents.InitialIntent> {
        return Observable.just(SettingsIntents.InitialIntent(this))
    }

    private fun updateWorkingDirectoryIntent(): Observable<SettingsIntents.NewWorkingDirectorySelected> = createObservableFrom { newWorkingDirecotyrEmiter = it }

    private fun toggleWifiOnlyIntent(): Observable<SettingsIntents.ToggleWifiOnly> {
        return createObservableFrom { emitter ->
            wifiOnlySwich.setOnCheckedChangeListener { _, selected ->
                emitter.onNext(SettingsIntents.ToggleWifiOnly(this, selected))
            }
        }
    }

    private fun restartClientIntent(): Observable<SettingsIntents.RestartApp> {
        return createObservableFrom { emitter ->
            restartClientButton.setOnClickListener {
                emitter.onNext(SettingsIntents.RestartApp())
            }
        }
    }

    fun intents(): Observable<SettingsIntents> {
        return Observable.merge(
                initialIntent(),
                updateWorkingDirectoryIntent(),
                restartClientIntent(),
                toggleWifiOnlyIntent()
        )
    }

    override fun render(state: SettingsViewState) {
        if (state.restart) (this.application as MyApplication).restart()
        state.currentWorkingDirectory?.absolutePath?.let { workingDirectoryField.text = it }
        when {
            state.workingDirectoryErrorString != null -> {
                workingDirectoryError.setVisible(true)
                workingDirectoryError.text = state.workingDirectoryErrorString
            }
            else -> workingDirectoryError.setVisible(false)
        }
        workingDirectorySpinner.setVisible(state.isLoading)
    }
}