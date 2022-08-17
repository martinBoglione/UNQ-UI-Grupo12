import './User.css'
import React, { useEffect, useState } from "react";
import { Link, useHistory, useParams } from 'react-router-dom'
import Api from './Api';


const User = () => {

    const logueado = JSON.parse(localStorage.getItem("user"))
    const [userData, setUserData] = useState({});
    const [posts, setPosts] = useState([]);
    const history = useHistory();
    const id = useParams();
    const esUser = id.id === logueado.id;
    const [follower, setFollower] = useState([]);
    const ides = follower.map(foll => { return foll.id });
    const loSigue = ides.includes(userData.id)
    const [sigue, setSigue] = useState(loSigue);

 
    const object = {
        headers: {
            authorization: localStorage.getItem("token")
        }
    }


    
    useEffect(() => {
        getUserID();
        setFollower(follower)
        setSigue(loSigue)
        getUserLikeado();
    }, [loSigue]);


    const changeSigue = event => {
        event.preventDefault();
        updateFollower();
        setSigue(!sigue)

    }


    const handleClickSearch = (event) => {
        event.preventDefault();
        history.push("/search");
    }


    const updateFollower = () => {
        Api.putFollower(object,{ id:userData.id ,name: userData.name , image: userData.image },id.id)
            .then((response) => {
                getUserLikeado();
            })
            .catch((error) => console.log("Error: ", error));
    }

    const getUserID = () => {
        Api.getUserWithID(object,id.id)
            .then((response) => {
                setUserData(response.data);
                setPosts(response.data.posts);
            })
            .catch((error) => console.log("Error: ", error));
    }


    const getUserLikeado = () => {
        Api.getUserWithID(object,logueado.id)
            .then((response) => {
                setFollower(response.data.followers)
            })
            .catch((error) => console.log("Error: ", error));
    }


    const handleLogout = (event) => {
        event.preventDefault();
        localStorage.removeItem("user")
        localStorage.removeItem("token")
        history.push("/login")
    }


    return (
        <>
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
                        <Link to={`/user/${logueado.id}`}>
                            <img src={logueado.image} id="rounded-img"></img>
                        </Link>
                    </li>
                </ul>
            </nav>

            {esUser && <div id="center">
                <img className="imagen margin-right" id="rounded-img" src={logueado.image} />
                <span className="mayus">{logueado.name}</span>
                <span><button onClick={handleLogout} type="submit" className="btn btn-danger" >Logout</button></span>
            </div>}

            {!esUser && <div id="center">
                <img className="imagen margin-right" id="rounded-img" src={userData.image} />
                <span className="mayus margin-right">{userData.name}</span>
                <button onClick={changeSigue} type="button" className="btn btn-primary">{sigue ? "Dejar de Seguir" : "Seguir"}</button>
            </div>}

            
            
            <div class="container">
            <hr></hr>
                <div class="row row-cols-1 row-cols-md-3">
                    {posts.map(post => (
                        <div key={post.id}>
                            <div className="margin-bottom">
                                <Link to={`/post/${post.id}`}>
                                    <img src={post.landScape} width="365" height="320" />
                                </Link>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </>
    )
}


export default User;