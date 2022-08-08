import React, {useEffect, useState} from 'react';
import './App.css';
import './Nav.css';


import {useNavigate} from "react-router-dom";

function Nav(props) {

  let navigate = useNavigate();

  const [displayMobileNav, setDisplayMobileNav] = useState(false);

  const handleHamburgerClick = (e) => {
    setDisplayMobileNav(!displayMobileNav);
  }


  const onResize = () => {
    if(window.innerWidth > 900) {
      setDisplayMobileNav(false);
    }
  }

  

  useEffect(() => {
    window.addEventListener('resize', onResize)

    return () => {window.removeEventListener('resize', onResize)};
  }, []);



  return (
    <nav>
        <div className="nav-header">
        <div className="menuIconContainer" onClick={handleHamburgerClick}>
          <div className="menuIcon" onClick={handleHamburgerClick}></div>
          <div className="hamburger" onClick={handleHamburgerClick}></div>
          <div className="hamburger2" onClick={handleHamburgerClick}></div>
          <div className="hamburger3" onClick={handleHamburgerClick}></div>
        </div>
        <img src = {require("./img/link.png").default} alt="Logo Link" className = "link_icon"/>

          <p className="nav-title">CCA Club Hub</p>
          {props.isLoggedIn && <button className = "logOutButton" onClick={() => {props.logOut(navigate)}}>Log Out</button>}
        </div>
          <ul className='nav-links' style={displayMobileNav ? {transform: "translateY(100%)"} : {}}>
            <li className="buttonLi"><a onClick={() => navigate("/featured-page")} className="button" unselectable="on">Home</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/club-search")} className="button" unselectable="on">Search Clubs</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/manage-clubs")} className="button" unselectable="on">Manage Clubs</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/asb")} className="button" unselectable="on">ASB</a></li>
            <li className="buttonLi"><a onClick={() => navigate("/about")} className="button" unselectable="on">About</a></li>
            <li className="buttonLi"><a onClick={() => navigate( (props.isLoggedIn) ? "/club-editor" : "/login" )} className="button" unselectable="on">{(props.isLoggedIn) ? "Club Editor" : "Login"}</a></li>

        </ul>
    </nav>  
  );
}

export default Nav;
