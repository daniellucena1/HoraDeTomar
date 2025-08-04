package br.upe.horaDeTomar.data.workManager.factory

import android.content.Context
import androidx.compose.ui.tooling.data.SourceContext
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class WrapperWorkerFactory @Inject constructor(
    private val workerFactory: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>,>,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ) : ListenableWorker? {
        val foundEntry = workerFactory.entries.find {
            Class.forName(workerClassName).isAssignableFrom(it.key)
        }

        val factoryProvider = foundEntry?.value ?: throw IllegalArgumentException("Worker Class Name desconehcida: $workerClassName")

        return factoryProvider.get().create(appContext, workerParameters)
    }
}