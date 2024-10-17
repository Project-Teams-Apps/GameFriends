import android.content.Context
import com.gamefriends.core.data.source.remote.response.FromUser
import com.gamefriends.core.data.source.remote.response.GetHistoryItem
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class SocketIOClient(private val context: Context, serverUrl: String, authToken: String, userID: String) {

    private val socket: Socket = IO.socket("$serverUrl?authToken=$authToken&userID=$userID")

    fun connect(onMessageReceived: (GetHistoryItem) -> Unit) {
        socket.connect()

        // Listen for incoming private messages
        socket.on("private message") { args ->
            val message = args[0] as JSONObject
            val fromUser = message.getJSONObject("fromUser") // Assuming your message has a fromUser object
            val textMessage = message.getString("textMessage")

            val chatHistoryItem = GetHistoryItem(
                fromUser = FromUser(
                    name = fromUser.getString("name"),
                    profilePictureUrl = fromUser.getString("profilePictureUrl"),
                    messageText = textMessage
                )
            )
            onMessageReceived(chatHistoryItem)
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            // Handle disconnection
        }
    }

    fun sendMessage(textMessage: String, toUser: String) {
        val message = JSONObject()
        message.put("textMessage", textMessage)
        message.put("toUser", toUser)
        socket.emit("private message", message)
    }

    fun disconnect() {
        socket.disconnect()
    }
}
