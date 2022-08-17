import './Home.css';
import React, { useEffect, useState } from "react";
import GenericPost from "./GenericPost";
import { Link, useHistory } from 'react-router-dom';
import Api from './Api';




const Home = () => {

    const [userData, setUserData] = useState({});
    const [timeline, setTimeline] = useState([]);
    const [followers, setFollowers] = useState([]);
    const history = useHistory();
    const usuarioLogueado = JSON.parse(localStorage.getItem("user"));
  


    useEffect(() => {
        getUser();
       
    }, []);

    const getUser = () => {
        const object = {
            headers: {
                authorization: localStorage.getItem("token")
            }
        }
        Api.getUserHome(object)
            .then((response) => {
                setUserData(response.data);
                setTimeline(response.data.timeline);
                setFollowers(response.data.followers);
            })
            .catch((error) => console.log("Error: ", error));
    }

    const handleClickSearch = (event) => {
        event.preventDefault();
        history.push("/search");
    }

    return (
        <div>
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
                        <Link to={`/user/${usuarioLogueado.id}`}>
                            <img alt="" src={userData.image} id="rounded-img"></img>
                        </Link>
                    </li>
                </ul>
            </nav>


            <div className="position-margin">
                <div className="margin-left"><b>Followers</b></div>
                {followers.map(follower => (
                    <div key={follower.id}>
                    <Link to={`/user/${follower.id}`}>
                        <img src={follower.image} />
                    </Link>
                     <span className="mayus">{follower.name}</span>
                     </div>
                    ))}
                   

            </div>

            <div className="container">
                {timeline.map(post => (
                    <div key={post.id} className="card width margin">
                        <GenericPost nombre={post.user.name} likes={post.likes} imagenUser={post.user.image}
                            imagenPost={post.portrait} fecha={post.date} postId={post.id} description={post.description}
                            userId={post.user.id} li={post.likes} />
                    </div>
                ))}
            </div>


        </div>


    );
}

export default Home;