//import logo from './logo.svg';
import './Login.css';
import React,{useState} from 'react';
import { Link } from 'react-router-dom';
import { useHistory} from 'react-router-dom'
import Api from './Api';


const Login = () => {
  const history = useHistory();
  const [data, setData] = useState({email: "", password: ""});
  const [error , setError] = useState(false);


const handleSubmit= (event) => {
  event.preventDefault(); 
    Api.login(data)
 .then((response) => {
      localStorage.setItem("token",response.headers.authorization);
      localStorage.setItem("user",JSON.stringify(response.data))

      history.push("/home");
    })
    .catch((error) => console.log("Error: ", error));
    setError(true);
};
 
const handleInputChange = (event) => {
  setData({  ...data,[event.target.name]: event.target.value });
};

    
const puedeIngresar =() => {
  return data.email.trim() && data.password.trim();
}


  document.body.style = 'background: #f7f6f6;';
  return(  
   <>
      <div className="container center max-width-padding">
        <div className="ig">Instagram</div>

        <form id="formulario" onSubmit={handleSubmit}>     

          <div className="email">
            <label htmlFor="email">Ingrese su email</label>
            <input type="email" name="email" value={data.email} placeholder="example@gmail.com" onChange={handleInputChange} className="form-control margin-bottom font-size-weight "  aria-describedby="emailHelp"></input>
          </div>
      
          <div className="password">
            <label htmlFor="password" >Ingrese su contraseña</label>
            <input type="password" name="password"  placeholder="*******" value={data.password} onChange={handleInputChange} className="form-control margin-bottom font-size-weight " ></input>
          </div>
      
          <button type="submit" className={`btn btn-primary margin-top-btn btn-block ${puedeIngresar()? '' : 'disabled'}`}> Iniciar sesión </button>

          <div className="linkTo">
           ¿No tienes cuenta? <Link to="/register">¡Registrate!</Link>
          </div>
      
        </form>
        {error && <div className="alert alert-danger" role="alert">Usuario o Contraseña Incorrectos </div>}
      </div>
   </>);
  }




export default Login;
