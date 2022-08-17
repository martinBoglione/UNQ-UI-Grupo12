import './App.css';
import React from 'react';
import { BrowserRouter, Switch} from 'react-router-dom';
import Login from './Login';
import Post from './Post';
import Register from './Register';
import Search from './Search';
import User from './User';
import Home from './Home';
import PrivateRoute from './PrivateRoute';
import PublicRoute from './PublicRoute';


const App = () => {

    return (
        <BrowserRouter> 
            <Switch>
                <PublicRoute  path="/login" component={Login} />
                <PublicRoute path="/register" component={Register} />
                <PrivateRoute path="/search" component={Search} />
                <PrivateRoute path="/post/:id" component={Post} />
                <PrivateRoute path="/user/:id" component={User} />
                <PrivateRoute path="/home" component={Home} />
                <PrivateRoute path="*" component={Home} />
            </Switch>
        </BrowserRouter>
    );

}


export default App;

