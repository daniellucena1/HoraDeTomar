package br.upe.horaDeTomar.data.repositories

import br.upe.horaDeTomar.data.daos.AccountDao
import br.upe.horaDeTomar.data.entities.Account
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val dao: AccountDao) {

    suspend fun insert(account: Account): Long {
        return dao.insert(account)
    }

    suspend fun update(account: Account) {
        dao.update(account)
    }

    suspend fun delete(account: Account) {
        dao.delete(account)
    }

    suspend fun getAll(): Flow<List<Account>> {
        return dao.getAllAccounts()
    }

    suspend fun getById(id: Int): Account? {
        return dao.getAccountById(id)
    }

    val accounts: Flow<List<Account>> = dao.getAllAccounts()
}
