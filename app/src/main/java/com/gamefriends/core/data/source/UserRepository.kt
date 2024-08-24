package com.gamefriends.core.data.source


import com.gamefriends.core.data.source.local.LocalDataSource
import com.gamefriends.core.data.source.remote.RemoteSource
import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.domain.model.LoginUserData
import com.gamefriends.core.domain.repository.IUserRepository
import com.gamefriends.core.utils.AppExecutors
import com.gamefriends.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IUserRepository {

    override fun getAllLoginUserData(email: String, password: String): Flow<Resource<LoginUserData>> =
        object : NetworkResources<LoginUserData, LoginResponse>() {
            override fun loadFromDB(): Flow<LoginUserData>  = localDataSource.getAllLoginUserData().map {
                DataMapper.mapEntitiesToDomain(it)
            }
            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteSource.login(email, password)
            }

            override suspend fun saveCallResult(data: LoginResponse) {
                val dataUser = DataMapper.mapResponseToEntities(data)
                appExecutors.diskIO().execute{localDataSource.insertLoginUserData(dataUser)}
            }

            override fun shouldFetch(data: LoginUserData?): Boolean {
                data == null || data.token.isNotEmpty()
                return true
            }
        }.asFlow()

    override fun getTokenUser(): Flow<LoginUserData> = localDataSource.getTokenUserData().map {
        DataMapper.mapEntitiesToDomain(it)
    }


}




