import './Post.css';
import React, { useEffect, useState } from 'react';
import AddComment from './AddComment';
import { Link, useHistory, useParams } from 'react-router-dom';
import Api from './Api'

const Comentario = ({ user, comentario, image, id }) => {
  return (<div className="media text-muted pt-3">
    <Link to={`/user/${id}`}>
      <img id="image" src={image}></img>
    </Link>
    <p className="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
      <strong className="d-block text-dark">{user}</strong>
      {comentario}
    </p>
  </div>);
}

const Post = () => {

  const user = JSON.parse(localStorage.getItem("user"));
  const nombre = user.name;
  const imagen = user.image;
  const [comments, setComments] = useState([]);
  const [likes, setlikes] = useState([]);
  const history = useHistory();
  const postId = useParams();
  const [data, setData] = useState({});


  const ides = likes.map(lik => { return lik.id });
  const poseeLike = ides.includes(user.id)
  const [like, setLike] = useState(poseeLike);
  useEffect(() => {
    getPost();
    setlikes(likes)
    setLike(poseeLike)
  },
    [likes]);

  const object = {
    headers: {
      authorization: localStorage.getItem("token")
    }
  }


  const getPost = () => {
   Api.getPostID(object,postId.id)
      .then((response) => {
        setData(response.data);

        setComments(response.data.comment);
        setlikes(response.data.likes)
      })
      .catch((error) => console.log("Error: ", error));
  }


  const handleClickSearch = (event) => {
    event.preventDefault();
    history.push("/search");
  }

  const addComment = text => {
    const data = {
      body: text,
    };
    Api.commentPost(object,postId.id,data)
      .then((response) => {

        setComments(prevstate => [...prevstate, { id:user.id , user: nombre, image: imagen, body: text }]);
      })
      .catch((error) => {
        console.log("Error: ", error)
      })

  }

  const updateLike = () => {
    Api.putlike(object,user,postId.id)
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
    <div className="container-fluid" >

      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="ig bg-light" >
          <Link to={`/home`}>
            Instagram
            </Link>
        </div>

        <ul className=" navbar-nav navbar-right">
          <li>
            <button onClick={handleClickSearch} type="button" className="btn btn-outline-success margin-right" >Search</button>
          </li>
        </ul>
        <ul className=" navbar-nav navbar-right">
          <li>
            <Link to={`/user/${user.id}`}>
              <img src={imagen} id="rounded-img"></img>
            </Link>
          </li>
        </ul>
      </nav>

      <div className="contenedor">


        <div>
          <img src={data.portrait} width="1197" height="800" />
        </div>

        <div className="margin-left">
          <button className='ignoreBorder btn-sm not-focusable' type='button' onClick={handleClickLike}>{like ?
            <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" className="bi bi-heart margin-right" fill="red" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z" />
            </svg>
            :
            <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" className="bi bi-heart margin-right" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z" />
            </svg>}
          </button>

          <small><b>{likes.length} Me gusta </b> </small>
          <div><i> {data.description}</i></div>
        </div>

        <div className="margin-left margin-right">
          {comments.map(com => <Comentario user={com.user.name} image={com.user.image} comentario={com.body} id={com.user.id} />)}
        </div>
        <div className="margin">
          <AddComment addComment={addComment} />
        </div>

      </div>
    </div>
  );
}


export default Post;
