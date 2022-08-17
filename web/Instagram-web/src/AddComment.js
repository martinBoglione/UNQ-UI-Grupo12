import React, { useState} from 'react';
import './AddComment.css';


const AddComment = (props) => {

   const [comentario, setComentario] = useState("");

   const handleChangeText = event => {
      setComentario(event.target.value);
   }

   const handleClick = () => {
      props.addComment(comentario.toString());
      setComentario("");
   }

   return (

      <div className="publish" >
         <input onChange={handleChangeText} value={comentario} type="text" size="120" className="no-border" placeholder="Agrega un comentario..."></input>
         <button onClick={handleClick} type="button" className='btn btn-dark btn-sm alinear'> Publicar </button>
      </div>
   );

}

export default AddComment;