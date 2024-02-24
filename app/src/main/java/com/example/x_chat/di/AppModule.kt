package com.example.x_chat.di

import android.app.Application
import android.util.Log
import com.example.x_chat.data.remote.AuthApi
import com.example.x_chat.data.interceptor.SocketInterceptor
import com.example.x_chat.data.interceptor.UserApiInterceptor
import com.example.x_chat.data.manager.LocalUserManagerImp
import com.example.x_chat.data.remote.FileApi
import com.example.x_chat.data.remote.GroupApi
import com.example.x_chat.data.repository.AuthRepositoryImp
import com.example.x_chat.data.repository.ChatRepositoryImp
import com.example.x_chat.data.repository.SocketRepositoryImp
import com.example.x_chat.data.repository.UserApiRepositoryImp
import com.example.x_chat.data.remote.UserApi
import com.example.x_chat.data.repository.FileRepositoryImp
import com.example.x_chat.data.repository.GroupRepositoryImp
import com.example.x_chat.domain.manager.LocalUserManager
import com.example.x_chat.domain.repository.AuthRepository
import com.example.x_chat.domain.repository.ChatRepository
import com.example.x_chat.domain.repository.FileRepository
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.SocketRepository
import com.example.x_chat.domain.repository.UserApiRepository
import com.example.x_chat.domain.usecase.AppEntryUseCases
import com.example.x_chat.domain.usecase.AuthUseCases
import com.example.x_chat.domain.usecase.ChatUseCases
import com.example.x_chat.domain.usecase.FileUseCases
import com.example.x_chat.domain.usecase.GroupUserCase
import com.example.x_chat.domain.usecase.SocketUseCase
import com.example.x_chat.domain.usecase.UserUseCases
import com.example.x_chat.domain.usecase.auth.SignInUser
import com.example.x_chat.domain.usecase.auth.SignUpUser
import com.example.x_chat.domain.usecase.chat.GetChat
import com.example.x_chat.domain.usecase.chat.ReceiveMessage
import com.example.x_chat.domain.usecase.chat.ReceiveSeenMessage
import com.example.x_chat.domain.usecase.chat.ReceiveUnsentMessage
import com.example.x_chat.domain.usecase.chat.SaveMessage
import com.example.x_chat.domain.usecase.chat.SendMessage
import com.example.x_chat.domain.usecase.chat.SendSeenMessage
import com.example.x_chat.domain.usecase.chat.SendUnsentMessage
import com.example.x_chat.domain.usecase.local.DeleteAppEntry
import com.example.x_chat.domain.usecase.local.ReadAppEntry
import com.example.x_chat.domain.usecase.local.SaveAppEntry
import com.example.x_chat.domain.usecase.socket.GetHomeChat
import com.example.x_chat.domain.usecase.socket.SocketConnection
import com.example.x_chat.domain.usecase.group.FetchGChatInfo
import com.example.x_chat.domain.usecase.user.FetchMe
import com.example.x_chat.domain.usecase.user.FetchPChatInfo
import com.example.x_chat.domain.usecase.user.UploadAbout
import com.example.x_chat.domain.usecase.file.UploadChatImage
import com.example.x_chat.domain.usecase.local.UploadGroupImage
import com.example.x_chat.domain.usecase.file.UploadProfileImage
import com.example.x_chat.domain.usecase.user.AcceptRequest
import com.example.x_chat.domain.usecase.group.AddMembers
import com.example.x_chat.domain.usecase.group.CreateGroup
import com.example.x_chat.domain.usecase.user.CreatePChat
import com.example.x_chat.domain.usecase.user.FetchAllFriends
import com.example.x_chat.domain.usecase.user.FetchNotification
import com.example.x_chat.domain.usecase.user.FriendRequest
import com.example.x_chat.domain.usecase.group.LeaveGroup
import com.example.x_chat.domain.usecase.group.RemoveUser
import com.example.x_chat.domain.usecase.user.SearchUser
import com.example.x_chat.domain.usecase.group.UploadGroupAbout
import com.example.x_chat.domain.usecase.group.UploadGroupName
import com.example.x_chat.utils.Constants
import com.example.x_chat.utils.Constants.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImp(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCase(localUserManager: LocalUserManager): AppEntryUseCases =
        AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager),
            deleteAppEntry = DeleteAppEntry(localUserManager)
        )
    @Singleton
    fun provideSocketInterceptor(localUserManager: LocalUserManager): SocketInterceptor {
        return SocketInterceptor(localUserManager)
    }

    @Provides
    @Singleton
    fun provideUserHeaderInterceptor(localUserManager: LocalUserManager): UserApiInterceptor {
        return UserApiInterceptor(localUserManager)
    }

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileApi(userApiInterceptor: UserApiInterceptor): FileApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(userApiInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FileApi::class.java)
    }
    @Provides
    @Singleton
    fun provideGroupApi(userApiInterceptor: UserApiInterceptor): GroupApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(userApiInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(userApiInterceptor: UserApiInterceptor): UserApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(userApiInterceptor).build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(userApi: AuthApi): AuthRepository = AuthRepositoryImp(userApi)

    @Provides
    @Singleton
    fun provideFileRepository(fileApi: FileApi): FileRepository = FileRepositoryImp(fileApi)

    @Provides
    @Singleton
    fun provideGroupRepository(groupApi: GroupApi): GroupRepository = GroupRepositoryImp(groupApi)

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserApiRepository = UserApiRepositoryImp(userApi)

    @Provides
    @Singleton
    fun provideSocketIO(interceptor: SocketInterceptor): Socket {
        return try {
            val options = interceptor.createSocketIOOptions()
            IO.socket(Constants.CHAT_SOCKET_STRING, options)
        } catch (e: Exception) {
            Log.e("SocketIO", "Failed to create Socket instance:", e)
            throw e
        }
    }

    @Provides
    @Singleton
    fun socketRepository(socket: Socket): SocketRepository = SocketRepositoryImp(socket)


    @Provides
    @Singleton
    fun chatRepository(socket: Socket): ChatRepository = ChatRepositoryImp(socket)

    @Provides
    @Singleton
    fun provideSocketUserCases(socketRepository: SocketRepository): SocketUseCase {
        return SocketUseCase(
            connect = SocketConnection(socketRepository),
            getHomeChat = GetHomeChat(socketRepository)
        )
    }

    @Provides
    @Singleton
    fun provideApiUserCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            signUpUser = SignUpUser(authRepository),
            signInUser = SignInUser(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(chatRepository),
            receiveMessage = ReceiveMessage(chatRepository),
            userChat = GetChat(chatRepository),
            receiveSeenMessage = ReceiveSeenMessage(chatRepository),
            sendSeenMessage = SendSeenMessage(chatRepository),
            saveMessage = SaveMessage(chatRepository),
            sendUnsentMessage = SendUnsentMessage(chatRepository),
            receiveUnsentMessage = ReceiveUnsentMessage(chatRepository)
        )
    }

    @Provides
    @Singleton
    fun provideFileUseCases(fileRepository: FileRepository): FileUseCases {
        return FileUseCases(
            uploadChatImage = UploadChatImage(fileRepository),
            uploadProfileImage = UploadProfileImage(fileRepository),
            uploadGroupImage = UploadGroupImage(fileRepository)

        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(userApiRepository: UserApiRepository): UserUseCases {
        return UserUseCases(
            fetchMe = FetchMe(userApiRepository),
            uploadAbout = UploadAbout(userApiRepository),
            fetchPChatInfo = FetchPChatInfo(userApiRepository),
            searchUser = SearchUser(userApiRepository),
            fetchAllFriends = FetchAllFriends(userApiRepository),
            createPChat = CreatePChat(userApiRepository),
            acceptRequest = AcceptRequest(userApiRepository),
            fetchNotification = FetchNotification(userApiRepository),
            friendRequest = FriendRequest(userApiRepository)
        )
    }
    @Provides
    @Singleton
    fun provideGroupUseCases(groupRepository: GroupRepository): GroupUserCase {
        return GroupUserCase(
            fetchGChatInfo = FetchGChatInfo(groupRepository),
            uploadGroupAbout = UploadGroupAbout(groupRepository),
            uploadGroupName = UploadGroupName(groupRepository),
            leaveGroup = LeaveGroup(groupRepository),
            removeUser = RemoveUser(groupRepository),
            createGroup = CreateGroup(groupRepository),
            addMembers = AddMembers(groupRepository),
        )
    }
}