import React, { useEffect , useState } from 'react';
import { Link } from 'react-router-dom';
import './Post.css';
import Api from './Api'


const GenericPost = ({ imagenPost, imagenUser, nombre, fecha, postId, li, userId, description }) => {

  const user = JSON.parse(localStorage.getItem("user"));
    const [likes, setlikes] = useState(li);
    const ides = likes.map(lik => { return lik.id });
    const poseeLike = ides.includes(user.id)
    const [like, setLike] = useState(poseeLike);
    const object = {
      headers: {
        authorization: localStorage.getItem("token")
      }
    }



    useEffect(()=>{
      setLike(like)
      setlikes(likes)

      getPost()
    },[like])


    const getPost = () => {
      Api.getPostID(object,postId)
      .then((response) => {
          setlikes(response.data.likes)
        })
        .catch((error) => console.log("Error: ", error));
    }


    const updateLike = () => {
      
  Api.putlike(object,{id:userId , name:nombre , image:imagenUser},postId)
      .then((response) => {

        setLike(!like);
      })
      .catch((error) => console.log("Error: ", error));

  }

  const handleClickLike = event => {
    event.preventDefault();
    updateLike();
  }



  return (
    <>
      <div className="media text-muted pt-3">
        <Link to={`/user/${userId}`}>
          <img src={imagenUser}></img>
        </Link>
        <p className="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
          <strong className="d-block text-dark mayus">{nombre}</strong>
          <small> {fecha} </small>
        </p>
      </div>
      <Link to={`/post/${postId}`}>
        <img src={imagenPost} width="800" height="700"></img>
      </Link>
      <div className="margin-left margin-bottom margin-top">
      {<button className='ignoreBorder btn-sm not-focusable' type='button' onClick={handleClickLike}>{like ?
            <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" className="bi bi-heart margin-right" fill="red" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z" />
            </svg>
            :
            <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" className="bi bi-heart margin-right" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z" />
            </svg>}
          </button>}

       <b>{likes.length} Me gusta </b> 
      </div>
      <div className="margin-left margin-bottom">
        {description}
      </div>

    </>
  );
}


export default GenericPost;