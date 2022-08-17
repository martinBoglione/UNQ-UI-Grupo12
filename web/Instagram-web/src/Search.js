import React, { useState } from 'react';
import './Search.css'
import { Link} from 'react-router-dom'
import Api from './Api';


const Search =()=>{

const usuarioLogueado = JSON.parse(localStorage.getItem("user"))
const [data,setData]= useState("");
const buscaTag=data.startsWith("#")
const [contenido,setContenido]= useState([]);
const object = {
    headers: {
        authorization: localStorage.getItem("token")
    }
}


  const handleClickSearch=(event)=>{
      event.preventDefault();
      search();
  }  

const search=()=>{
    let dato = ""
    if(data.startsWith("#")){
   dato =data.replace("#","%23")}
    else{
        dato=data
    }
    Api.searchIG(object,dato)
    .then((response)=>{
    setContenido(response.data.content)
    })
    .catch((error) => console.log("Error: ", error));
}

const handleInputChange = (event) => {
    setData(event.target.value);
}


return(
<>
    <nav className="navbar navbar-expand-lg navbar-light bg-light" >
              <div className="ig  bg-light" id="ignoreBorder" >
              <Link to={`/home`}>
                        Instagram
                    </Link></div>
               
                <ul className=" navbar-nav navbar-right">
                  <ul></ul>
                    <li> <Link to={`/user/${usuarioLogueado.id}`}>
                            <img src={usuarioLogueado.image} id="rounded-img"></img>
                        </Link></li>
                </ul>
            </nav>

            <div className="row justify-content-center">
                <div className="col-12 col-md-10 col-lg-8">
                    <form className="card card-sm">
                        <div className="card-body row no-gutters align-items-center ignoreBorder">
                            <div className="col">
                                <input className="form-control form-control-lg form-control-borderless ignoreBorder" type="search" onChange={handleInputChange} placeholder=" Search users or tags"></input>
                            </div>
                            <div class="col-auto ignoreBorder">
                                <button onClick={handleClickSearch} className="btn btn-lg btn-success" type="submit">Search</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <br></br>
            {buscaTag &&
                <div className="centrado"> <h2 className="text-center"> Post relacionados a tu busqueda: </h2>
                    <hr></hr>
                    <div class="row row-cols-1 row-cols-md-3">
                        {contenido.map(post => (
                            <div key={post.id}>
                                <div className="margin-botton">
                                    <Link to={`/post/${post.id}`}>
                                        <img src={post.landScape} width="230" height="300" />
                                    </Link>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>}

                
            {!buscaTag && data.trim() &&
                <div className="centrado"> <h2 className="text-center"> Usuarios con nombre: {data} </h2>
                    <hr></hr>
                    <div className="row row-cols-1 row-cols-md-3">
                        {contenido.map(user => (
                            <div key={user.id}>
                                <div className="margin-top izq">
                                    <Link to={`/user/${user.id}`}>
                                        <img src={user.image} id="rounded-img"></img>
                                        <strong className="text-dark mayus margin-left">{user.name} </strong>
                                    </Link>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>}
        </>);
}

export default Search;