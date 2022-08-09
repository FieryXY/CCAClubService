import React from "react";

function Header(){
  return(
    <div className="topnav">
      <a href="./">
        <img src={require("./images/link.png")} alt="Logo Link" className="link_icon"></img>
      </a>
      <a href="./" className="button">Manage Clubs</a>
      <a href="./" className="button">ASB</a>
      <a href="./" className="button">Login</a>
    </div>
  );
}

export default Header;