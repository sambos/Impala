package rsol.impala.service

data class User(val id:String, val fname: String, val lname: String)

interface UserService {
    fun getUser(id: String): User
    fun addUser(user: User): Unit
    fun remUser(id: String): Unit
}
