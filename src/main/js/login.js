import React, { useState } from 'react';
import { render } from 'react-dom';
import "./login.css" 
import ClubService from './ClubService.js';
import {useNavigate} from "react-router-dom";
const Login = (props) => {

    const [userName, setUserName] = useState(""); 
    const [password, setPassword] = useState(""); 
    const [badLogin, setBadLogin] = useState("");
    const [passwordShown, setPasswordShown] = useState(false);
    let navigate = useNavigate();

    const makeVisibleCSS = {
        opacity: 1,
        visibility: "visible"
    };
    const changeUserName = (e) => {
        setUserName(e.target.value);
    }
    const changePassword = (e) => {
        setPassword(e.target.value);
    }   

    const handleKeyPress = (e) => {
        
      if (e.key === 'Enter') {
       onLogin();
       e.preventDefault();
      }
    };
   


    const onLogin = () => {
        ClubService.doLogin(userName, password).then(response => response.json()).then(json => {
            if(json["status"]) {
              if(json["status"] === 401) {
                if(json["message"].toLowerCase().includes("email")) setBadLogin("email");
                else if(json["message"].toLowerCase().includes("password")) setBadLogin("password");
              }
               return;
            }
              sessionStorage.setItem("accessToken", json["jwtToken"]);
              sessionStorage.setItem("clubId", json["clubId"]);
              props.setLoggedIn(true);
              navigate("/club-editor");
      })
    }

    const onToggleShow = (e) => {
        setPasswordShown(!passwordShown);
    }

    return (
        <div className="user-modal">
            <div className="user-modal-container">
                <ul className="switcher">
                    <li><h3 style={{color: "black"}}>Sign in</h3></li>
                </ul>

                <div id="login">
                    <div className="form">
                        <div className="fieldset">
                            <label className="image-replace email" htmlFor="signin-email">E-mail</label>
                            <input onKeyPress = {handleKeyPress} onChange={changeUserName} value={userName} className="full-width has-padding has-border login-input" id="signin-email" type="email" placeholder="E-mail" />
                            <span style={(badLogin === "email") ? makeVisibleCSS : null} className="error-message">An account with this username does not exist!</span>
                        </div>

                        <div className="fieldset">
                            <label className="image-replace password" htmlFor="signin-password">Password</label>
                            <input onKeyPress = {handleKeyPress} onChange={changePassword} value={password} className="full-width has-padding has-border" id="signin-password" type={passwordShown ? "text" : "password"} placeholder="Password" />
                            <a style={{ userSelect: "none" }} onClick={onToggleShow} className="hide-password">{passwordShown ? "Hide" : "Show"}</a>
                            <span style={(badLogin === "password") ? makeVisibleCSS : null} className="error-message">Wrong password! Try again.</span>
                        </div>

                        <div className="fieldset">
                            <div onClick={onLogin} className="full-width submit">Login</div>
                        </div>
                    </div>

                    <p className="form-bottom-message" ><a href="#0">Forgot your password?</a></p>

                </div>
            </div>


        </div>
    );


}
export default Login;
