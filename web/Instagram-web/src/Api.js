import axios from 'axios'

const getUserHome=(header)=>axios.get("http://localhost:7000/user", header)
const getPostID = (header,postId) => axios.get(`http://localhost:7000/post/${postId}`, header)
const putlike=(header,dataUser,postId)=>axios.put(`http://localhost:7000/post/${postId}/like`,dataUser, header)
const commentPost=(header,postId,body)=> axios.post(`http://localhost:7000/post/${postId}/comment`, body, header)
const login =(dataUser)=>axios.post("http://localhost:7000/login", dataUser)
const register = (userData)=> axios.post("http://localhost:7000/register", userData)
const getUserWithID=(header,userId)=>axios.get(`http://localhost:7000/user/${userId}`, header)
const searchIG =(header,dato)=> axios.get(`http://localhost:7000/search?q=${dato}`,header)
const putFollower=(header,userdata,id)=>axios.put(`http://localhost:7000/user/${id}/follow`, userdata, header)

export default {
    getUserHome,
    getPostID,
    putlike,
    commentPost,
    login,
    register,
    getUserWithID,
    searchIG,
    putFollower
};