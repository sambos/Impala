package rsol.impala.service

import java.util.*

class MemoryUserService(): UserService {

    private val _users = HashMap<String, User>()
    private val default = User("0","none","none")

    init {
        addUser(User("1", "user1_fname", "user1_lname"))
    }

    override fun getUser(id: String): User {
        return if (_users.containsKey(id)) _users.getOrDefault(id, default)
        else throw IllegalArgumentException("Unknown user $id")
    }

    override fun addUser(user: User): Unit {
        _users.put(user.id, user)
    }

    override fun remUser(id: String): Unit {
        _users.remove(id)
    }
}
