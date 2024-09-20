package com.gamefriends.core.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.gamefriends.core.data.source.local.LocalDataSources
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.local.enitity.RemoteEntity
import com.gamefriends.core.data.source.remote.network.ApiService

@OptIn(ExperimentalPagingApi::class)
class ContentPagingSource (
    private val localDataSources: LocalDataSources,
    private val apiService: ApiService,
    private val userId: String
):RemoteMediator<Int, FeedUserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedUserEntity>,
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosesToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = apiService.getListContent(page, state.config.pageSize, userId)
            val endOfPaginationReached = response.data?.list?.isEmpty()

            localDataSources.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSources.deleteAllRemoteKeys()
                    localDataSources.deleteFeedUser()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1

                val keys = response.data?.list?.map {
                    it?.id?.let { it1 ->
                        RemoteEntity(
                            id = it1,
                            prevKey  = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
                localDataSources.insertRemoteKeys(keys as List<RemoteEntity>)

                response.data.list.forEach { feedUserItem ->
                    val feedUser = FeedUserEntity(
                        feedUserItem?.id!!,
                        feedUserItem.name!!,
                        feedUserItem.bioUser?.bio!!,
                        feedUserItem.bioUser.gender!!,
                        feedUserItem.bioUser.gamePlayed!!,
                        feedUserItem.bioUser.hobby!!,
                        feedUserItem.bioUser.location!!,
                        feedUserItem.bioUser.profilePicureUrl!!
                    )

                    localDataSources.insertFeedUser(feedUser)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FeedUserEntity>): RemoteEntity? {
        return state.pages.lastOrNull {it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            localDataSources.getAllRemoteKeys(data.userId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FeedUserEntity>): RemoteEntity? {
        return state.pages.firstOrNull {it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            localDataSources.getAllRemoteKeys(data.userId)
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state: PagingState<Int, FeedUserEntity>): RemoteEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.userId?.let { id ->
                localDataSources.getAllRemoteKeys(id)
            }
        }
    }

}