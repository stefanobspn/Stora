package dev.stefano.stora.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.stefano.stora.data.database.AppDatabase
import dev.stefano.stora.data.model.PegawaiDao
import dev.stefano.stora.data.model.ProductDao
import dev.stefano.stora.data.model.TransactionDao
import dev.stefano.stora.data.model.MemberDao
import dev.stefano.stora.data.model.CabangDao
import dev.stefano.stora.data.repository.PegawaiRepository
import dev.stefano.stora.data.repository.PegawaiRepositoryImpl
import dev.stefano.stora.data.repository.ProductRepository
import dev.stefano.stora.data.repository.ProductRepositoryImpl
import dev.stefano.stora.data.repository.TransactionRepository
import dev.stefano.stora.data.repository.TransactionRepositoryImpl
import dev.stefano.stora.data.repository.MemberRepository
import dev.stefano.stora.data.repository.MemberRepositoryImpl
import dev.stefano.stora.data.repository.CabangRepository
import dev.stefano.stora.data.repository.CabangRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pos_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun providePegawaiDao(db: AppDatabase): PegawaiDao = db.pegawaiDao()

    @Provides
    fun provideMemberDao(db: AppDatabase): MemberDao = db.memberDao()

    @Provides
    fun provideCabangDao(db: AppDatabase): CabangDao = db.cabangDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindPegawaiRepository(impl: PegawaiRepositoryImpl): PegawaiRepository

    @Binds
    @Singleton
    abstract fun bindMemberRepository(impl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    abstract fun bindCabangRepository(impl: CabangRepositoryImpl): CabangRepository
}
