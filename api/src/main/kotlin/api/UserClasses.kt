package api

data class DraftUser(val id: String, val name: String, val image: String, val followers: List<SimpleUserID>, val posts: List<PostUser>)

data class SimpleUser(val name: String, val image: String)
data class SimpleUserID(val id:String ,val name: String, val image: String)

data class PostUser(val id: String, val description: String, val portrait: String, val landScape: String, val likes: List<SimpleUserID>, val date: String, val user: SimpleUser)

data class UserConTimelineYFollowers(val name: String, var image: String, val followers: List<SimpleUserID>, val timeline: List<SimpleTimeLine>)

data class LoginData(var email: String, var password: String)

data class RegisterData(var name: String, val email: String, var password: String, var image: String)

data class PostByTag(val content: List<PostContent>)

data class PostContent(val id: String, val description: String, val portrait: String, val landScape: String, val likes: List<SimpleUserID>, val date: String, val user: SimpleUser)

data class UserByName(val content: List<UserContent>)

data class UserContent(val id:String, val name: String, val image: String, val followers: List<SimpleUserID>)

data class SimpleComment(val id: String, val body: String, val user: SimpleUser)
data class SimpleCommentID(val id: String, val body: String, val user: SimpleUserID)

data class SimpleTimeLine(val id: String, var description: String, var portrait: String, var landscape: String, var likes: List<SimpleUserID>, var Date: String, var user: SimpleUserID)

data class SimplePost(val id:String, var description:String, var portrait:String, var landscape:String, val likes:List<SimpleUser>, val date:String, val user: SimpleUserID, val comment:List<SimpleComment>)
data class SimplePostID(val id:String, var description:String, var portrait:String, var landscape:String, val likes: List<SimpleUserID>, val date:String, val user: SimpleUserID, val comment:List<SimpleCommentID>)