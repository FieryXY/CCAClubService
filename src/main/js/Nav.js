import React from 'react';
import './App.css';
import './Nav.css';

import {useNavigate} from "react-router-dom";

function Nav(props) {

  let navigate = useNavigate();

  return (
    <nav>
        <div class="nav-header">
          <img src={''} alt="Logo Link" className = "link_icon"/>
          <p className="nav-title">CCA Club Hub</p>
        </div>
        <ul className='nav-links'>
            <li className="buttonLi"><a onClick={() => navigate("/featured-page")} class="button" unselectable="on">Home</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/manage-clubs")} class="button" unselectable="on">Manage Clubs</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/asb")} class="button" unselectable="on">ASB</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/about")} class="button" unselectable="on">About</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/login")} class="button" unselectable="on">Login</a></li>
        </ul>
    </nav>  
  );
}

export default Nav;
