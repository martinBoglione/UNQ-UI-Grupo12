import './Register.css'
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom'
import Api from './Api';


const Register = () => {
    const history = useHistory();
    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        image: ""
    });


    const [error,setError]= useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        Api.register(data)
            .then((response) => {
                localStorage.setItem("token", response.headers.authorization);
                localStorage.setItem("user",JSON.stringify(response.data))
                history.push("/login");
            })
            .catch((error) => setError(true));
    };

    const handleInputChange = (event) => {
        setData({ ...data, [event.target.name]: event.target.value });
    }


    const puedeIngresar = () => {
        return data.email.trim() && data.name.trim() && data.password.trim() && data.image.trim();
    }

    document.body.style = 'background: #f7f6f6;'
    return (

        <div className="container-fluid" id="cajaPrincipal">

            <div className="ig">Instagram</div>
            <div className="promocion">Registrate para ver fotos y videos de tus amigos.</div>

            <form id="formulario" onSubmit={handleSubmit}>

                <input type="text" placeholder="Nombre de usuario" name="name" value={data.user} onChange={handleInputChange} className="form-control margin-top font-size-weight" ></input>
                <input type="email" placeholder="example@gmail.com" name="email" value={data.email} onChange={handleInputChange} className="form-control margin-top font-size-weight" aria-describedby="emailHelp"></input>
                <input type="password" placeholder="Constraseña" name="password" value={data.password} onChange={handleInputChange} className="form-control margin-top font-size-weight" ></input>
                <input type="text" placeholder="Imagen" name="image" value={data.image} onChange={handleInputChange} className="form-control margin-top font-size-weight" ></input>
                <button type="submit" className={`btn btn-primary margin-top-btn btn-block ${puedeIngresar() ? '' : 'disabled'}`}>Registrarte</button>

            </form>

            <p id="condiciones">
                Al registrarte, aceptas nuestras Condiciones, la
                Política de datos y la Política de cookies.
                </p>

            <div id="cuentaCreada">
                ¿Tienes una cuenta?<Link to="/login">  Inicia sesión</Link>
            </div>
            {error && <div className="alert alert-danger" role="alert">Email ya existente </div>}
        </div>
    );
}


export default Register;