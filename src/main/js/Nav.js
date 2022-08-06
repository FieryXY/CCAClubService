import React from 'react';
import './App.css';
import './Nav.css';


import {useNavigate} from "react-router-dom";

function Nav(props) {

  let navigate = useNavigate();
  
  return (
    <nav>
        <div className="nav-header">
          <img alt="Logo Link" className = "link_icon"/>
          <p className="nav-title">CCA Club Hub</p>
          <button className = "logOutButton">Log Out</button>
        </div>
        <ul className='nav-links'>
            <li className="buttonLi"><a onClick={() => navigate("/featured-page")} className="button" unselectable="on">Home</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/manage-clubs")} className="button" unselectable="on">Manage Clubs</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/asb")} className="button" unselectable="on">ASB</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/about")} className="button" unselectable="on">About</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/club-search")} className="button" unselectable="on">Search</a></li>
            <li className="buttonLi"><a onClick={() => navigate((props.isLoggedIn) ? "/club-editor" : "/login")} className="button" unselectable="on">{(props.isLoggedIn) ? "Club Editor" : "Login"}</a></li>
        </ul>
    </nav>  
  );
}

export default Nav;
