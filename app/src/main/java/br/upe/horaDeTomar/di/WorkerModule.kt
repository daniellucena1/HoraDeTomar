package br.upe.horaDeTomar.di

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import br.upe.horaDeTomar.data.workManager.factory.AlarmCheckerWorkerFactory
import br.upe.horaDeTomar.data.workManager.factory.AlarmWorkerFactory
import br.upe.horaDeTomar.data.workManager.factory.ChildWorkerFactory
import br.upe.horaDeTomar.data.workManager.factory.RescheduleAlarmWorkerFactory
import br.upe.horaDeTomar.data.workManager.factory.WrapperWorkerFactory
import br.upe.horaDeTomar.data.workManager.worker.AlarmCheckerWorker
import br.upe.horaDeTomar.data.workManager.worker.AlarmWorker
import br.upe.horaDeTomar.data.workManager.worker.RescheduleAlarmWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactoryModule(workerFactory: WrapperWorkerFactory): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(AlarmWorker::class)
    abstract fun bindAlarmWorker(alarmWorkerFactory: AlarmWorkerFactory) : ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(RescheduleAlarmWorker::class)
    abstract fun bindRescheduleAlarmWorker(rescheduleAlarmWorkerFactory: RescheduleAlarmWorkerFactory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(AlarmCheckerWorker::class)
    abstract fun bindAlarmCheckerWorker(alarmCheckerWorkerFactory: AlarmCheckerWorkerFactory): ChildWorkerFactory
}